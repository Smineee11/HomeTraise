package com.example.hometraise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class grid extends AppCompatActivity {
    GridView gridView;
    String[] names = {"squat", "lunge", "add", "add", "add", "add"};
    int[] images ={R.drawable.squat, R.drawable.lunge, R.drawable.add2, R.drawable.add2, R.drawable.add2, R.drawable.add2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_list);
        //인텐트 처리 필요
        gridView = findViewById(R.id.gridView);
        Intent it = getIntent();
        CustomAdapter customAdapter = new CustomAdapter(names, images, this);
        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = names[position];
                int selectedImage = images[position];
                Toast.makeText(grid.this, " " +position, Toast.LENGTH_SHORT).show();
                if(position == 0) {
                    startActivity(new Intent(grid.this, gridNextPage.class).putExtra("name", selectedName).putExtra("image", selectedImage));
                }
            }
            });
    }

    public class CustomAdapter extends BaseAdapter {
        private String[] imageNames;
        private  int[] imagePhoto;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(String[] imageNames, int[] imagePhoto, Context context) {
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
                convertView =layoutInflater.inflate(R.layout.exercise_row_items, parent, false);
            }

            TextView tvName = convertView.findViewById(R.id.tvName);
            ImageView imageView  = convertView.findViewById(R.id.imageView);


            tvName.setText(imageNames[position]);
            imageView.setImageResource(imagePhoto[position]);
            return convertView;
        }

    }

}