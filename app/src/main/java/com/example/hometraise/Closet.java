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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class Closet extends AppCompatActivity {

    Button back, change;
    ImageView picture;
    GridView gridview;
    String[] names = {"c_basic","c_halloween", "c_christmas"};
    int[] images = new int[]{R.drawable.c_basic, R.drawable.c_halloween, R.drawable.c_christmas };
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

        //getFirebaseDatabase();

        Intent it = getIntent();
        id = it.getExtras().getString("id");
        Closet.CustomAdapter3 customAdapter = new CustomAdapter3(names, images, this);
        gridview.setAdapter(customAdapter);



        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedName = names[position];
                selectedImage = images[position];

//                Toast.makeText(Closet.this, " " +position, Toast.LENGTH_SHORT).show();
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
        });
    }



    public void backshop(View v){
        finish();
    }

    public void changecliked(View v) {

        if(selectedName == NULL){

        }
        else{

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView =layoutInflater.inflate(R.layout.store_row_items, parent, false);
        }

        final View finalConvertView = convertView;
        final DatabaseReference dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference().child("Closet").child(id);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("getFirebaseDatabase", "count: " + snapshot.getChildrenCount());


                    ClosetData data = snapshot.getValue(ClosetData.class);
                    if (data == null)
                        System.out.println("Undefined user");

                    else {
                        for (int i = 0; i < 3; i++) {
                            if (data.YorN[i] == 1) {
                                TextView itemName = finalConvertView.findViewById(R.id.itemName);
                                ImageView imageView = finalConvertView.findViewById(R.id.imageView);


                                itemName.setText(imageNames[i]);
                                imageView.setImageResource(imagePhoto[i]);
                            }
                        }
                    }

                }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: ", error.getMessage());
            }

        });

        return convertView;
    }

}

}
