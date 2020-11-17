package com.example.hometraise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class gridNextPage extends AppCompatActivity {
    private TextView count;
    private TextView goaltext;
    private SensorManager sensorManager;
    private float acceleration;
    private float previousZ, currentZ;
    private int  squats=0;

    private ProgressBar progressBar;
    private TextView progressText;
    int i=1;

    EditText eText;
    Button btn;
    int num=0;
    int goal;
    int flag;
    int squats_progress=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_detail);
        Intent intent = getIntent();
        count = (TextView) findViewById(R.id.count);
        previousZ = currentZ = squats = 0;
        acceleration = 0.0f;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(stepDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

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
                progressText.setText("0");
                progressBar.setProgress(0);
                squats = 0;

            }
        });


    }
    private SensorEventListener stepDetector = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                float z = event.values[2];
                currentZ = z;

                if (flag == 0 && currentZ > 14) {
                    // 1/2 이벤트발생!!
                    flag++;
                    previousZ = currentZ;
                }
                else if(flag == 1 && currentZ < 6 &&previousZ>14) {

                    flag = 0;
                    squats++;
                    count.setText(String.valueOf(squats));

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(squats_progress<=100){

                                progressText.setText(String.valueOf(squats));
                                squats_progress = squats_progress+100/goal; //실수 처리 필요!
                                progressBar.setProgress(squats_progress);



                            }
                            else{
                                handler.removeCallbacks(this);
                            }
                        }
                    },200);
                }

            }

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    //reset 버튼용
    public void resetSteps(View v) {
        squats = 0;
        count.setText(String.valueOf(squats));
    }


}