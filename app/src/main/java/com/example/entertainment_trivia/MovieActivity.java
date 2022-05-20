package com.example.entertainment_trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MovieActivity extends AppCompatActivity {

    private int bufferSize;
    private TextView heading;
    private TextView question;
    private Button option1;
    private Button option2;
    private Button option3;
    private String answer;
    private String [] questions;
    private String [] options;
    private int questionsCorrect;
    private final int TOTAL_QUESTIONS = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        readingJson();

    }

    public void initializeLists(){
        questions = new String[3];

    }
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
           /*
           for (int i = 0; i < objectArray.length(); i++){
               heading = findViewById(R.id.heading);
               heading.setText("Question " + (i+ 1));
               JSONObject obj = objectArray.getJSONObject(i);
               displayingInfo(obj);

           }
           */
           JSONObject obj = objectArray.getJSONObject(0);
           displayingInfo(obj);

       }
       catch(IOException e){

       }
       catch(JSONException e){

       }
    }

    public void displayingInfo(JSONObject obj){
          question = findViewById(R.id.question);
          option1 = findViewById(R.id.option1);
          option2 = findViewById(R.id.option2);
          option3 = findViewById(R.id.option3);
          try{
              Log.d("Object","Made it here");
              question.setText(obj.getString("question"));
              option1.setText(obj.getString("option1"));
              option2.setText(obj.getString("option2"));
              option3.setText(obj.getString("option3"));
          }
          catch(JSONException e){

          }

    }
}



