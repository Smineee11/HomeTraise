package com.example.hometraise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Purchase extends AppCompatActivity {

    Button purchaseB, cancleB;
    ImageView picture;
    TextView mypoint;
    private Context context;
    String id;
    int point;
    String selectedName;
    int selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase);

        purchaseB = (Button)findViewById(R.id.purchase_ok);
        cancleB = (Button)findViewById(R.id.purchase_cancle);
        picture = (ImageView)findViewById(R.id.purchase_image);
        mypoint = (TextView)findViewById(R.id.purchase_mypoint);
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        point = intent.getExtras().getInt("point");
        if(intent.getExtras()!=null){
            selectedName  = intent.getStringExtra("name");
            selectedImage = intent.getIntExtra("image", 0);
            picture.setImageResource(selectedImage);
        }

        mypoint.setText("MY POINT: " + point);
    }
    public void purchaseClicked(View v){

        //Toast.makeText(this, "버튼 눌림", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if (point >=500) {

            alertDialogBuilder.setTitle("구매 완료");
            alertDialogBuilder.setMessage("구매가 완료되었습니다. ");
            alertDialogBuilder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Closet").child(id);
                    dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ClosetData data = snapshot.getValue(ClosetData.class);
                            if (data == null)
                                System.out.println("Undefined user");
                            else {
                                if (selectedName.equals("halloween")) {
                                    if (data.halloween == 0 ) {
                                        //구매 가능
                                        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Characters").child(id);
                                        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                CharacterData data = snapshot.getValue(CharacterData.class);
                                                Log.d("NAME ", data.name);
                                                Log.d("POINT ", String.valueOf(data.point));

                                                if (data == null)
                                                    System.out.println("Undefined user");

                                                else {

                                                    data.point = data.point - 500;
                                                    Log.d("CHANGE ", String.valueOf(data.point));

                                                }

                                                String key = dbRef.child("Characters").push().getKey();
                                                CharacterData newdata = new CharacterData(data.name, data.point, data.clothes);
                                                Map<String, Object> newdataValues = newdata.toMap();

                                                dbRef.setValue(newdataValues);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.e("The read failed: ", error.getMessage());
                                            }
                                        });
                                        data.halloween = 1;
                                    }
                                } else if (selectedName.equals("christmas")) {
                                    if (data.christmas == 0) {
                                        //구매 가능
                                        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Characters").child(id);
                                        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                CharacterData data = snapshot.getValue(CharacterData.class);
                                                Log.d("NAME ", data.name);
                                                Log.d("POINT ", String.valueOf(data.point));

                                                if (data == null)
                                                    System.out.println("Undefined user");

                                                else {

                                                    data.point = data.point - 500;
                                                    Log.d("CHANGE ", String.valueOf(data.point));

                                                }

                                                String key = dbRef.child("Characters").push().getKey();
                                                CharacterData newdata = new CharacterData(data.name, data.point, data.clothes);
                                                Map<String, Object> newdataValues = newdata.toMap();

                                                dbRef.setValue(newdataValues);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.e("The read failed: ", error.getMessage());
                                            }
                                        });
                                        data.christmas = 1;

                                    }
                                }
                                String key = dbRef.child("Closet").push().getKey();
                                ClosetData newdata = new ClosetData(data.exercise, data.halloween, data.christmas);
                                Map<String, Object> newdataValues = newdata.toMap();

                                dbRef.setValue(newdataValues);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    finish();
                }

            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else {
            //구매 불가능한 경우
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage("구매하실 수 없습니다.. ");
            alertDialogBuilder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }

            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public void cancelClicked(View v){
        finish();
    }

}
