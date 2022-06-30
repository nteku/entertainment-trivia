package com.example.entertainment_trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private Button movieButton;
    private Button sportButton;
    private Button musicButton;
    private Button tvButton;
    private Button geographyButton;
    private Button techButton;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);
        name = findViewById(R.id.name);
        Intent intent = getIntent();

        final String userName = intent.getStringExtra("name");

        name.setText(" Hello " + userName + ",");
        animationBackground();

        movieButton = findViewById(R.id.movieButton);
        movieButton.setOnClickListener((v) ->{
           openActivity(MovieActivity.class);
        });

        sportButton = findViewById(R.id.sportButton);
        sportButton.setOnClickListener((v) ->{
            openActivity(SportsActivity.class);
        });

        musicButton = findViewById(R.id.musicButton);
        musicButton.setOnClickListener((v) ->{
            openActivity(MusicActivity.class);
        });

        tvButton = findViewById(R.id.tvButton);
        tvButton.setOnClickListener((v) ->{
            openActivity(TVActivity.class);
        });

        geographyButton = findViewById(R.id.geographyButton);
        geographyButton.setOnClickListener((v) ->{
            openActivity(GeographyActivity.class);
        });
        techButton = findViewById(R.id.technologyButton);
        techButton.setOnClickListener((v) ->{
            openActivity(TechnologyActivity.class);
        });


    }

    public void animationBackground(){
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }

    public void openActivity(Class activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }
}