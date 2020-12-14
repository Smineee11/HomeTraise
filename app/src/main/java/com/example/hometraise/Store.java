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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Store extends AppCompatActivity {
    GridView gridview;
    Button closet;
    String[] names = {"c_basic","c_halloween"};
    int[] images = new int[]{R.drawable.c_basic, R.drawable.c_halloween};
    String id;
    int mypoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail);
        closet = (Button)findViewById(R.id.closet_button);
        gridview = findViewById(R.id.gridView);
        //Intent it = getIntent();
        CustomAdapter2 customAdapter = new CustomAdapter2(names, images, this);
        gridview.setAdapter(customAdapter);

        Intent it = getIntent();
        id = it.getExtras().getString("id");

        // firebase - query point and clothes and set textView
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Characters").child(id);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CharacterData data = snapshot.getValue(CharacterData.class);

                if(data == null)
                    System.out.println("Undefined user");

                else {
                    TextView pointText = (TextView)findViewById(R.id.store_point);
                    pointText.setText(Integer.toString(data.point));
                    mypoint = data.point;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: ", error.getMessage());
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long myid) {
                String selectedName = names[position];
                int selectedImage = images[position];

//                Toast.makeText(Store.this, " " +position, Toast.LENGTH_SHORT).show();
                if(position == 0) {
                    Intent intent = new Intent(Store.this, Purchase.class);
                    intent.putExtra("name", selectedName);
                    intent.putExtra("image", selectedImage);
                    intent.putExtra("id", id);
                    intent.putExtra("point", mypoint);
                    startActivity(intent);

                }
                else if(position == 1) {
                    Intent intent = new Intent(Store.this, Purchase.class);
                    intent.putExtra("name", selectedName);
                    intent.putExtra("image", selectedImage);
                    intent.putExtra("id", id);
                    intent.putExtra("point", mypoint);
                    startActivity(intent);
                }
            }
        });

    }
    public void clickCloset (View v){
        Intent it = new Intent(this, Closet.class);
        startActivity(it);
    }
    public class CustomAdapter2 extends BaseAdapter {
        private String[] imageNames;
        private  int[] imagePhoto;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter2(String[] imageNames, int[] imagePhoto, Context context) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView =layoutInflater.inflate(R.layout.store_row_items, parent, false);
            }

            TextView itemName = convertView.findViewById(R.id.itemName);
            ImageView imageView  = convertView.findViewById(R.id.imageView);


            itemName.setText(imageNames[position]);
            imageView.setImageResource(imagePhoto[position]);
            return convertView;
        }

    }
}



