package com.example.hometraise;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private Button Ok;
    private Button Cancel;
    private EditText Edit;

    private CustomDialogListener customDialogListener;

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

    }
    @Override
    public void onClick(View v){
        
    }


}
