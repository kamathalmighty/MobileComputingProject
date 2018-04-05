package com.example.gordon.mobilecomputingproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity{

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignInButton;
    private Button mSignUpButton;
    private Button mSkipButton;
    private TextView mSignInError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        //Fields
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);

        //Buttons
        mSkipButton = findViewById(R.id.btnSkip);
        mSkipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                gotoMenu();
            }
        });
        mSignUpButton = findViewById(R.id.btnSignUp);
        mSignUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                gotoSignUpPage();
            }
        });
        mSignInButton = findViewById(R.id.btnSignIn);
        mSignInButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String pass = mPasswordField.getText().toString();
                String email = mEmailField.getText().toString();
                if(!TextUtils.isEmpty(pass) && !TextUtils.isEmpty(email)) {
                    signInUser(email, pass);
                }
            }
        });

        //Text

        mSignInError = findViewById(R.id.txtSignInError);
        mSignInError.setTextColor(Color.RED);
        mSignInError.setVisibility(View.INVISIBLE);


    }
    private void skipButtonListener() {
    }
    @Override
    public void onStart(){
        super.onStart();
        //Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            gotoMenu();
        }
    }
    public void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("test", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("test", "signInWithEmail:failure", task.getException());
                            Toast errToast = Toast.makeText(LoginActivity.this,"Authentication Failed",
                                    Toast.LENGTH_SHORT);
                            errToast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                            errToast.show();
                        }

                        // ...
                    }
                });
    }
    private void gotoSignUpPage(){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
    private void gotoMenu() {
        Log.w("test","Going to menu!");
    }
}
