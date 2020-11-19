package com.example.hometraise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button startButton, makenewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    //인텐트 처리
    public  void displaygrid(View v) {
        Intent it = new Intent(this, character_detail.class);
        startActivity(it);
    }


}