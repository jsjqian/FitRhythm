package com.example.jack.fitrhythmapp;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.lang.Thread;

import org.w3c.dom.Text;

import java.lang.Math;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class RunningActivity extends AppCompatActivity implements SensorEventListener {

    private float mLastX, mLastY, mLastZ;
    private double max_Volume = 1300;
    private double magnitude_total;
    private double current_mag, previous_mag;
    private boolean mInitialized;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ArrayList<Double> magnitudes = new ArrayList<Double>();
    private final float NOISE = (float) 2.0;
    private double threshold = 2;
    private double max_mag;
    private String workout;
    private ArrayList<String> quotes = new ArrayList<String>();
    private Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        quotes.add("''Pain is weakness leaving the body.''");
        quotes.add("''Sore today, strong tomorrow.''");
        quotes.add("''Harder, better, faster, stronger.''");
        quotes.add("''Better sore than sorry.''");
        quotes.add("''Sore. The most satisfying pain.''");
        String quote;
        int num = (int)(Math.random() * 5);
        quote = quotes.get(num);
        TextView quotey = (TextView)findViewById(R.id.quote);
        quotey.setText(quote);
        switch(getIntent().getSerializableExtra("type").toString()) {
            case("walking"):
                max_mag = 4;
                workout = "walking";
                break;
            case("running"):
                max_mag = 22;
                workout = "running";
                break;
            case("push_ups"):
                max_mag = 5;
                workout = "push ups";
                break;
            case("sit_ups"):
                max_mag = 3.5;
                workout = "sit ups";
                break;
        }

        switch(getIntent().getSerializableExtra("intensity").toString()) {
            case("easy"):
                max_mag *= .8;
                break;
            case("medium"):
                break;
            case("hard"):
                max_mag *= 1.2;
                break;
        }

        TextView type = (TextView)findViewById(R.id.type);
        TextView intense = (TextView)findViewById(R.id.intensity);
        type.setText(workout);
        intense.setText(getIntent().getSerializableExtra("intensity").toString());

        mInitialized = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Button restart = (Button)findViewById(R.id.restart_button);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunningActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        final Button startstop = (Button)findViewById(R.id.start_stop_button);
        startstop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                switch (startstop.getText().toString()) {
                    case ("start workout"): {
                        startstop.setText("stop workout");
                        int volume = (int) (max_Volume * .6);
                        audio.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
                        previous_mag = magnitude_total;
                        break;
                    }
                    case ("stop workout"): {
                        startstop.setText("start workout");
                        audio.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                        break;
                    }
                }
            }
        });



        SeekBar seek = (SeekBar)findViewById(R.id.seek_bar);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                double percent = Double.valueOf(progress);
                max_Volume = percent * 0.01 * maxVolume;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

    }


    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // can be safely ignored for this demo
    }

    public void onSensorChanged(SensorEvent event) {
        ImageView iv = (ImageView)findViewById(R.id.image);
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
//            tvX.setText("0.0");
//            tvY.setText("0.0");
//            tvZ.setText("0.0");
            mInitialized = true;
        } else {
            float deltaX = Math.abs(mLastX - x);
            float deltaY = Math.abs(mLastY - y);
            float deltaZ = Math.abs(mLastZ - z);
            if (deltaX < NOISE) deltaX = (float)0.0;
            if (deltaY < NOISE) deltaY = (float)0.0;
            if (deltaZ < NOISE) deltaZ = (float)0.0;
            mLastX = x;
            mLastY = y;
            mLastZ = z;
//            tvX.setText(Float.toString(deltaX));
//            tvY.setText(Float.toString(deltaY));
//            tvZ.setText(Float.toString(deltaZ));
            double magnitude = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));
            magnitudes.add(magnitude);
            if (magnitudes.size() == 15) {
                double total = 0;
                for(int i = 0; i < 15; i++) {
                        total += magnitudes.get(i);
                }
                magnitude_total = total/15;
                magnitudes.clear();
                final Button startstop = (Button)findViewById(R.id.start_stop_button);
                if(startstop.getText().toString() == "stop workout") {
                    current_mag = magnitude_total;
                    AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
                    if (current_mag > max_mag) {
                        audio.setStreamVolume(AudioManager.STREAM_MUSIC, (int) max_Volume, 0);
                    } else {
                        audio.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (current_mag * max_Volume / max_mag), 0);
                    }
                }

                AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
                int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                int volume = (int) (currentVolume *100 / maxVolume);
            }
        }

    }
}
