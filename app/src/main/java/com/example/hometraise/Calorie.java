package com.example.hometraise;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import java.util.HashMap;
import java.util.Map;


public class Calorie extends AppCompatActivity {
    private TextView timer, calorie;
    private Button stopButton, restartButton;
    private Context context;

    //임의로 설정해둔 칼로리 계산 변수
   private double kg = 55;
    private double MET = 8;

    public static final int INIT = 0;   //처음
    public static final int RUN = 1;    //실행중
    public static final int PAUSE = 2;  //정지

    public static int status = INIT;

    private long baseTime,pauseTime;

    private TextView count;
    private TextView goaltext;
    private SensorManager sensorManager;
    private float acceleration;
    private float previousZ, currentZ;
    private int  squats=0;

    private ProgressBar progressBar;
    private TextView progressText;
    int i=1;

    int num=0;
    int goal = 0;
    int flag;
    int squats_progress=0;
    int total_count=0;
    int max, min =0;
    String id;
    int mypoint = 0;
    MediaPlayer mediaPlayer;
    private DatabaseReference mDatabase;



    public void onBackPressed() {
        //super.onBackPressed();
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Characters").child(id);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CharacterData data = snapshot.getValue(CharacterData.class);
                Log.d("NAME ", data.name);
                Log.d("POINT ", String.valueOf(data.point));

                if(data == null)
                    System.out.println("Undefined user");

                else {
                    data.point = data.point + total_count;

                    Log.d("CHANGE ", String.valueOf(data.point));
                }

                String key = dbRef.child("Characters").push().getKey();
                CharacterData newdata = new CharacterData(data.name, data.point, data.clothes);
                Map<String, Object> newdataValues = newdata.toMap();

                dbRef.setValue(newdataValues);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: ", error.getMessage());
            }
        });


        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.exercise_detail);

        timer = (TextView)findViewById(R.id.time);
        stopButton = (Button)findViewById(R.id.stop);
        restartButton = (Button)findViewById(R.id.restart);
        restartButton.setEnabled(false);    //일단 재시작 버튼 비활성화
        calorie = (TextView)findViewById(R.id.calorie);

        stopButton.setOnClickListener(onClickListener);
        restartButton.setOnClickListener(onClickListener);

        Intent intent = getIntent();
        max =  intent.getIntExtra("max", 1);
        min =  intent.getIntExtra("min", 1);
        id = intent.getExtras().getString("id");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        this.context = context;
        count = (TextView) findViewById(R.id.count);
        previousZ = currentZ = squats = 0;
        acceleration = 0.0f;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(stepDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        progressBar = findViewById(R.id.progress_bar);
        progressText= findViewById(R.id.progress_text);
        goaltext= (TextView)findViewById(R.id.goal); //커스텀 다이얼로그에서 값 받아와서 값 띄우기
        // eText.setText("EditText 사용하기");
        final CustomDialog customDialog = new CustomDialog(this);
        customDialog.show();
        customDialog.setDialogListener(new CustomDialog.CustomDialogListener() {
            @Override
            public void onOkClicked(String goal_count) {
                goaltext.setText(goal_count);
                goal = Integer.parseInt(goal_count);
            }

            @Override
            public void onCancelClicked() {
//                Intent intent = new Intent(Calorie.this, grid.class);
//                startActivity(intent);
//                onBackPressed();
                finish();
            }
        });


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

    private SensorEventListener stepDetector = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (status == RUN) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                    float z = event.values[2];
                    currentZ = z;

                    if (flag == 0 && currentZ > max) {
                        // 1/2 이벤트발생!!
                        Toast.makeText(Calorie.this, "max: "+max, Toast.LENGTH_SHORT).show();
                        flag++;
                        previousZ = currentZ;
                    }
                    else if (flag == 1 && currentZ < min) {
//                        Toast.makeText(Calorie.this, "min: "+min, Toast.LENGTH_SHORT).show();
                        flag = 0;
                        squats++;
                        total_count++;
                        count.setText(String.valueOf(total_count));
                        //이렇게 무식하게 짜야하는 것인가,,,??
                        if(squats %10 ==1) {
                            mediaPlayer = MediaPlayer.create(Calorie.this, R.raw.music1);
                            mediaPlayer.start();
                        }
                        else if (squats %10 == 2) {
                            mediaPlayer = MediaPlayer.create(Calorie.this, R.raw.music2);
                            mediaPlayer.start();
                        }
                        else if (squats %10 == 3) {
                            mediaPlayer = MediaPlayer.create(Calorie.this, R.raw.music3);
                            mediaPlayer.start();
                        }
                        else if (squats %10 == 4) {
                            mediaPlayer = MediaPlayer.create(Calorie.this, R.raw.music4);
                            mediaPlayer.start();
                        }
                        else if (squats %10 == 5) {
                            mediaPlayer = MediaPlayer.create(Calorie.this, R.raw.music5);
                            mediaPlayer.start();
                        }
                        else if (squats %10 == 6) {
                            mediaPlayer = MediaPlayer.create(Calorie.this, R.raw.music6);
                            mediaPlayer.start();
                        }
                        else if (squats %10 == 7) {
                            mediaPlayer = MediaPlayer.create(Calorie.this, R.raw.music7);
                            mediaPlayer.start();
                        }
                        else if (squats %10 == 8) {
                            mediaPlayer = MediaPlayer.create(Calorie.this, R.raw.music8);
                            mediaPlayer.start();
                        }
                        else if (squats %10 == 9) {
                            mediaPlayer = MediaPlayer.create(Calorie.this, R.raw.music9);
                            mediaPlayer.start();
                        }
                        else if (squats %10 == 0) {
                            mediaPlayer = MediaPlayer.create(Calorie.this, R.raw.music0);
                            mediaPlayer.start();
                        }
                        if (squats_progress <= 100) {
                            progressText.setText(String.valueOf(squats));
                            if (squats == goal) {
                                squats_progress = 100;
                                progressBar.setProgress(squats_progress);
                                //팝업
                                recButton();
                            }
                            else {
                                squats_progress = squats_progress + 100 / goal; //실수 처리 필요!
                                progressBar.setProgress(squats_progress);
                            }


                        }

                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void staButton(){
        switch (status){
            case INIT:
                //어플리케이션 실행 후 실제 경과 시간
                baseTime = SystemClock.elapsedRealtime();

                handler.sendEmptyMessage(0);
                stopButton.setText("STOP");

                status = RUN;
                break;
            case RUN:
                handler.removeMessages(0);

                pauseTime = SystemClock.elapsedRealtime();

                stopButton.setText("START");
                restartButton.setText("RESTART");
                restartButton.setEnabled(true);


                status = PAUSE;
                break;
            case PAUSE:
                long reStart = SystemClock.elapsedRealtime();
                baseTime += (reStart - pauseTime);

                handler.sendEmptyMessage(0);

                stopButton.setText("STOP");
                restartButton.setEnabled(false);


                status = RUN;

        }
    }

    private void recButton(){
        stopButton.setText("START");
        restartButton.setText("RESTART");
        restartButton.setEnabled(false);

        timer.setText("00:00");

        baseTime = 0;
        pauseTime = 0;

        progressText.setText("0");
        progressBar.setProgress(0);
        squats = 0;
        squats_progress = 0;

        status = INIT;

        CustomDialog dialog = new CustomDialog(this);
        dialog.show();
        dialog.setDialogListener(new CustomDialog.CustomDialogListener() {
            @Override
            public void onOkClicked(String goal_count) {
                goaltext.setText(goal_count);
                goal = Integer.parseInt(goal_count);
            }

            @Override
            public void onCancelClicked() {

            }
        });

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

    private long getMinute(){
        long nowTime = SystemClock.elapsedRealtime();
        long overTime = nowTime - baseTime;

        long m = overTime/1000/60;

        return m;
    }

    Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            timer.setText((getTime()));
            long minute = getMinute();
            double result = 0.0175 * kg * MET * minute;
            String cal = String.format("%.0f",result);
            calorie.setText(cal);

            handler.sendEmptyMessage(0);
        }
    };

}
