package com.example.hometraise;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class character_detail extends AppCompatActivity {

    Button button;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.characterdetail);
        Intent it = getIntent();
    }

    public void clickExercise(View v){
        Toast.makeText(this,"운동클릭", Toast.LENGTH_SHORT).show();
    }
    public void clickShop(View v){
        Toast.makeText(this,"상점클릭", Toast.LENGTH_SHORT).show();
    }

    public void convert_to_grid(View v) {
        int id = v.getId();
        Button button= (Button)v.findViewById(id);
        String tag = (String) button.getTag();

        Toast.makeText(this, tag, Toast.LENGTH_LONG);
        Intent it = new Intent(this, grid.class);
        it.putExtra("it_tag", tag);
        startActivity(it);
    }
}
