package com.example.shredder.imview.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shredder.imview.R;
import com.example.shredder.imview.Utils.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static maes.tech.intentanim.CustomIntent.customType;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout signUp_textInputEmail;
    private TextInputLayout signUp_textInputPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        Button signUp_btn  = findViewById(R.id.signUp_signUp_btn);
        signUp_textInputEmail = findViewById(R.id.signUp_text_input_email);
        signUp_textInputPassword = findViewById(R.id.signUp_text_input_password);



        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailInput = signUp_textInputEmail.getEditText().getText().toString().trim();
                String passwordInput = signUp_textInputPassword.getEditText().getText().toString().trim();

                if (!emailValidator(emailInput) | !passwordValidator(passwordInput)) {
                    return;
                }
                else
                {
                    mAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                        customType(SignUpActivity.this,"fadein-to-fadeout");
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(SignUpActivity.this, "Falied to Create Account", Toast.LENGTH_SHORT).show();
                                    }
                                    
                                }
                            });
                }
            }
        });

    }

    private boolean emailValidator(String emailInput )
    {
        if(emailInput.isEmpty())
        {
            signUp_textInputEmail.setError("Enter Email!");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
        {
            signUp_textInputEmail.setError("Enter Valid Email!");
            return false;
        }
        else
        {
            signUp_textInputEmail.setError(null);
            return true;
        }

    }

    private boolean passwordValidator(String passwordInput )
    {
        if (passwordInput.isEmpty()) {
            signUp_textInputPassword.setError("Field can't be empty");
            return false;
        } else if (!Util.PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            signUp_textInputPassword.setError("Password too weak");
            return false;
        } else {
            signUp_textInputPassword.setError(null);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
        customType(SignUpActivity.this,"right-to-left");
        finish();
    }
}
