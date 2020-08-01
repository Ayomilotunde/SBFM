package com.example.sbfm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText email, password;
    private Button signup;
    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        mDialog=new ProgressDialog(this);

        email = findViewById(R.id.editTextTextPersonName);
        password  = findViewById(R.id.editTextTextPassword);
        signup = findViewById(R.id.signUp);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();

            }
        });
    }

    public void createAccount(){

        String mEmail=email.getText().toString().trim();
        String mPassword= password.getText().toString().trim();

        if (TextUtils.isEmpty(mEmail)){
            email.setError("Required field");
            return;
        }

        if (TextUtils.isEmpty(mPassword)){
            password.setError("Required field");
            return;

        }

        mDialog.setMessage("Processing..");
        mDialog.setCancelable(false);
        mDialog.show();

        mAuth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"SignUp Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }

            }
        });

    }

    public void LoginAct(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}