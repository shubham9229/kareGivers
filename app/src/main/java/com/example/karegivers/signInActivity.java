package com.example.karegivers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signInActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inemail;
    EditText inpassword;
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
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.signinactivity);
        mAuth=FirebaseAuth.getInstance();
        findViewById(R.id.movetosignup).setOnClickListener(this);
        findViewById(R.id.signInButton).setOnClickListener(this);
        inemail=(EditText)findViewById(R.id.emaileditsignin);
        inpassword=(EditText)findViewById(R.id.passwordeditsignin);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.movetosignup:
                finish();
                startActivity(new Intent(this,entermechanic.class));
                break;
            case R.id.signInButton:
                entertheApp();
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null)
        {
//            Intent intent=new Intent(signInActivity.this,chooseService.class);
//            finish();
//            startActivity(intent);
        }
    }

    private void entertheApp() {
        String signinemail=inemail.getText().toString().trim();
        String signinpassword=inpassword.getText().toString().trim();
        if(signinemail.isEmpty())
        {
            inemail.setError("E-MAIL IS REQUIRED");
            inemail.requestFocus();
            return;
        }
        if(signinpassword.isEmpty())
        {
            inpassword.setError("PASSWORD IS REQUIRED");
            inpassword.requestFocus();
            return;
        }
        if(signinpassword.length()<6)
        {
            inpassword.setError("NOT A VALID PASSWORD, MUST BE OF MORE THAN 6 CHARACTERS");
            inpassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(signinemail).matches())
        {
            inemail.setError("NOT A VALID E-MAIL ID");
            inemail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(signinemail,signinpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                   Intent intent=new Intent(signInActivity.this,chooseService.class);
                    finish();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
