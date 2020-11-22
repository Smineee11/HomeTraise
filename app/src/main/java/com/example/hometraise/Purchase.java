package com.example.hometraise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Purchase extends AppCompatActivity {

    Button purchaseB, cancleB;
    ImageView picture;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase);

        purchaseB = (Button)findViewById(R.id.purchase_ok);
        cancleB = (Button)findViewById(R.id.purchase_cancle);
        picture = (ImageView)findViewById(R.id.purchase_image);
        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            String selectedName  = intent.getStringExtra("name");
            int selectedImage = intent.getIntExtra("image", 0);

            picture.setImageResource(selectedImage);
        }
    }
    public void purchaseClicked(View v){

        //Toast.makeText(this, "버튼 눌림", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //if 구매 가능한 경우
        alertDialogBuilder.setTitle("구매 완료");
        alertDialogBuilder.setMessage("구매가 완료되었습니다. ");
        alertDialogBuilder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        //else 구매 불가능한 경우
//        alertDialogBuilder.setTitle("Warning");
//        alertDialogBuilder.setMessage("구매하실 수 없습니다.. ");
//        alertDialogBuilder.setPositiveButton("네", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//
//        });
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
    }

    public void cancelClicked(View v){
        finish();
    }

}
