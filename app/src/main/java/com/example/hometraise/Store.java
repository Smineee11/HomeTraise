package com.example.hometraise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Store extends AppCompatActivity {
    GridView gridview;
    Button closet;
    String[] names = {"c_basic","basic_2"};
    int[] images = new int[]{R.drawable.c_basic, R.drawable.ic_launcher_background };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail);
        closet = (Button)findViewById(R.id.closet_button);
        gridview = findViewById(R.id.gridView);
        //Intent it = getIntent();
        CustomAdapter2 customAdapter = new CustomAdapter2(names, images, this);
        gridview.setAdapter(customAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = names[position];
                int selectedImage = images[position];

//                Toast.makeText(Store.this, " " +position, Toast.LENGTH_SHORT).show();
                if(position == 0) {
                    Intent intent = new Intent(Store.this, Purchase.class);
                    intent.putExtra("name", selectedName);
                    intent.putExtra("image", selectedImage);
                    startActivity(intent);

                }
                else if(position == 1) {
                    Intent intent = new Intent(Store.this, Purchase.class);
                    intent.putExtra("name", selectedName);
                    intent.putExtra("image", selectedImage);
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



