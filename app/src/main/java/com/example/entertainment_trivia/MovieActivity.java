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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class MovieActivity extends AppCompatActivity {


    private TextView heading;
    private TextView question;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button nextButton;
    private String answer;
    private double questionsCorrect;
    private double questionsDone = 0;
    private  List <List<String>> log;
    private List <Integer> images;
    private ImageView currentImage;
    private double totalQuestions;
    private HashMap <Integer,List <String>> info;
    private List <String> currentLog;
    private double correctPercentage;
    private Account account;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_movie);

        animationBackground();
        log = new ArrayList<>();
        images = new ArrayList<>();
        info = new HashMap<>();


        Intent intent = getIntent();

        if (intent != null) {
            account = (Account) intent.getSerializableExtra("account");
            score = Integer.parseInt(account.getScore());
        }

        initializeLists();
        heading = findViewById(R.id.heading);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        nextButton = findViewById(R.id.nextButton);
        currentImage = findViewById(R.id.image);


        currentImage.setImageResource( images.get((int) questionsDone));
        question.setText(info.get(images.get((int) questionsDone)).get(0));
        option1.setText(info.get(images.get((int) questionsDone)).get(1));
        option2.setText(info.get(images.get((int) questionsDone)).get(2));
        option3.setText(info.get(images.get((int) questionsDone)).get(3));
        answer = info.get(images.get( (int) questionsDone)).get(4);




        option1.setOnClickListener((v) -> {
            if (option1.getText().equals(answer) ) {
                option1.setBackground(getResources().getDrawable(R.drawable.correct));
                switch ((int) questionsDone){
                    case 0:
                        option1.setText("Correct + 2");
                        score += 2;
                        break;
                    case 1:
                        option1.setText("Correct + 4");
                        score += 4;
                        break;
                    case 2:
                        option1.setText("Correct + 6");
                        score += 6;
                        break;

                }
               // option1.setText("Correct");
                questionsCorrect++;
                questionsDone++;
                disableButtons();
            }
            else{
                option1.setBackground(getResources().getDrawable(R.drawable.incorrect));
                option1.setText("Incorrect");
                questionsDone++;
                disableButtons();



            }
        });

        option2.setOnClickListener((v) -> {
            if (option2.getText().equals(answer) ) {
                option2.setBackground(getResources().getDrawable(R.drawable.correct));
                switch ((int) questionsDone){
                    case 0:
                        option2.setText("Correct + 2");
                        score += 2;
                        break;
                    case 1:
                        option2.setText("Correct + 4");
                        score += 4;
                        break;
                    case 2:
                        option2.setText("Correct + 6");
                        score += 6;
                        break;

                }
             //   option2.setText("Correct");
                questionsCorrect++;
                questionsDone++;
                disableButtons();
            }
            else{
                option2.setBackground(getResources().getDrawable(R.drawable.incorrect));
                option2.setText("Incorrect");
                questionsDone++;
                disableButtons();



            }
        });
       // TODO : Implement the event handling correct and incorrect xml files for these buttons
        option3.setOnClickListener((v) -> {
            if (option3.getText().equals(answer) ) {
                option3.setBackground(getResources().getDrawable(R.drawable.correct));
                switch ((int) questionsDone){
                    case 0:
                        option3.setText("Correct + 2");
                        score += 2;
                        break;
                    case 1:
                        option3.setText("Correct + 4");
                        score += 4;
                        break;
                    case 2:
                        option3.setText("Correct + 6");
                        score += 6;
                        break;

                }
              //  option3.setText("Correct");
                questionsCorrect++;
                questionsDone++;
                disableButtons();
            }
            else{
                option3.setText("Incorrect");
                option3.setBackground(getResources().getDrawable(R.drawable.incorrect));
                questionsDone++;
                disableButtons();


            }
        });


        nextButton.setOnClickListener((v) ->{
            if (questionsDone == totalQuestions){
                gameResult();
            }
            else{
                setBackToDefault( );
            }


        });


    }

    public void disableButtons(){
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
    }
    public void initializeLists(){

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

        images.add(R.drawable.denzelwashington);
        images.add( R.drawable.money);
        images.add(R.drawable.scoobydoo);


        for (int i = 0; i < log.size(); i++){
            info.put(images.get(i),log.get(i));
        }
        Collections.shuffle(images);


    }

    public void gameResult(){

                 nextButton.setText("Done");
                 option1.setVisibility(View.GONE);
                 option2.setVisibility(View.GONE);
                 option3.setVisibility(View.GONE);
                 currentImage.setVisibility(View.GONE);
                 question.setVisibility(View.GONE);


                 correctPercentage = (double) (questionsCorrect/totalQuestions) * 100;



                 if ( (correctPercentage  >= 0) && (correctPercentage <= 59)){
                     heading.setText("You got " + (int) questionsCorrect + " out of " + (int) totalQuestions + " correct. Try again. Updated Score: " + score);
                 }
                 else if ( (correctPercentage >= 60) && (correctPercentage <= 69)){
                     heading.setText("You got " + (int) questionsCorrect + " out of " + (int) totalQuestions + " correct. Not looking so good. Updated Score: " + score);
                 }
                 else if ( (correctPercentage >= 70) && (correctPercentage <= 79)){
                     heading.setText("You got " + (int) questionsCorrect + " out of " + (int) totalQuestions + " correct. Could do better. Updated Score: " + score);
                 }
                 else if ( (correctPercentage >= 80)  && (correctPercentage <= 89)){
                     heading.setText("You got " + (int) questionsCorrect + " out of " + (int) totalQuestions + " correct. Great job! Updated Score: " + score);
                 }
                 else{
                     heading.setText("You got " + (int) questionsCorrect + " out of " + (int) totalQuestions +  " correct. Excellent work! Updated Score: " + score);
                 }


                 nextButton.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                         FirebaseDatabase.getInstance().getReference("account").setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if (task.isSuccessful()) {
                                     Toast.makeText(MovieActivity.this, "Success",Toast.LENGTH_SHORT);
                                 }
                             }
                         });

                         Intent intent = new Intent(view.getContext(), MenuActivity.class);
                         account.setScore(String.valueOf(score));
                         intent.putExtra("account", account);
                         startActivity(intent);
                     }
                 });

    }


    public void setBackToDefault( ){


        currentImage.setImageResource(images.get((int) questionsDone));
        question.setText(info.get(images.get((int) questionsDone)).get(0));
        option1.setText( info.get(images.get((int) questionsDone)).get(1));
        option2.setText(info.get(images.get((int) questionsDone)).get(2));
        option3.setText(info.get(images.get((int) questionsDone)).get(3));

        option1.setBackground(getResources().getDrawable(R.drawable.gradient));
        option2.setBackground(getResources().getDrawable(R.drawable.gradient));
        option3.setBackground(getResources().getDrawable(R.drawable.gradient));

        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        answer = info.get(images.get((int) questionsDone)).get(4);

    }



    public void animationBackground(){
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }
}



