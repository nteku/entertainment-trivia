package com.example.entertainment_trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private TextView loggingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
       // animateBackground();

        startButton = findViewById(R.id.startButton);
        loggingIn = findViewById(R.id.logInLink);

        loggingIn.setOnClickListener( (v) -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        startButton.setOnClickListener((v) -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        });

    }


  public void animateBackground(){
          ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
          AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
          animationDrawable.setEnterFadeDuration(2500);
          animationDrawable.setExitFadeDuration(5000);
          animationDrawable.start();
  }
}