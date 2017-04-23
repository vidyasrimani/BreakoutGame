package com.example.shrutibidada.breakoutgame;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class BreakOutActivity extends AppCompatActivity implements SensorEventListener {
    public static int tiltDirection = 0;
    GameScreenLayout gameScreenLayout;
    boolean firstReadingDone = false;
    long lastSensorReading = 0;
    private float lastX, lastY, lastZ;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Thread accelerometerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);
        gameScreenLayout = new GameScreenLayout(this, size);
        setContentView(gameScreenLayout);
        try {
            readInputData();
        } catch (IOException ioe) {

        }


    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (GameView.isBallMoving)
            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                //if(!firstReadingDone  || (System.currentTimeMillis() - lastSensorReading) > 50) {
                lastSensorReading = System.currentTimeMillis();
                firstReadingDone = true;
                float x = sensorEvent.values[0];
                Ball.tiltValue = x;
                if (x > 0) {
                    tiltDirection = -1;
                    Ball.isTilted = true;
                } else if (x < 0) {
                    tiltDirection = 1;
                    Ball.isTilted = true;
                } else {
                    Ball.isTilted = false;
                }
            }
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        gameScreenLayout.resume();

    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        gameScreenLayout.pause();
    }


    public void readInputData() throws IOException {
        File file = new File(this.getFilesDir(), "highscores");
        HighScoreUtil.highScores = new ArrayList<HighScore>();
        if (!file.exists()) {
            file.createNewFile();
        }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] highScoreDetails = line.split("\\t");
            HighScore highScore = new HighScore();
            highScore.setName(highScoreDetails[0]);
            highScore.setTime(highScoreDetails[1]);
            HighScoreUtil.highScores.add(highScore);
        }
        Collections.sort(HighScoreUtil.highScores);
    }

}
