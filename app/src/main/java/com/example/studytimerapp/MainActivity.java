package com.example.studytimerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    String timeValue, timeTask, task, previousTimeKey, previousTaskKey, previousTask;
    TextView timerText, timerView;
    ImageButton pause, start, stop;
    EditText taskName;
    Timer timer;
    TimerTask timerTask;
    double time = 0.0, previousTime = 0.0;
    boolean timerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find by layout
        timerText = findViewById(R.id.studyTimeText);
        timerView = findViewById(R.id.studyTextView);
        pause = findViewById(R.id.pauseImageButton);
        start = findViewById(R.id.playImageButton);
        stop = findViewById(R.id.stopImageButton);
        taskName = findViewById(R.id.editTextPersonName);

        // start a new instance of the timer
        timer = new Timer();

        if (savedInstanceState != null){
            // this is setting the next value of time
            time = savedInstanceState.getDouble(timeValue);
            task = savedInstanceState.getString(timeTask);
            previousTime = savedInstanceState.getDouble(previousTimeKey);
            previousTask = savedInstanceState.getString(previousTaskKey);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putDouble(timeValue, time);
        //outState.putString(timeTask, task);
        //outState.putDouble(previousTimeKey, previousTime);
    }

    public void startButtonPushed(View view){
        task = taskName.getText().toString();
        timerView.setText("you spent " + previousTime + " on the task " + previousTask);
        if (timerStarted == false){
            timerStarted = true;
            startTimer();
        }
    }

    public void pauseButtonPushed(View view){
        timerStarted = false;
        timerTask.cancel();
        task = taskName.getText().toString();
    }

    public void stopButtonPushed(View view){
        // stops the timer
        timerStarted = false;
        timerTask.cancel();

        // save time and job text
        // sets the string variable task to the value of the taskName
        task = taskName.getText().toString();
        previousTime = time;
        previousTask = task;
    }

    private void startTimer(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private String getTimerText(){
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = (rounded % 86400) / 3600;

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours){
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }
}