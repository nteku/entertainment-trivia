package com.example.entertainment_trivia.loginandregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.entertainment_trivia.R;
import com.example.entertainment_trivia.account.Account;
import com.example.entertainment_trivia.menu.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;

/**
 * Class:   MainActivity.java
 * Purpose: This activity is meant as a login screen for a user
 * @author  Nathan Teku
 */
public class MainActivity extends AppCompatActivity
{
    // private attributes needed for this class
    private Button loginButton;
    private TextView registeringLink;
    private EditText email;
    private EditText password;
    private Account account;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // hiding action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // creating view
        setContentView(R.layout.activity_main);

        // catching the ID's of the private attributes from the xml file
        loginButton = findViewById(R.id.loginButton);
        registeringLink = findViewById(R.id.registrationLink);
        email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword);
        progressBar = findViewById(R.id.progressBar);

        // setting progress bar invisible
        progressBar.setVisibility(View.INVISIBLE);

        // capturing the authentication of Firebase
        fAuth = FirebaseAuth.getInstance();

        // if user wants register a new account
        registeringLink.setOnClickListener( (v) ->
        {
            progressBar.setVisibility(View.VISIBLE);

            // going to LoginActivity link
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });

        // if the user is already logged in
        if (fAuth.getCurrentUser() != null)
        {
            // retrieving the object from Firebase
            FirebaseDatabase.getInstance().getReference().child(getResources().getString(R.string.users)).child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    // saving the object and going to the MenuActivity with account being passed through
                    account = (snapshot.getValue(Account.class));
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra(getResources().getString(R.string.account), account);
                    startActivity(intent);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.successful_Message),Toast.LENGTH_SHORT).show();
                }
            });
        }

        // if the button to log in is clicked
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // if there are any credentials that are empty
                if (!emptyCredential())
                {
                    // authenticate the email and password being inputted to Firebase
                    fAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {

                                // set the visible progress bar to visible
                                progressBar.setVisibility(View.VISIBLE);

                                // retrieve the object from Firebase Realtime Database
                                FirebaseDatabase.getInstance().getReference().child(getResources().getString(R.string.users)).child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot)
                                    {
                                        // saving the object and going to the MenuActivity with account being passed through
                                        account = (snapshot.getValue(Account.class));
                                        Intent intent = new Intent(v.getContext(), MenuActivity.class);
                                        intent.putExtra(getResources().getString(R.string.account), account);
                                        startActivity(intent);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error)
                                    {
                                    }
                                });
                            }
                            else{
                                Toast.makeText(MainActivity.this,getResources().getString(R.string.incorrect_Credentials),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * Method:  emptyCredential()
     * Purpose: Detects if either the password or email slots are left empty when logging in
     * @return
     */
    public boolean emptyCredential()
    {
        // if both the email and password slots are empty, trigger an error
        if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty())
        {
              email.setError(getResources().getString(R.string.empty_Email));
              password.setError(getResources().getString(R.string.empty_Password));
              return true;
        }
        else
        {
            // if only the email is left empty, trigger an error
            if (email.getText().toString().isEmpty())
            {
                email.setError(getResources().getString(R.string.empty_Email));
                return true;
            }
            // if only the password is left empty, trigger an error
            if (password.getText().toString().isEmpty())
            {
                password.setError(getResources().getString(R.string.empty_Password));
                return true;
            }

        }
        // if nothing is empty
        return false;
    }
}