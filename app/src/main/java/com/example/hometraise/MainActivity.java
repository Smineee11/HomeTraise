package com.example.hometraise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    //인텐트 처리
    public  void displaygrid(View v) {
        int id = v.getId();
        LinearLayout layout = (LinearLayout)v.findViewById(id);
        String tag = (String) layout.getTag();

        Toast.makeText(this, tag, Toast.LENGTH_LONG).show();
        Intent it = new Intent(this, character_detail.class);
        it.putExtra("it_tag", tag);
        startActivity(it);
    }


}