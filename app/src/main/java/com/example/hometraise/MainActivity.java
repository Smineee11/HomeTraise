package com.example.hometraise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button startButton, makenewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.start);
        makenewButton = (Button)findViewById(R.id.makenew);
    }

    //인텐트 처리
    public  void displaygrid(View v) {
        Intent it = new Intent(this, character_detail.class);
        startActivity(it);
    }


}