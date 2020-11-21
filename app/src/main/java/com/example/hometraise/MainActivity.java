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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView idtext, nametext;

    private Button startButton, makenewButton;

    androidx.appcompat.widget.Toolbar toolbar;

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

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.

        idtext = (TextView)findViewById(R.id.main_myid);
        nametext = (TextView)findViewById(R.id.main_myname);

        startButton = (Button)findViewById(R.id.start);
        makenewButton = (Button)findViewById(R.id.makenew);

        SharedPreferences pref = getSharedPreferences("app_preferences", MODE_PRIVATE);
        final String id = pref.getString("id", null);

        if(id == null) {    // do not have id
            startButton.setEnabled(false);
            makenewButton.setEnabled(true);
        }
        else {  // id exists
            startButton.setEnabled(true);
            makenewButton.setEnabled(false);

            idtext.setText("ID: " + id);

            final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Characters").child(id);

            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CharacterData data = snapshot.getValue(CharacterData.class);

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

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), character_detail.class);
                intent.putExtra("id", id);
                System.out.println(id);
                startActivity(intent);
            }
        });

        makenewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), signUp.class);
                startActivity(intent);
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
               Toast.makeText(this, "Link Account", Toast.LENGTH_SHORT).show();
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


}