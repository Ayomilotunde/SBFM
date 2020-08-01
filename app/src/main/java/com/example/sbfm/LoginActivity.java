package com.example.sbfm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginemail, loginpassword;
    private Button login;
    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mDialog=new ProgressDialog(this);

        loginemail = findViewById(R.id.loginMail);
        loginpassword  = findViewById(R.id.loginPassword);
        login = findViewById(R.id.login);

            }


            public void loginAccount(){

                String mEmail=loginemail.getText().toString().trim();
                String mPassword= loginpassword.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)){
                    loginemail.setError("Required field");
                    return;
                }

                if (TextUtils.isEmpty(mPassword)){
                    loginpassword.setError("Required field");
                    return;

                }

                mDialog.setMessage("Processing..");
                mDialog.setCancelable(false);
                mDialog.show();
                mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    mDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),MessageActivity.class));

                                }else {
                                    mDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Incorrect Username/Password", Toast.LENGTH_SHORT).show();
                                }



                            }
                        });
            }

    public void Login(View view) {
        loginAccount();
    }

    public void Members(View view) {
        startActivity(new Intent(this, StartActivity.class));
    }
}