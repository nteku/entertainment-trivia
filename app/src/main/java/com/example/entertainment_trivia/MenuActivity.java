package com.example.entertainment_trivia;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    private Button movieButton;
    private Button sportButton;
    private Button musicButton;
    private Button tvButton;
    private Button geographyButton;
    private Button techButton;
    private Button signOutButton;
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
            account = (Account) intent.getSerializableExtra(getResources().getString(R.string.account));
            name.setText(" Hello " + account.getUserName() + ",");
            score.setText("Score: " + account.getScore());
        }

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

        signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MenuActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
    }

    public void openActivity(Class activity, View view){
        Intent intent = new Intent(view.getContext(), activity);
        intent.putExtra("account", account);
        startActivity(intent);
    }
}