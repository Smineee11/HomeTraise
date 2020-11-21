package com.example.hometraise;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Store extends AppCompatActivity {

    Button closet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail);

        closet = (Button)findViewById(R.id.closet_button);

        Intent it = getIntent();
    }


}
