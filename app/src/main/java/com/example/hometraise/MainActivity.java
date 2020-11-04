package com.example.hometraise;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void clickShop(View view){
        Toast.makeText(this,"Shop버튼(테스트용)",Toast.LENGTH_SHORT).show();
    }
    public void clickExercise(View view){
        Toast.makeText(this,"Exercise버튼(테스트용)",Toast.LENGTH_SHORT).show();
    }
}