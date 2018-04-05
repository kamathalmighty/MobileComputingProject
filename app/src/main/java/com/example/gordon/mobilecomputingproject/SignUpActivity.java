package com.example.gordon.mobilecomputingproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button mSignUpButton;
    private EditText mFirstnameField;
    private EditText mLastnameField;
    private EditText mEmailField;
    private EditText mPasswordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        //Fields
        mFirstnameField = findViewById(R.id.su_fieldFirstname);
        mLastnameField = findViewById(R.id.su_fieldLastname);
        mEmailField = findViewById(R.id.su_fieldEmail);
        mPasswordField = findViewById(R.id.su_fieldPassword);

        //Button
        mSignUpButton = findViewById(R.id.su_btnSignUp);
        mSignUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String email = mEmailField.getText().toString();
                String pass = mPasswordField.getText().toString();
                Log.w("test","Email: " + email + " Password: " + pass);
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
                    createUser(email,pass);
                }
            }
        });

    }
    public void createUser(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("test", "createUserWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("test", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Sign Up Failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}
