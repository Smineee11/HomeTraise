package com.example.hometraise;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static com.example.hometraise.MainActivity.flag;

public class ChangeMyInfo extends AppCompatActivity {

    String name;
    String age;
    String height;
    Double weight=0.0;

    String characterName;
    int characterPoint;
    int characterClothes;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        flag = 2;
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_myinfo);

        Intent info_intent = getIntent();
        final String id = info_intent.getStringExtra("info_id");
        TextView idText = (TextView)findViewById(R.id.id_textview);
        idText.setText(id);

        //유저 데이터 객체를 하나 생성해
        //

        final EditText nameEdit = (EditText)findViewById(R.id.name_edittext);
        final TextView nameText = (TextView)findViewById(R.id.name_textview);
        final EditText ageEdit = (EditText)findViewById(R.id.age_edittext);
        final TextView ageText = (TextView)findViewById(R.id.age_textview);
        final EditText heightEdit = (EditText)findViewById(R.id.height_edittext);
        final TextView heightText = (TextView)findViewById(R.id.height_textview);
        final EditText weightEdit = (EditText)findViewById(R.id.weight_edittext);
        final TextView weightText = (TextView)findViewById(R.id.weight_textview);


        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        final DatabaseReference dbRef1 = FirebaseDatabase.getInstance().getReference().child("Characters").child(id);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserData data = snapshot.getValue(UserData.class);
                name = data.name; age = data.age; height = data.height; weight=data.weight;
                nameText.setText(data.name);
                ageText.setText(data.age);
                heightText.setText(data.height);
                weightText.setText(Double.toString(data.weight));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: ", error.getMessage());
            }
        });

        dbRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CharacterData characterData = snapshot.getValue(CharacterData.class);
                characterName = characterData.name; characterPoint = characterData.point; characterClothes = characterData.clothes;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: ", error.getMessage());
            }
        });


        final UserData myinfo_data = new UserData(name, age, height, weight);    // 삽입할 데이터
        final CharacterData myinfo_character = new CharacterData(characterName,characterPoint,characterClothes);

        Button switch_button = (Button)findViewById(R.id.button_change);

        switch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                    myinfo_data.name = nameEdit.getText().toString();
                    myinfo_data.age = ageEdit.getText().toString();
                    myinfo_data.height = heightEdit.getText().toString();
                    myinfo_data.weight = Double.parseDouble(weightEdit.getText().toString());

                    myinfo_character.name = nameEdit.getText().toString();

                    dbRef.setValue(myinfo_data);
                    dbRef1.setValue(myinfo_character);

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

        ((MainActivity) MainActivity.CONTEXT).onResume();

    }



}
