package com.example.hometraise;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CountAction extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView progressText;
    private TextView goaltext;
    int i=1;

    EditText eText;
    Button btn;
    int num=1;
    int goal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_progressbar);



        progressBar = findViewById(R.id.progress_bar);
        progressText= findViewById(R.id.progress_text);
        goaltext= (TextView)findViewById(R.id.goal);
        eText = (EditText)findViewById(R.id.edittext_progressbar);
       // eText.setText("EditText 사용하기");
        btn = (Button)findViewById(R.id.button_progressbar);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String str = eText.getText().toString();
                goal = Integer.parseInt(str);
                goaltext.setText(String.valueOf(goal));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(i<=100){
                            progressText.setText(""+num);
                            progressBar.setProgress(i);
                            num++;

                            i = i+100/goal; //실수 처리 필요!

                    /*
                    i는 센서에서 카운트 한 개수 받아오고
                    setProgress는 goal의 개수가 g라고 하면 progress는 100/g만큼 증가
                    이때 if문의 조건은 goal의 개수<=동작한 개수
                     */

                        }
                        else{
                            handler.removeCallbacks(this);
                        }
                    }
                },200);
            }
        });



    }
}
