package com.example.hometraise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class gridNextPage extends AppCompatActivity {
    private TextView count;
    private SensorManager sensorManager;
    private float acceleration;
    private float previousZ, currentZ;
    private int squats;


    SeekBar seekBar;
    int threshold;
    int flag;
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
    }
    private SensorEventListener stepDetector = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                //float x = event.values[0];
               //float y = event.values[1];
                float z = event.values[2];
                currentZ = z;

                if (flag == 0 && currentZ > 14) {
                    // 1/2 이벤트발생!!
                    flag++;
                    previousZ = currentZ;
                }
                else if(flag == 1 && currentZ < 6) {

                    flag = 0;
                    squats++;
                    count.setText(String.valueOf(squats));
                }
                //gx.setText(String.valueOf(x));
                //gy.setText(String.valueOf(y));
                //gz.setText(String.valueOf(z));
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