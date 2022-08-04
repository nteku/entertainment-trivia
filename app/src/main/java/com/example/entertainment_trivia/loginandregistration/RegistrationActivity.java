package com.example.entertainment_trivia.loginandregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.entertainment_trivia.R;
import com.example.entertainment_trivia.account.Account;
import com.example.entertainment_trivia.menu.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

/**
 * Class:   RegistrationActivity.java
 * Purpose: Registers a user to Firebase.
 * @author  Nathan Teku
 */
public class RegistrationActivity extends AppCompatActivity
{
    // private attributes needed for this class
    private Button signUpButton;
    private EditText name;
    private EditText email;
    private EditText password;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // hiding top bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // creating view
        setContentView(R.layout.activity_register);

        // catching the ID's of the private attributes from the xml file
        signUpButton = findViewById(R.id.signUp);
        name = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        progressBar = findViewById(R.id.progressBar);

        // setting progress bar to invisible
        progressBar.setVisibility(View.INVISIBLE);

        // capturing the authentication of Firebase
        fAuth = FirebaseAuth.getInstance();

        // if the button to log in is clicked
        signUpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!emptyCredential())
                {
                    // creating a account on user based on email and password through Firebase
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        // set the visible progress bar to visible
                                        progressBar.setVisibility(View.VISIBLE);

                                        // creating an account based on the info of the user
                                        account = new Account(name.getText().toString(), email.getText().toString(), password.getText().toString(), "0");

                                        // placing account object to Firebase Realtime Database
                                        FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.users))
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(account).addOnCompleteListener(new OnCompleteListener<Void>()
                                                {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task)
                                                    {
                                                        if (task.isSuccessful())
                                                        {
                                                            // create toast message and going to the MenuActivity with account being serialized
                                                            Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.successful_register),Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(v.getContext(), MenuActivity.class);
                                                            intent.putExtra(getResources().getString(R.string.account), account);
                                                            startActivity(intent);
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.failed_register), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });
    }

    /**
     * Method:  emptyCredential()
     * Purpose: Detects if either the password or email slots are left empty when logging in.
     * @return
     */
    public boolean emptyCredential()
    {
        // if both the email and password slots are empty, trigger an error
        if ( name.getText().toString().isEmpty() && email.getText().toString().isEmpty() && password.getText().toString().isEmpty())
        {
            name.setError(getResources().getString(R.string.empty_Name));
            email.setError(getResources().getString(R.string.empty_Email));
            password.setError(getResources().getString(R.string.empty_Password));
            return true;
        }
        else
        {
            // if only the name is left empty, trigger an error
            if (name.getText().toString().isEmpty())
            {
                name.setError(getResources().getString(R.string.empty_Name));
                return true;
            }
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