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



        /*
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    String userId = fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fStore.collection("users").document(userId);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("fullName",name.getText().toString());
                                    user.put("email",email.getText().toString());
                                    user.put("password",password.getText().toString());
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG,"User profile is created");
                                        }
                                    });
                                    Intent intent = new Intent(v.getContext(), MenuActivity.class);
                                    intent.putExtra("name",name.getText().toString());
                                    startActivity(intent);

                                }
                                else{
                                    Toast.makeText(LoginActivity.this,"Task failed",Toast.LENGTH_SHORT).show();
                                }
                            }




                        });


            }

        });
    */

        signUpButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
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
                                                                                        Toast.makeText(LoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                                                                        Intent intent = new Intent(v.getContext(), MenuActivity.class);
                                                                                        intent.putExtra("account", account);
                                                                                        startActivity(intent);
                                                                                    }else{
                                                                                        Toast.makeText(LoginActivity.this,"Task failed",Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });


                                                                }
                                                            }
                                                        });
                                            }
                                        });



    }


    public void animationBackground(){
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

    }





}