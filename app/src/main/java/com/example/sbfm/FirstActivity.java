package com.example.sbfm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {

    private Button submit, log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        log = findViewById(R.id.view_edit);
        submit = findViewById(R.id.submit);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToFirst2();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToLogin();
            }
        });
    }

    private void sendToLogin() {
        Intent i = new Intent(FirstActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void sendToFirst2() {
        Intent i = new Intent(FirstActivity.this, First2Activity.class);
        startActivity(i);
        finish();
    }
}