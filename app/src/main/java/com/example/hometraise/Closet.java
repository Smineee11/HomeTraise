package com.example.hometraise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Closet extends AppCompatActivity {

    Button back, change;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail);

        back = (Button)findViewById(R.id.closet_back);
        change = (Button)findViewById(R.id.closet_change);
        picture = (ImageView)findViewById(R.id.closet_image);

    }

}
