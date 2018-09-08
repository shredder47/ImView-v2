package com.example.shredder.imview.Activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shredder.imview.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static maes.tech.intentanim.CustomIntent.customType;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    int wrongPassCount=0;

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputPassword = findViewById(R.id.text_input_password);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signIn_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                customType(LoginActivity.this,"left-to-right");
                finish();
            }
        });

        findViewById(R.id.signIn_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailInput = textInputEmail.getEditText().getText().toString().trim();
                String passwordInput = textInputPassword.getEditText().getText().toString().trim();

                if (!emailInput.isEmpty() && !passwordInput.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                customType(LoginActivity.this,"fadein-to-fadeout");

                            }
                            if(!task.isSuccessful())
                            {
                                wrongPassCount++;

                                if (wrongPassCount==3)
                                {
                                    new CountDownTimer(30000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            Button singIn_btn = findViewById(R.id.signIn_btn);
                                            singIn_btn.setText(String.valueOf(+millisUntilFinished / 1000));
                                            singIn_btn.setEnabled(false);
                                        }
                                        @Override
                                        public void onFinish() {
                                            Button singIn_btn = findViewById(R.id.signIn_btn);
                                            singIn_btn.setText("Sign In");
                                            singIn_btn.setEnabled(true);
                                            wrongPassCount=0;
                                        }
                                    }.start();
                                }



                            }

                        }
                    });
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Enter Fields Properly", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser!=null)
        {
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            customType(LoginActivity.this,"fadein-to-fadeout");
            finish();
        }
    }

}


/**
 * left-to-right
 * right-to-left
 * bottom-to-up
 * up-to-bottom
 * fadein-to-fadeout
 * rotateout-to-rotatein
 */