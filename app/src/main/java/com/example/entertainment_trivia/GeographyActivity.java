package com.example.entertainment_trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;

public class GeographyActivity extends AppCompatActivity {

    private TextView heading;
    private TextView question;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button nextButton;
    private String answer;
    private int questionsCorrect;
    private int questionsDone = 0;
    private  List <List<String>> log;
    private List <Integer> images;
    private ImageView currentImage;


    private final int TOTAL_QUESTIONS = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_geography);

        log = new ArrayList<>();
        images = new ArrayList<>();

        initializeLists();
        heading = findViewById(R.id.heading);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        nextButton = findViewById(R.id.nextButton);
        currentImage = findViewById(R.id.image);



        question.setText(log.get(questionsDone).get(0));
        option1.setText( log.get(questionsDone).get(1));
        option2.setText(log.get(questionsDone).get(2));
        option3.setText(log.get(questionsDone).get(3));
        answer = log.get(questionsDone).get(4);



        option1.setOnClickListener((v) -> {
            if (option1.getText().equals(answer) ) {
                option1.setBackgroundColor(Color.parseColor("#00FF00"));
                option1.setText("Correct");
                questionsCorrect++;
                questionsDone++;
                disableButtons();
            }
            else{
                option1.setBackgroundColor(Color.parseColor("#FF0000"));
                option1.setText("Incorrect");
                questionsDone++;
                disableButtons();
                //  setBackToDefault();


            }
        });

        option2.setOnClickListener((v) -> {
            if (option2.getText().equals(answer) ) {
                option2.setBackgroundColor(Color.parseColor("#00FF00"));
                option2.setText("Correct");
                questionsCorrect++;
                questionsDone++;
                disableButtons();
            }
            else{
                option2.setBackgroundColor(Color.parseColor("#FF0000"));
                option2.setText("Incorrect");
                questionsDone++;
                disableButtons();
                //setBackToDefault();


            }
        });

        option3.setOnClickListener((v) -> {
            if (option3.getText().equals(answer) ) {
                option3.setBackgroundColor(Color.parseColor("#00FF00"));
                option3.setText("Correct");
                questionsCorrect++;
                questionsDone++;
                disableButtons();
            }
            else{
                option3.setBackgroundColor(Color.parseColor("#FF0000"));
                option3.setText("Incorrect");
                questionsDone++;
                disableButtons();


            }
        });


        nextButton.setOnClickListener((v) ->{
            if (questionsDone == TOTAL_QUESTIONS){
                gameResult();
            }
            else{
                setBackToDefault();
            }


        });


    }

    public void disableButtons(){
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
    }
    public void initializeLists(){
        List<String> question1 = new ArrayList<>();
        question1.add("What is this country?");
        question1.add("Oman");
        question1.add("Romania");
        question1.add("Bangladesh");
        question1.add("Oman");

        List<String> question2 = new ArrayList<>();
        question2.add("What continent is Algeria in?");
        question2.add("Asia");
        question2.add("Europe");
        question2.add("Africa");
        question2.add( "Africa");

        List <String> question3 = new ArrayList<>();
        question3.add("Which country borders Italy?");
        question3.add("Germany");
        question3.add("Austria");
        question3.add("Greece");
        question3.add("Austria");


        log.add(question1);
        log.add(question2);
        log.add(question3);


        images.add(R.drawable.oman);
        images.add(R.drawable.algeria);
        images.add(R.drawable.italy);


    }

    public void gameResult(){



        nextButton.setText("Done");
        option1.setVisibility(View.GONE);
        option2.setVisibility(View.GONE);
        option3.setVisibility(View.GONE);
        currentImage.setVisibility(View.GONE);
        if (  (double) (questionsCorrect/TOTAL_QUESTIONS) > 0.3){
            question.setVisibility(View.GONE);
            heading.setText("You got " + questionsCorrect + " out of " + TOTAL_QUESTIONS + " correct. Good job!");


        }
        else{
            question.setText("You got " + questionsCorrect + " out of " + TOTAL_QUESTIONS + " correct. Better luck next time.");
        }


        nextButton.setOnClickListener((v)-> {
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
        });




    }


    public void setBackToDefault(){


        currentImage.setImageResource(images.get(questionsDone));

        question.setText(log.get(questionsDone).get(0));
        option1.setText( log.get(questionsDone).get(1));
        option2.setText(log.get(questionsDone).get(2));
        option3.setText(log.get(questionsDone).get(3));

        option1.setBackgroundColor(Color.parseColor("#7471C3"));
        option2.setBackgroundColor(Color.parseColor("#7471C3"));
        option3.setBackgroundColor(Color.parseColor("#7471C3"));



        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        answer = log.get(questionsDone).get(4);

    }
}



