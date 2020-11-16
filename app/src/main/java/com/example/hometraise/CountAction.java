package com.example.hometraise;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CountAction extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView progressText;
    int i=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_progressbar);

        progressBar = findViewById(R.id.progress_bar);
        progressText= findViewById(R.id.progress_text);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(i<=100){
                    progressText.setText(""+i);
                    progressBar.setProgress(i);
                    i++;

                    /*
                    i는 센서에서 카운트 한 개수 받아오고
                    setProgress는 goal의 개수가 g라고 하면 progress는 100/g만큼 증가
                    이때 if문의 조건은 goal의 개수<=동작한 개수
                     */
                    handler.postDelayed(this,200);
                }
                else{
                    handler.removeCallbacks(this);
                }
            }
        },200);
    }
}
