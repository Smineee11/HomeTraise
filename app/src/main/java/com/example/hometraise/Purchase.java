package com.example.hometraise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Purchase extends AppCompatActivity {

    Button purchaseB, cancleB;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase);

        purchaseB = (Button)findViewById(R.id.purchase_ok);
        cancleB = (Button)findViewById(R.id.purchase_cancle);
        picture = (ImageView)findViewById(R.id.purchase_image);
        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            String selectedName  = intent.getStringExtra("name");
            int selectedImage = intent.getIntExtra("image", 0);

            picture.setImageResource(selectedImage);
        }
    }

}
