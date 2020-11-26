package com.example.hometraise;

import android.content.Context;
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

public class Closet extends AppCompatActivity {

    Button back, change;
    ImageView picture;
    GridView gridview;
    String[] names = {"c_basic","basic_2"};
    int[] images = new int[]{R.drawable.c_basic, R.drawable.ic_launcher_background };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet_detail);

        back = (Button)findViewById(R.id.closet_back);
        change = (Button)findViewById(R.id.closet_change);
        picture = (ImageView)findViewById(R.id.closet_image);
        gridview = findViewById(R.id.gridView);
        //Intent it = getIntent();
        Closet.CustomAdapter3 customAdapter = new CustomAdapter3(names, images, this);
        gridview.setAdapter(customAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = names[position];
                int selectedImage = images[position];

//                Toast.makeText(Closet.this, " " +position, Toast.LENGTH_SHORT).show();
                if(position == 0) {
                    //옷장에서 1번 옷을 고르고 change를 누르면 옷 변화

                }
                else if(position == 1) {

                }
            }
        });
    }


    public void backshop(View v){
        finish();
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
