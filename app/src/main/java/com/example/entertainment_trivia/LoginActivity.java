package com.example.entertainment_trivia;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    private Button signUpButton;
    private EditText name;
    private EditText email;
    private EditText password;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);


        signUpButton = findViewById(R.id.signUp);
        name = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (!emptyCredential()) {
                                                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                    if (task.isSuccessful()) {
                                                                        account = new Account(name.getText().toString(), email.getText().toString(), password.getText().toString(), "0");
                                                                        FirebaseDatabase.getInstance().getReference("users")
                                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            Toast.makeText(LoginActivity.this, "Successfully registered user.", Toast.LENGTH_SHORT).show();
                                                                                            Intent intent = new Intent(v.getContext(), MenuActivity.class);
                                                                                            intent.putExtra("account", account);
                                                                                            startActivity(intent);
                                                                                        } else {
                                                                                            Toast.makeText(LoginActivity.this, "Unable to register user.", Toast.LENGTH_SHORT).show();
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

    public boolean emptyCredential(){

        if ( name.getText().toString().isEmpty() && email.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
            name.setError("Name is empty");
            email.setError("Email is empty.");
            password.setError("Password is empty.");
            return true;
        }
        else{
            if (name.getText().toString().isEmpty()){
                name.setError("Name is empty.");
                return true;
            }
            if (email.getText().toString().isEmpty()){
                email.setError("Email is empty.");
                return true;
            }
            if (password.getText().toString().isEmpty()){
                password.setError("Password is empty.");
                return true;
            }
        }
        return false;
    }

}