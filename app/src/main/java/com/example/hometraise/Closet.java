package com.example.hometraise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class Closet extends AppCompatActivity {

    Button back, change;
    ImageView picture;
    GridView gridview;
    String[] names3 = {"exercise","halloween", "christmas"}; // 1 1 1
    int[] images3 = new int[]{R.drawable.c_basic, R.drawable.c_halloween, R.drawable.c_christmas };

    String[] names2= {"exercise","halloween"}; //1 1 0
    int [] images2 = new int[]{R.drawable.c_basic, R.drawable.c_halloween};

    String[] names2_1= {"exercise","christmas"}; // 1 0 1
    int [] images2_1 = new int[]{R.drawable.c_basic, R.drawable.c_christmas};

    String[] names1 = {"exercise"}; //1 0 0
    int [] images1 = new int [] {R.drawable.c_basic};

    String selectedName;
    int selectedImage;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet_detail);

        back = (Button)findViewById(R.id.closet_back);
        change = (Button)findViewById(R.id.closet_change);
        picture = (ImageView)findViewById(R.id.closet_image);
        gridview = findViewById(R.id.gridView);

        Intent it = getIntent();
        id = it.getExtras().getString("id");
        final DatabaseReference dbRef;
        final DatabaseReference dbRef2;

        dbRef = FirebaseDatabase.getInstance().getReference().child("Closet").child(id);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("getFirebaseDatabase", "count: " + snapshot.getChildrenCount());


                ClosetData data = snapshot.getValue(ClosetData.class);

                if (data == null)
                    System.out.println("Undefined user");
                else {
                    if (data.exercise == 1 && data.halloween == 0 && data.christmas == 0 ) { // 1 0 0일 때
                        Closet.CustomAdapter3 customAdapter = new CustomAdapter3(names1, images1, Closet.this);
                        gridview.setAdapter(customAdapter);
                    } else if (data.exercise == 1 && data.halloween == 1 && data.christmas == 0 ) { // 1 1 0일때
                        Closet.CustomAdapter3 customAdapter = new CustomAdapter3(names2, images2, Closet.this);
                        gridview.setAdapter(customAdapter);
                    } else if (data.exercise == 1 && data.halloween == 0 && data.christmas == 1 ) { //1 0 1일때
                        Closet.CustomAdapter3 customAdapter = new CustomAdapter3(names2_1, images2_1, Closet.this);
                        gridview.setAdapter(customAdapter);
                    }else if (data.exercise == 1 && data.halloween == 1 && data.christmas == 1 ) { //1 1 1일때
                        Closet.CustomAdapter3 customAdapter = new CustomAdapter3(names3, images3, Closet.this);
                        gridview.setAdapter(customAdapter);
                    }

                }



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: ", error.getMessage());
            }
        });

        dbRef2 = FirebaseDatabase.getInstance().getReference().child("Characters").child(id);

        dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("getFirebaseDatabase", "count: " + snapshot.getChildrenCount());
                CharacterData data2 = snapshot.getValue(CharacterData.class);
                if (data2.clothes == 0) {
                    picture.setImageResource(R.drawable.c_basic);
                } else if (data2.clothes == 1) {
                    picture.setImageResource(R.drawable.c_halloween);
                } else if (data2.clothes == 2) {
                    picture.setImageResource(R.drawable.c_christmas);
                }
            }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("The read failed: ", error.getMessage());
                }
            });



        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id3) {
                final DatabaseReference dbRef3;
                dbRef3 = FirebaseDatabase.getInstance().getReference().child("Closet").child(id);

                dbRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("getFirebaseDatabase", "count: " + snapshot.getChildrenCount());


                        ClosetData data = snapshot.getValue(ClosetData.class);

                        if (data == null)
                            System.out.println("Undefined user");
                        else {
                            if (data.exercise == 1 && data.halloween == 0 && data.christmas == 0 ) { // 1 0 0일 때
                                selectedName = names1[position];
                                selectedImage = images1[position];

                                if(position == 0) {
                                    picture.setImageResource(selectedImage);

                                }
                                else if(position == 1) {
                                    picture.setImageResource(selectedImage);
                                }

                                else if(position == 2){
                                    picture.setImageResource(selectedImage);
                                }
                            } else if (data.exercise == 1 && data.halloween == 1 && data.christmas == 0 ) { // 1 1 0일때
                                selectedName = names2[position];
                                selectedImage = images2[position];

                                if(position == 0) {
                                    picture.setImageResource(selectedImage);

                                }
                                else if(position == 1) {
                                    picture.setImageResource(selectedImage);
                                }

                                else if(position == 2){
                                    picture.setImageResource(selectedImage);
                                }
                            } else if (data.exercise == 1 && data.halloween == 0 && data.christmas == 1 ) { //1 0 1일때
                                selectedName = names2_1[position];
                                selectedImage = images2_1[position];

                                if(position == 0) {
                                    picture.setImageResource(selectedImage);

                                }
                                else if(position == 1) {
                                    picture.setImageResource(selectedImage);
                                }

                                else if(position == 2){
                                    picture.setImageResource(selectedImage);
                                }
                            }else if (data.exercise == 1 && data.halloween == 1 && data.christmas == 1 ) { //1 1 1일때
                                selectedName = names3[position];
                                selectedImage = images3[position];

                                if(position == 0) {
                                    picture.setImageResource(selectedImage);

                                }
                                else if(position == 1) {
                                    picture.setImageResource(selectedImage);
                                }

                                else if(position == 2){
                                    picture.setImageResource(selectedImage);
                                }
                            }

                        }



                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("The read failed: ", error.getMessage());
                    }
                });

            }
        });
    }



    public void backshop(View v){
        finish();
    }

    public void changecliked(View v) {
        Toast.makeText(Closet.this, " " + selectedName, Toast.LENGTH_SHORT).show();
        if (selectedName == NULL) {
            //변화 없음
        } else {
            final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Characters").child(id);

            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CharacterData data = snapshot.getValue(CharacterData.class);
                    Log.d("NAME ", data.name);

                    if (data == null)
                        System.out.println("Undefined user");

                    else {
                        if (selectedName.equals ("halloween"))
                            data.clothes = 1;
                        else if (selectedName.equals("christmas"))
                            data.clothes = 2;
                        else if (selectedName.equals("exercise"))
                            data.clothes = 0;

                        Log.d("CHANGE ", String.valueOf(data.clothes));
                    }

                    String key = dbRef.child("Characters").push().getKey();
                    CharacterData newdata = new CharacterData(data.name, data.point, data.clothes);
                    Map<String, Object> newdataValues = newdata.toMap();

                    dbRef.setValue(newdataValues);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    public class CustomAdapter3 extends BaseAdapter {
        private String[] imageNames;
        private  int[] imagePhoto;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter3(String[] imageNames, int[] imagePhoto, Context context) {
            this.imageNames = imageNames;
            this.imagePhoto = imagePhoto;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

    @Override
    public  int getCount(){
        return imagePhoto.length;
    }
    @Override
    public Object getItem(int i){
        return  null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView =layoutInflater.inflate(R.layout.store_row_items, parent, false);
        }

                    TextView itemName = convertView.findViewById(R.id.itemName);
                    ImageView imageView = convertView.findViewById(R.id.imageView);
                    itemName.setText(imageNames[position]);
                    imageView.setImageResource(imagePhoto[position]);

        return convertView;
    }

}

}
