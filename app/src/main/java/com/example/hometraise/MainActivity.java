package com.example.hometraise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TextView idtext, nametext;
    private Button startButton, makenewButton;
    SharedPreferences pref;
    androidx.appcompat.widget.Toolbar toolbar;
    String id;

    private FirebaseAuth auth;
    GoogleApiClient googleApiClient;
    private static final int REQ_LINK_GOOGLE = 100;
    private static final int REQ_SIGN_GOOGLE = 200;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        idtext = (TextView)findViewById(R.id.main_myid);
        nametext = (TextView)findViewById(R.id.main_myname);

        startButton = (Button)findViewById(R.id.start);
        makenewButton = (Button)findViewById(R.id.makenew);

        pref = getSharedPreferences("app_preferences", MODE_PRIVATE);
        id = pref.getString("id", null);

        setGoogleSettings();

        if(id == null) {    // do not have id
            idtext.setText("");
            nametext.setText("");
            startButton.setText("Sign in");

            startButton.setOnClickListener(new View.OnClickListener() { // 구글 계정 연동 버튼
                @Override
                public void onClick(View v) {
                    googleSignIn();
                }
            });
        }

        else {  // id exists
            toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.

            startButton.setText("Start");
            idtext.setText("ID: " + id);

            findName();

            startButton.setOnClickListener(new View.OnClickListener() { // 시작하기 눌렀을 때
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), character_detail.class);
                    intent.putExtra("id", id);
                    System.out.println(id);
                    startActivity(intent);
                }
            });

        }

        makenewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), signUp.class);
                startActivity(intent);
            }
        });
    }

    void setGoogleSettings() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        auth = FirebaseAuth.getInstance();
    }

    void googleLink() { // 로그인 한 후 구글 Link 누르면
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_LINK_GOOGLE);
    }

    void googleSignIn() {   // 구글로 로그인
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_SIGN_GOOGLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  // 구글 인증 요청했을 때 결과값 받는 곳
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_LINK_GOOGLE) {    // 구글 계정 등록할 때
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {    // 인증 결과가 성공적이면
                Log.i("Google Link", "auth Success");
                GoogleSignInAccount account = result.getSignInAccount();    // 구글 로그인 정보 담고있음
                handleLinkGoogle(account);
            }
        }

        else if(requestCode == REQ_SIGN_GOOGLE) {   // 구글 계정으로 로그인할 때
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {    // 인증 결과가 성공적이면
                GoogleSignInAccount account = result.getSignInAccount();    // 구글 로그인 정보 담고있음
                handleSignGoogle(account);
            }
        }
    }

    private void handleLinkGoogle(final GoogleSignInAccount account) {    // 구글 계정 연결하기 (로그인 되어 있을 때)
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) { // 로그인 성공
                            String email = account.getEmail();
                            email = email.split("@")[0];
                            Log.i("GoogleLink", "Signin Success : "+email);

                            final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Google");
                            dbRef.child(email).setValue(id);
                        }

                        else    // 로그인 실패
                            Log.e("GoogleLink", "Signin Failed");
                    }
                });
    }

    void handleSignGoogle(final GoogleSignInAccount account) {  // 구글 계정으로 로그인
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) { // 로그인 성공
                            String email = account.getEmail();
                            email = email.split("@")[0];
                            Log.i("GoogleSign", "Signin Success : " + email);

                            final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Google").child(email);

                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String myId = snapshot.getValue().toString();

                                    if(myId == null)
                                        System.out.println("Undefined user");

                                    else {
                                        SharedPreferences pref = getSharedPreferences("app_preferences", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("id", myId);
                                        editor.commit();

                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("The read failed: ", error.getMessage());
                                }
                            });
                        }

                        else  // 로그인 실패
                            Log.i("GoogleSign", "Signin Failed : ");
                    }
                });
    }

    void findName() {
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Users").child(id);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserData data = snapshot.getValue(UserData.class);

                if(data == null)
                    System.out.println("Undefined user");

                else {
                    nametext.setText("NAME: " + data.name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: ", error.getMessage());
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.logout:
               Toast.makeText(this, "My Info", Toast.LENGTH_SHORT).show();
               return true;

           case R.id.myaccount:
               GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
               if(account == null)
                   googleLink();
               else
                   Toast.makeText(this, "Link Completed", Toast.LENGTH_SHORT).show();

               return true;
           default:
               return super.onOptionsItemSelected(item);
       }
    }

    //인텐트 처리
    public  void displaygrid(View v) {
        Intent it = new Intent(this, character_detail.class);
        startActivity(it);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("Google Link and SignIn","GoogleApiClient - ConnectionFailed");
    }
}