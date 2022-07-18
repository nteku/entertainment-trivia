package com.example.entertainment_trivia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class MenuActivity extends AppCompatActivity {

    private Button movieButton;
    private Button sportButton;
    private Button musicButton;
    private Button tvButton;
    private Button geographyButton;
    private Button techButton;
    private TextView name;
    private TextView score;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);
        name = findViewById(R.id.name);
        score = findViewById(R.id.score);


        Intent intent = getIntent();

        if (intent != null) {
            account = (Account) intent.getSerializableExtra("account");
            name.setText(" Hello " + account.getUserName() + ",");
            score.setText("Score: " + account.getScore());
        }


        animationBackground();

        movieButton = findViewById(R.id.movieButton);
        movieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivity(MovieActivity.class,view);
            }
        });

        sportButton = findViewById(R.id.sportButton);
        sportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivity(SportsActivity.class,view);
            }
        });

        musicButton = findViewById(R.id.musicButton);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivity(MusicActivity.class,view);
            }
        });

        tvButton = findViewById(R.id.tvButton);
        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivity(TVActivity.class,view);
            }
        });

        geographyButton = findViewById(R.id.geographyButton);
        geographyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivity(GeographyActivity.class,view);
            }
        });
        techButton = findViewById(R.id.technologyButton);
        techButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivity(TechnologyActivity.class,view);
            }
        });


    }

    public void updateAccount(){

        FirebaseDatabase.getInstance().getReference("account").setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MenuActivity.this, "Success",Toast.LENGTH_SHORT);
                }
            }
        });
    }
    public void animationBackground(){
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }

    public void openActivity(Class activity, View view){
        Intent intent = new Intent(view.getContext(), activity);
        intent.putExtra("account", account);
        startActivity(intent);
    }
}