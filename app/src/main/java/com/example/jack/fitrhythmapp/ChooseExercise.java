package com.example.jack.fitrhythmapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class ChooseExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercise);

        ImageButton walking = (ImageButton)findViewById(R.id.walking);
        walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseExercise.this, ChooseIntensity.class);
                intent.putExtra("type", "walking");
                startActivity(intent);
            }
        });

        ImageButton running = (ImageButton)findViewById(R.id.running);
        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseExercise.this,ChooseIntensity.class);
                intent.putExtra("type", "running");
                startActivity(intent);
            }
        });

        ImageButton push_ups = (ImageButton)findViewById(R.id.push_ups);
        push_ups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseExercise.this,ChooseIntensity.class);
                intent.putExtra("type", "push_ups");
                startActivity(intent);
            }
        });

        ImageButton sit_ups = (ImageButton)findViewById(R.id.sit_ups);
        sit_ups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseExercise.this, ChooseIntensity.class);
                intent.putExtra("type", "sit_ups");
                startActivity(intent);
            }
        });

        ImageButton back = (ImageButton)findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseExercise.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
