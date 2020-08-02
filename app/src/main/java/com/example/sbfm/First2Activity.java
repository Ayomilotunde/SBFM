package com.example.sbfm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class First2Activity extends AppCompatActivity {

    private ProgressBar startProgress;
    private TextView startFeedbackText;

    private FirebaseAuth mFirebaseAuth;
    private static final String START_TAG = "Start Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first2);

        startProgress = findViewById(R.id.start_progress);
        startFeedbackText = findViewById(R.id.start_feedback);

        mFirebaseAuth = FirebaseAuth.getInstance();

        startFeedbackText.setText("Checking User Account...");
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser == null)
        {
            //create new account
            startFeedbackText.setText("Creating Account...");
            mFirebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){

                        startFeedbackText.setText("Account Created...");
                        Intent i = new Intent(First2Activity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }else
                    {
                        Log.d(START_TAG, "Start Log : " + task.getException());
                    }
                }
            });
        }else
        {
            //navigate to home page
            startFeedbackText.setText("Logged in...");
            Intent i = new Intent(First2Activity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

}