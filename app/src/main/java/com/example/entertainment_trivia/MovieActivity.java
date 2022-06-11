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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;

public class MovieActivity extends AppCompatActivity {


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
    private int totalQuestions;
    private HashMap <Integer,List <String>> info;
    private List <String> currentLog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_movie);

        log = new ArrayList<>();
        images = new ArrayList<>();
        info = new HashMap<>();

        initializeLists();
        heading = findViewById(R.id.heading);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        nextButton = findViewById(R.id.nextButton);
        currentImage = findViewById(R.id.image);


        currentImage.setImageResource( images.get(questionsDone));
        question.setText(info.get(images.get(questionsDone)).get(0));
        option1.setText(info.get(images.get(questionsDone)).get(1));
        option2.setText(info.get(images.get(questionsDone)).get(2));
        option3.setText(info.get(images.get(questionsDone)).get(3));
        answer = info.get(images.get(questionsDone)).get(4);


        /*
        question.setText(log.get(questionsDone).get(0));
        option1.setText( log.get(questionsDone).get(1));
        option2.setText(log.get(questionsDone).get(2));
        option3.setText(log.get(questionsDone).get(3));
        answer = log.get(questionsDone).get(4);
        */


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
            if (questionsDone == totalQuestions){
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
         List<String> question2 = new ArrayList<>();
         List <String> question3 = new ArrayList<>();

         currentLog = new ArrayList<>();






        try {
            DataInputStream textFileStream = new DataInputStream(getAssets().open(String.format("movies.txt")));
            Scanner input = new Scanner(textFileStream);
            while (input.hasNextLine()) {

                String line = input.nextLine();
                if (line.equals("?")){

                    log.add(currentLog);
                    currentLog = new ArrayList<>();
                    totalQuestions++;
                }
                else{
                    currentLog.add(line);

                }
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



         /*
         question1.add("Which movie was Denzel Washington not in?");
         question1.add("Philadelphia");
         question1.add("Malcolm X");
         question1.add("Ali");
         question1.add("Ali");


        question2.add("What is the highest grossed movie franchise of all time?");
        question2.add("Star Wars");
        question2.add("Marvel Cinematic Universe");
        question2.add("Harry Potter");
        question2.add( "Marvel Cinematic Universe");


        question3.add("Based on this image, what movie did this cast star in?");
        question3.add("Scooby-Doo");
        question3.add("Mamma Mia!");
        question3.add("2 Hearts");
        question3.add("Scooby-Doo");


        log.add(question1);
        log.add(question2);
        log.add(question3);

         */
        images.add(R.drawable.denzelwashington);
        images.add( R.drawable.money);
        images.add(R.drawable.scoobydoo);


        for (int i = 0; i < log.size(); i++){
            info.put(images.get(i),log.get(i));
        }
        Collections.shuffle(images);

        // Create A HashMap that stores an integer and a list, which then have a list of integers which shuffles the order of it
        // It crashed so now do a log.debug session to see what is the issue
    }

    public void gameResult(){



                 nextButton.setText("Done");
                 option1.setVisibility(View.GONE);
                 option2.setVisibility(View.GONE);
                 option3.setVisibility(View.GONE);
                 currentImage.setVisibility(View.GONE);
                 if (  (double) (questionsCorrect/totalQuestions) > 0.5){
                     question.setVisibility(View.GONE);
                     heading.setText("You got " + questionsCorrect + " out of " + totalQuestions + " correct. Good job!");

                 }
                 else{
                     question.setText("You got " + questionsCorrect + " out of " + totalQuestions + " correct. Better luck next time.");
                 }


                 nextButton.setOnClickListener((v)-> {
                     Intent intent = new Intent(this,MenuActivity.class);
                     startActivity(intent);
                 });




    }


    public void setBackToDefault(){


        currentImage.setImageResource(images.get(questionsDone));
        question.setText(info.get(images.get(questionsDone)).get(0));
        option1.setText( info.get(images.get(questionsDone)).get(1));
        option2.setText(info.get(images.get(questionsDone)).get(2));
        option3.setText(info.get(images.get(questionsDone)).get(3));
        /*
        question.setText(log.get(questionsDone).get(0));
        option1.setText( log.get(questionsDone).get(1));
        option2.setText(log.get(questionsDone).get(2));
        option3.setText(log.get(questionsDone).get(3));

         */

        option1.setBackgroundColor(Color.parseColor("#7471C3"));
        option2.setBackgroundColor(Color.parseColor("#7471C3"));
        option3.setBackgroundColor(Color.parseColor("#7471C3"));



        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        //answer = log.get(questionsDone).get(4);
        answer = info.get(images.get(questionsDone)).get(4);
    }
}



