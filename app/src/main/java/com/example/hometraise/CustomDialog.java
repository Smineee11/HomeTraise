package com.example.hometraise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private Button Ok;
    private Button Cancel;
    private EditText Edit;

    private CustomDialogListener customDialogListener;

    //뒤로가기 버튼 방지
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public CustomDialog(Context context){
        super(context);
        this.context = context;
    }
    //인터페이스 설정
    interface  CustomDialogListener{
        void onOkClicked(String goal_count);
        void onCancelClicked();
    }
    //호출할 리스너 초기화
    public void setDialogListener (CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        setCancelable(false);
        //init
        Ok = (Button)findViewById(R.id.okbutton);
        Cancel=(Button)findViewById(R.id.cancelButton);
        Edit = (EditText)findViewById(R.id.message);

        //버튼 클릭 리스너
        Ok.setOnClickListener(this);
        Cancel.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id. okbutton:
                String goal = Edit.getText().toString();

                if(Integer.parseInt(goal)>0 &&Integer.parseInt(goal)<=100 ) {
                    //인터페이스의 함수를 호출하여 변수에 저장된 값들을 Activity에 전달
                    customDialogListener.onOkClicked(goal);
                    dismiss();

                }
                else {
                    AlertDialog.Builder alertDialogBuiler = new AlertDialog.Builder(context);
                    alertDialogBuiler.setTitle("목표 설정 오류");
                    alertDialogBuiler.setMessage("1에서 100 사이의 값을 입력해주십시오!!!");
                    alertDialogBuiler.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                        //Edit.setText("1에서 100 사이의 값을 입력해주십시오!!!");
                    });
                    AlertDialog alertDialog = alertDialogBuiler.create();
                    alertDialog.show();
                }

                break;
            case R.id.cancelButton:
                customDialogListener.onCancelClicked();
                dismiss();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }


}
