package com.example.entertainment_trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class SportsActivity extends AppCompatActivity {

    private int bufferSize;
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
    private WebView gif ;

    private final int TOTAL_QUESTIONS = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

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
        // gif = findViewById(R.id.gif);
        //  gif.setVisibility(View.GONE);

        //displayingInfo();



        question.setText(log.get(questionsDone).get(0));
        option1.setText( log.get(questionsDone).get(1));
        option2.setText(log.get(questionsDone).get(2));
        option3.setText(log.get(questionsDone).get(3));
        answer = log.get(questionsDone).get(4);
        // answer = log.get(questionsDone).get(1);


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
        question1.add("Which NBA player has the most championships?");
        question1.add("Kobe Bryant");
        question1.add("Bill Russell");
        question1.add("Michael Jordan");
        question1.add("Bill Russell");

        List<String> question2 = new ArrayList<>();
        question2.add("Which NFL Team is the only team to lose all games in a season?");
        question2.add("Detroit Lions");
        question2.add("Cleveland Browns");
        question2.add("Houston Texans");
        question2.add( "Detroit Lions");

        List <String> question3 = new ArrayList<>();
        question3.add("Which MLB Team is not based in New York?");
        question3.add("Mets");
        question3.add("Yankees");
        question3.add("Marlins");
        question3.add("Marlins");


        log.add(question1);
        log.add(question2);
        log.add(question3);

        /*
        ImageView imageOne = new ImageView(this);
        imageOne.setImageResource(R.drawable.denzelwashington);

        ImageView imageTwo = new ImageView(this);
        imageTwo.setImageResource(R.drawable.money);

        ImageView imageThree = new ImageView(this);
        imageThree.setImageResource(R.drawable.scoobydoo);
    */

        images.add(R.drawable.rings);
        images.add( R.drawable.cries);
        images.add(R.drawable.nyc);


    }
    /*
    public void readingJson(){
       String jsonString;

       try{
           InputStream input = getAssets().open("./json-files/movies.json");
           bufferSize = input.available();
           byte [] buf = new byte[bufferSize];
           input.read(buf);
           input.close();

           jsonString = new String(buf,"UTF-8");
           JSONArray objectArray = new JSONArray(jsonString);

           for (int i = 0; i < objectArray.length(); i++){
               heading = findViewById(R.id.heading);
               heading.setText("Question " + (i+ 1));
               JSONObject obj = objectArray.getJSONObject(i);
               displayingInfo(obj);

           }

           //JSONObject obj = objectArray.getJSONObject(0);
          //displayingInfo(obj);

       }
       catch(IOException e){

       }
       catch(JSONException e){

       }
    }
    */
    public void gameResult(){



        nextButton.setText("Done");
        option1.setVisibility(View.GONE);
        option2.setVisibility(View.GONE);
        option3.setVisibility(View.GONE);
        currentImage.setVisibility(View.GONE);
        if (  (double) (questionsCorrect/TOTAL_QUESTIONS) > 0.3){
            question.setVisibility(View.GONE);
            heading.setText("You got " + questionsCorrect + " out of " + TOTAL_QUESTIONS + " correct. Good job!");

                     /*
                     gif.setVisibility(View.VISIBLE);
                     gif.getSettings().setJavaScriptEnabled(true);
                     gif.setWebViewClient(new WebViewClient());
                     gif.loadUrl("https://www.icegif.com/wp-content/uploads/good-job-icegif-20.gif");
                     */


            //   GifImageView gif = findViewById(R.id.gif);
            //   gif.setImageResource(R.drawable.eddie);
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



