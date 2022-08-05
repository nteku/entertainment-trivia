package com.example.entertainment_trivia.geography;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.entertainment_trivia.menu.MenuActivity;
import com.example.entertainment_trivia.R;
import com.example.entertainment_trivia.account.Account;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Class: GeographyActivity.java
 * Purpose: Activity tests user's knowledge in Geography
 * @author Nathan Teku
 */
public class GeographyActivity extends AppCompatActivity
{
    // private attributes needed for this class
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
    private int score;
    private Account account;
    private final int TWO = 2;
    private final int FOUR = 4;
    private final int SIX = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // hiding top bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_geography);

        // initializing two lists and a hash map needed for this class
        log = new ArrayList<>();
        images = new ArrayList<>();
        info = new HashMap<>();

        // capturing Intent
        Intent intent = getIntent();

        // if the intent exists, retrieve account object that was serialized
        if (intent != null)
        {
            account = (Account) intent.getSerializableExtra(getResources().getString(R.string.account));
            score = Integer.parseInt(account.getScore());
        }

        // calling method to initialize questions and images
        initializeData();

        // catching the ID's of the private attributes from the xml file
        heading = findViewById(R.id.heading);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        nextButton = findViewById(R.id.nextButton);
        currentImage = findViewById(R.id.image);

        // setting image to view
        currentImage.setImageResource( images.get((int) questionsDone));

        // setting the texts to view
        question.setText(info.get(images.get((int) questionsDone)).get(0));
        option1.setText(info.get(images.get((int) questionsDone)).get(1));
        option2.setText(info.get(images.get((int) questionsDone)).get(2));
        option3.setText(info.get(images.get((int) questionsDone)).get(3));

        // storing answer of question
        answer = info.get(images.get( (int) questionsDone)).get(4);

        // if the first option for an answer is clicked
        option1.setOnClickListener((v) ->
        {
            // if the answer is correct
            if (option1.getText().equals(answer))
            {
                // update the color of the button to green since it's correct
                option1.setBackground(getResources().getDrawable(R.drawable.correct));

                // based on how many questions user did, the score for a correct answer differs
                switch ((int) questionsDone)
                {
                    // if user is in the first question, update score and text of button
                    case 0:
                        option1.setText(getResources().getString(R.string.plusTwo));
                        score += TWO;
                        questionsCorrect++;
                        questionsDone++;
                        disableButtons();
                        break;
                    // if user is in the second question, update score and text of button
                    case 1:
                        option1.setText(getResources().getString(R.string.plusFour));
                        score += FOUR;
                        questionsCorrect++;
                        questionsDone++;
                        disableButtons();
                        break;
                    // if user is in the third question, update score and text of button
                    case 2:
                        option1.setText(getResources().getString(R.string.plusSix));
                        score += SIX;
                        questionsCorrect++;
                        questionsDone++;
                        disableButtons();
                        break;
                }
            }
            // if the user chooses the incorrect answer
            else
            {
                // update the color of the button to red since it's incorrect
                option1.setBackground(getResources().getDrawable(R.drawable.incorrect));
                option1.setText(getResources().getString(R.string.incorrect));
                questionsDone++;
                disableButtons();
            }
        });

        // if the second option for an answer is clicked
        option2.setOnClickListener((v) ->
        {
            // if the answer is correct
            if (option2.getText().equals(answer))
            {
                // update the color of the button to green since it's correct
                option2.setBackground(getResources().getDrawable(R.drawable.correct));

                // based on how many questions user did, the score for a correct answer differs
                switch ((int) questionsDone)
                {
                    // if user is in the first question, update score and text of button
                    case 0:
                        option2.setText(getResources().getString(R.string.plusTwo));
                        score += TWO;
                        questionsCorrect++;
                        questionsDone++;
                        disableButtons();
                        break;
                    // if user is in the second question, update score and text of button
                    case 1:
                        option2.setText(getResources().getString(R.string.plusFour));
                        score += FOUR;
                        questionsCorrect++;
                        questionsDone++;
                        disableButtons();
                        break;
                    // if user is in the third question, update score and text of button
                    case 2:
                        option2.setText(getResources().getString(R.string.plusSix));
                        score += SIX;
                        questionsCorrect++;
                        questionsDone++;
                        disableButtons();
                        break;
                }
            }
            // if the user chooses the incorrect answer
            else
            {
                // update the color of the button to red since it's incorrect
                option2.setBackground(getResources().getDrawable(R.drawable.incorrect));
                option2.setText(getResources().getString(R.string.incorrect));
                questionsDone++;
                disableButtons();
            }
        });

        // if the third option for an answer is clicked
        option3.setOnClickListener((v) ->
        {
            // if the answer is correct
            if (option3.getText().equals(answer))
            {
                // update the color of the button to green since it's correct
                option3.setBackground(getResources().getDrawable(R.drawable.correct));

                // based on how many questions user did, the score for a correct answer differs
                switch ((int) questionsDone)
                {
                    // if user is in the first question, update score and text of button
                    case 0:
                        option3.setText(getResources().getString(R.string.plusTwo));
                        score += TWO;
                        questionsCorrect++;
                        questionsDone++;
                        disableButtons();
                        break;
                    // if user is in the second question, update score and text of button
                    case 1:
                        option3.setText(getResources().getString(R.string.plusFour));
                        score += FOUR;
                        questionsCorrect++;
                        questionsDone++;
                        disableButtons();
                        break;
                    // if user is in the third question, update score and text of button
                    case 2:
                        option3.setText(getResources().getString(R.string.plusSix));
                        score += SIX;
                        questionsCorrect++;
                        questionsDone++;
                        disableButtons();
                        break;
                }
            }
            // if the user chooses the incorrect answer
            else
            {
                // update the color of the button to red since it's incorrect
                option3.setText(getResources().getString(R.string.incorrect));
                option3.setBackground(getResources().getDrawable(R.drawable.incorrect));
                questionsDone++;
                disableButtons();
            }
        });

        // if the next button is clicked
        nextButton.setOnClickListener((v) ->
        {
            // if user answered all questions, invoke method to summarize game results
            if (questionsDone == totalQuestions)
            {
                gameResult();
            }
            // if user has not answered all questions, invoke method to set game definitions to default
            else
            {
                setBackToDefault( );
            }
        });
    }

    /**
     * Method: disableButtons()
     * Purpose: disables buttons from being clicked
     * @return
     */
    public void disableButtons()
    {
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
    }

    /**
     * Method: initializeData()
     * Purpose: reads text file to capture questions and initializes images
     * @return
     */
    public void initializeData()
    {
        // initializing list to read a question and its choices
        currentLog = new ArrayList<>();

        try
        {
            // reading text file
            DataInputStream textFileStream = new DataInputStream(getAssets().open(String.format(getResources().getString(R.string.geography_File))));
            Scanner input = new Scanner(textFileStream);

            // reading line by line
            while (input.hasNextLine())
            {
                // capturing line
                String line = input.nextLine();

                // if set of question and choices is fully read
                if (line.equals("?"))
                {
                    // add it to the questions list
                    log.add(currentLog);
                    // reinitialize currentLog
                    currentLog = new ArrayList<>();
                    totalQuestions++;
                }
                // if its not fully read, just add to the currentLog
                else
                {
                    currentLog.add(line);
                }
            }
            // close file
            input.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // adding images to images to list
        images.add(R.drawable.oman);
        images.add( R.drawable.algeria);
        images.add(R.drawable.italy);

        // looping through to add image and question as a pair to the HashMap
        for (int i = 0; i < log.size(); i++)
        {
            info.put(images.get(i),log.get(i));
        }

        // shuffle the order of the questions
        Collections.shuffle(images);
    }

    /**
     * Method: gameResult()
     * Purpose: analyzes the user's results based on this topic
     * @return
     */
    public void gameResult()
    {
        // updating text of button
        nextButton.setText(getResources().getString(R.string.done));

        // hiding the visibility of all other buttons
        option1.setVisibility(View.GONE);
        option2.setVisibility(View.GONE);
        option3.setVisibility(View.GONE);
        currentImage.setVisibility(View.GONE);
        question.setVisibility(View.GONE);

        // calculating the percentage the user got correct
        correctPercentage = (double) (questionsCorrect/totalQuestions) * 100;

        // determining the range of the percentage to display summary
        if ((correctPercentage  >= 0) && (correctPercentage <= 59))
        {
            heading.setText("You got " + (int) questionsCorrect + " out of " + (int) totalQuestions + " correct. Try again. Updated Score: " + score);
        }
        else if ((correctPercentage >= 60) && (correctPercentage <= 69))
        {
            heading.setText("You got " + (int) questionsCorrect + " out of " + (int) totalQuestions + " correct. Not looking so good. Updated Score: " + score);
        }
        else if ((correctPercentage >= 70) && (correctPercentage <= 79))
        {
            heading.setText("You got " + (int) questionsCorrect + " out of " + (int) totalQuestions + " correct. Could do better. Updated Score: " + score);
        }
        else if ((correctPercentage >= 80)  && (correctPercentage <= 89))
        {
            heading.setText("You got " + (int) questionsCorrect + " out of " + (int) totalQuestions + " correct. Great job! Updated Score: " + score);
        }
        else
        {
            heading.setText("You got " + (int) questionsCorrect + " out of " + (int) totalQuestions +  " correct. Excellent work! Updated Score: " + score);
        }

        // when the nextButton is clicked
        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // retrieving user object from Firebase Realtime Database and updating their score
                FirebaseDatabase.getInstance().getReference().child(getResources().getString(R.string.users)).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getResources().getString(R.string.score)).setValue(String.valueOf(score));
                // updating score of account object
                account.setScore(String.valueOf(score));
                // going to the MenuActivity with account being serialized
                Intent intent = new Intent(v.getContext(), MenuActivity.class);
                intent.putExtra(getResources().getString(R.string.account), account);
                startActivity(intent);
            }
        });
    }

    /**
     * Method: setBackToDefault
     * Purpose: Setting buttons properties back to default and updating answer
     * @ return
     */
    public void setBackToDefault()
    {
        // displaying the next image
        currentImage.setImageResource(images.get((int) questionsDone));

        // updating question and the 3 options to the next set
        question.setText(info.get(images.get((int) questionsDone)).get(0));
        option1.setText( info.get(images.get((int) questionsDone)).get(1));
        option2.setText(info.get(images.get((int) questionsDone)).get(2));
        option3.setText(info.get(images.get((int) questionsDone)).get(3));

        // setting buttons' colors back to default
        option1.setBackground(getResources().getDrawable(R.drawable.gradient));
        option2.setBackground(getResources().getDrawable(R.drawable.gradient));
        option3.setBackground(getResources().getDrawable(R.drawable.gradient));

        // enabling buttons
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);

        // updating answer to the next answer for the next set
        answer = info.get(images.get((int) questionsDone)).get(4);
    }
}

