package com.example.hometraise;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class character_detail extends AppCompatActivity {

    Button button;
    String name = "babo";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.characterdetail);
        Intent it = getIntent();
        TextView nameText = (TextView)findViewById(R.id.textview_name);
        nameText.setText(name);

        // firebase - query point and clothes and set textView
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Characters").child(name);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CharacterData data = snapshot.getValue(CharacterData.class);

                if(data == null)
                    System.out.println("Undefined user");

                else {
                    TextView pointText = (TextView)findViewById(R.id.textview_point);
                    pointText.setText(Integer.toString(data.point));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: ", error.getMessage());
            }
        });

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
