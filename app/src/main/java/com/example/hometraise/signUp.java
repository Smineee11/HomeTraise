package com.example.hometraise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class signUp extends AppCompatActivity {
    EditText text_id;
    EditText text_name;
    EditText text_age;
    EditText text_height;
    EditText text_weight;
    String id;
    final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    boolean canSignUp = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        text_id = (EditText) findViewById(R.id.signUp_id);
        text_name = (EditText) findViewById(R.id.signUp_name);
        text_age = (EditText) findViewById(R.id.signUp_age);
        text_height = (EditText) findViewById(R.id.signUp_height);
        text_weight = (EditText) findViewById(R.id.signUp_weight);

        text_id.addTextChangedListener(new TextWatcher() {  // id 수정하면
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                canSignUp = false;  // 확인 버튼을 눌러야 회원가입 가능
            }
        });

        this.checkDuplicateId();
        this.signUp();
    }

    public void checkDuplicateId() {
        Button id_button = (Button)findViewById(R.id.button_id);

        id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = text_id.getText().toString();

                dbRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(id)) {
                            Toast.makeText(signUp.this, "중복되는 아이디입니다.", Toast.LENGTH_LONG).show();
                            canSignUp = false;   // 회원가입 불가
                        }
                        else {
                            Toast.makeText(signUp.this, "사용 가능한 아이디입니다.", Toast.LENGTH_LONG).show();
                            canSignUp = true;   // 회원가입 가능
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("The read failed: ", error.getMessage());
                    }
                });

            }
        });
    }

    public void signUp() {
        final Button signUp = (Button)findViewById(R.id.signUp_button);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canSignUp) {
                    // set Data
                    String name = text_name.getText().toString();
                    String age = text_age.getText().toString();
                    String height = text_height.getText().toString();
                    double weight = Double.parseDouble(text_weight.getText().toString());

                    // Users에 데이터 삽입
                    UserData data = new UserData(name, age, height, weight);    // 삽입할 데이터
                    Map<String, Object> childData = data.toMap();
                    dbRef.child("Users").child(id).setValue(childData);

                    // Characters에 데이터 삽입
                    CharacterData data_char = new CharacterData(name, 0, 0);
                    Map<String, Object> childData_char = data_char.toMap();
                    dbRef.child("Characters").child(id).setValue(childData_char);

                    SharedPreferences pref = getSharedPreferences("app_preferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("id", id);
                    editor.commit();

                    // 다시 처음페이지로 되돌아감
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(signUp.this, "아이디를 확인해 주세요", Toast.LENGTH_LONG).show();
            }

        });
    }
}
