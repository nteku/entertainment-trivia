package com.example.entertainment_trivia;

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
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private TextView loggingIn;
    private  EditText email;
    private  EditText password;
    private  Account account;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        loggingIn = findViewById(R.id.logInLink);
        email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword);
        progressBar = findViewById(R.id.progressBar);


        progressBar.setVisibility(View.INVISIBLE);

        loggingIn.setOnClickListener( (v) -> {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        if (FirebaseAuth.getInstance().getCurrentUser() != null){

            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    account = (snapshot.getValue(Account.class));
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("account", account);
                    startActivity(intent);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this,"Successfully Signed Out.",Toast.LENGTH_SHORT).show();
                }
            });
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!emptyCredential()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.VISIBLE);
                                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        account = (snapshot.getValue(Account.class));
                                        Intent intent = new Intent(v.getContext(), MenuActivity.class);
                                        intent.putExtra("account", account);
                                        startActivity(intent);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Email or Password is incorrect",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

        public boolean emptyCredential(){
            if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                email.setError("Email is empty.");
                password.setError("Password is empty.");
                return true;
            }
            else{
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