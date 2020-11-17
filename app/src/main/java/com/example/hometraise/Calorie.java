package com.example.hometraise;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Calorie extends AppCompatActivity {
    private TextView timer, calorie;
    private Button stopButton, restartButton;

    private double kg = 75;
    private double MET = 8;

    public static final int INIT = 0;   //처음
    public static final int RUN = 1;    //실행중
    public static final int PAUSE = 2;  //정지

    public static int status = INIT;

    private long baseTime,pauseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_detail);

        timer = (TextView)findViewById(R.id.time);
        stopButton = (Button)findViewById(R.id.stop);
        restartButton = (Button)findViewById(R.id.restart);
        restartButton.setEnabled(false);    //일단 재시작 버튼 비활성화
        calorie = (TextView)findViewById(R.id.calorie);

        stopButton.setOnClickListener(onClickListener);
        restartButton.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.stop:
                    staButton();
                    break;
                case R.id.restart:
                    recButton();
            }
        }
    };

    private void staButton(){
        switch (status){
            case INIT:
                //어플리케이션 실행 후 실제 경과 시간
                baseTime = SystemClock.elapsedRealtime();

                handler.sendEmptyMessage(0);
                stopButton.setText("STOP");
                restartButton.setEnabled(true);

                status = RUN;
                break;
            case RUN:
                handler.removeMessages(0);

                pauseTime = SystemClock.elapsedRealtime();

                stopButton.setText("START");
                restartButton.setText("RESTART");

                status = PAUSE;
                break;
            case PAUSE:
                long reStart = SystemClock.elapsedRealtime();
                baseTime += (reStart - pauseTime);

                handler.sendEmptyMessage(0);

                stopButton.setText("STOP");

                status = RUN;

        }
    }

    private void recButton(){
        stopButton.setText("START");
        restartButton.setText("RESTART");
        restartButton.setEnabled(false);

        timer.setText("0.0");

        baseTime = 0;
        pauseTime = 0;

        status = INIT;
    }

    private String getTime(){
        //경과된 시간 체크

        long nowTime = SystemClock.elapsedRealtime();
        //시스템이 부팅된 이후의 시간?
        long overTime = nowTime - baseTime;

        long m = overTime/1000/60;
        long s = (overTime/1000)%60;

        String recTime = String.format("%02d:%02d",m,s);

        return recTime;
    }

    Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            timer.setText((getTime()));

            handler.sendEmptyMessage(0);
        }
    };

}
