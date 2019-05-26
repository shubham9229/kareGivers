package com.example.karegivers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText upemail;
    EditText uppassword;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.signupactivity);
        FirebaseApp.initializeApp(signUpActivity.this);
        upemail=(EditText)findViewById(R.id.emaileditsignup);
        uppassword=(EditText)findViewById(R.id.passwordeditsignup);
        mAuth = FirebaseAuth.getInstance();
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        findViewById(R.id.signUpButton).setOnClickListener(this);
        findViewById(R.id.signUpTextView).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signUpButton:
                registerUser();
                break;

            case R.id.signUpTextView:
                finish();
                startActivity(new Intent(this,signInActivity.class));
                break;
        }
    }

    private void registerUser() {
        String signupemail=upemail.getText().toString().trim();
        String signuppassword=uppassword.getText().toString().trim();
        if(signupemail.isEmpty())
        {
            upemail.setError(" E-mail is empty ");
            upemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(signupemail).matches())
        {
            upemail.setError("valid E-mail is required ");
            upemail.requestFocus();
            return;
        }
        if(signuppassword.isEmpty())
        {
            uppassword.setError("Password is Empty");
            uppassword.requestFocus();
            return;
        }
        if(signuppassword.length()<6)
        {
            uppassword.setError("minimum reqiured 6 characters");
            uppassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(signupemail,signuppassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    Intent intent=new Intent(signUpActivity.this,DetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(),"E-MAIL ID ALREADY REGISTERED",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
