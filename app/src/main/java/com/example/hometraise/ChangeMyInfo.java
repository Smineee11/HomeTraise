package com.example.hometraise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChangeMyInfo extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_myinfo);

        Intent info_intent = getIntent();

        Button switch_button = (Button)findViewById(R.id.button_change);
        switch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nameEdit = (EditText)findViewById(R.id.name_edittext);
                TextView nameText = (TextView)findViewById(R.id.name_textview);

                EditText ageEdit = (EditText)findViewById(R.id.age_edittext);
                TextView ageText = (TextView)findViewById(R.id.age_textview);

                EditText heightEdit = (EditText)findViewById(R.id.height_edittext);
                TextView heightText = (TextView)findViewById(R.id.height_textview);

                EditText weightEdit = (EditText)findViewById(R.id.weight_edittext);
                TextView weightText = (TextView)findViewById(R.id.weight_textview);

                if(nameEdit.getVisibility() == View.GONE){

                    nameText.setVisibility(View.GONE);
                    ageText.setVisibility(View.GONE);
                    heightText.setVisibility(View.GONE);
                    weightText.setVisibility(View.GONE);

                    nameEdit.setText(nameText.getText());
                    nameEdit.setVisibility(View.VISIBLE);
                    ageEdit.setText(ageText.getText());
                    ageEdit.setVisibility(View.VISIBLE);
                    heightEdit.setText(heightText.getText());
                    heightEdit.setVisibility(View.VISIBLE);
                    weightEdit.setText(weightText.getText());
                    weightEdit.setVisibility(View.VISIBLE);

                }

                else if(nameEdit.getVisibility() == View.VISIBLE){
                    nameEdit.setVisibility(View.GONE);
                    ageEdit.setVisibility(View.GONE);
                    heightEdit.setVisibility(View.GONE);
                    weightEdit.setVisibility(View.GONE);

                    nameText.setText(nameEdit.getText());
                    nameText.setVisibility(View.VISIBLE);
                    ageText.setText(ageEdit.getText());
                    ageText.setVisibility(View.VISIBLE);
                    heightText.setText(heightEdit.getText());
                    heightText.setVisibility(View.VISIBLE);
                    weightText.setText(weightEdit.getText());
                    weightText.setVisibility(View.VISIBLE);

                }

            }
        });

    }



}
