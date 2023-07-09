package com.example.entertainment_trivia.menu;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.entertainment_trivia.loginandregistration.MainActivity;
import com.example.entertainment_trivia.R;
import com.example.entertainment_trivia.account.Account;
import com.example.entertainment_trivia.geography.GeographyActivity;
import com.example.entertainment_trivia.movies.MovieActivity;
import com.example.entertainment_trivia.music.MusicActivity;
import com.example.entertainment_trivia.sports.SportsActivity;
import com.example.entertainment_trivia.technology.TechnologyActivity;
import com.example.entertainment_trivia.tv.TVActivity;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Class: MenuActivity.java
 * Purpose: A way for the user to choose a topic they want to be questioned on.
 * @author Nathan Teku
 */
public class MenuActivity extends AppCompatActivity
{

    // private attributes needed for this class
    private ImageButton movieButton;
    private ImageButton sportButton;
    private ImageButton musicButton;
    private ImageButton tvButton;
    private ImageButton geographyButton;
    private ImageButton techButton;
    private Button signOutButton;
    private TextView name;
    private TextView score;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // hiding top bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_menu);

        // catching the ID's of the private attributes from the xml file
        name = findViewById(R.id.name);
        score = findViewById(R.id.score);

        // capturing Intent
        Intent intent = getIntent();

        // if the intent exists, retrieve account object that was serialized and display name and score of user
        if (intent != null)
        {
            account = (Account) intent.getSerializableExtra(getResources().getString(R.string.account));
            name.setText(" Hello " + account.getUserName() + ",");
            score.setText("Score: " + account.getScore());
        }

        // capturing id of movie button and if clicked, goes into MovieActivity
        movieButton = findViewById(R.id.movieButton);
        movieButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                openActivity(MovieActivity.class,view);
            }
        });

        // capturing id of sports button and if clicked, goes into SportsActivity
        sportButton = findViewById(R.id.sportButton);
        sportButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                openActivity(SportsActivity.class,view);
            }
        });

        // capturing id of music button and if clicked, goes into MusicActivity
        musicButton = findViewById(R.id.musicButton);
        musicButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                openActivity(MusicActivity.class,view);
            }
        });

        // capturing id of tv button and if clicked, goes into TVActivity
        tvButton = findViewById(R.id.tvButton);
        tvButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                openActivity(TVActivity.class,view);
            }
        });

        // capturing id of geography button and if clicked, goes into GeographyActivity
        geographyButton = findViewById(R.id.geographyButton);
        geographyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                openActivity(GeographyActivity.class,view);
            }
        });

        // capturing id of technology button and if clicked, goes into TechnologyActivity
        techButton = findViewById(R.id.technologyButton);
        techButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                openActivity(TechnologyActivity.class,view);
            }
        });

        // capturing id of sign out button and if clicked, signs user out and redirects to MainActivity
        signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MenuActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
    }

    /**
     * Method: openActivity
     * Purpose: Loads to new Activity
     * @param activity
     * @param view
     */
    public void openActivity(Class activity, View view)
    {
        // creates Intent of specific class
        Intent intent = new Intent(view.getContext(), activity);
        // serializes account object
        intent.putExtra("account", account);
        // loads object to new activity
        startActivity(intent);
    }
}