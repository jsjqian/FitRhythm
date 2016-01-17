package com.example.jack.fitrhythmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class ChooseIntensity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_intensity);

        final String exercise_type = getIntent().getSerializableExtra("type").toString();

        ImageButton easy = (ImageButton)findViewById(R.id.easy);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseIntensity.this, RunningActivity.class);
                intent.putExtra("intensity", "easy");
                intent.putExtra("type", exercise_type);
                startActivity(intent);
            }
        });

        ImageButton medium = (ImageButton)findViewById(R.id.medium);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseIntensity.this,RunningActivity.class);
                intent.putExtra("intensity", "medium");
                intent.putExtra("type", exercise_type);
                startActivity(intent);
            }
        });

        ImageButton hard = (ImageButton)findViewById(R.id.hard);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseIntensity.this, RunningActivity.class);
                intent.putExtra("intensity", "hard");
                intent.putExtra("type", exercise_type);
                startActivity(intent);
            }
        });
    }

}
