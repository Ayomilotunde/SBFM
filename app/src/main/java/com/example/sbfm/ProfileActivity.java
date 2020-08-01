package com.example.sbfm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private Button saveForm;

    private TextInputEditText Name, Post;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef, FormRef;
    private String CurrentUSerID;
    private CircleImageView Upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Init();
    }
    private void Init()
    {
        mProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        CurrentUSerID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUSerID);

        saveForm = findViewById(R.id.saveFormW);

        Name = findViewById(R.id.aaa);
        Post = findViewById(R.id.bbb);
        Upload = findViewById(R.id.imageUpload);



        saveAccountInformation();
    }

    private void saveAccountInformation() {
        saveForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Name1 = Name.getText().toString();
                String Post1 = Post.getText().toString();


                if (TextUtils.isEmpty(Name1)) {

                    Toast.makeText(ProfileActivity.this, "Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Post1)) {

                    Toast.makeText(ProfileActivity.this, "Post ?", Toast.LENGTH_SHORT).show();
                }
                else {

                    mProgress.show();
                    mProgress.setCanceledOnTouchOutside(true);


                    HashMap userMap= new HashMap();

                    userMap.put("UserId", CurrentUSerID);
                    userMap.put("Name", Name1);
                    userMap.put("Post", Post1);

                    UserRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                sendToProduct();
                                Toast.makeText(ProfileActivity.this, "Form Saved", Toast.LENGTH_LONG).show();
                                mProgress.dismiss();
                            } else {

                                String Message = Objects.requireNonNull(task.getException()).getMessage();
                                Toast.makeText(ProfileActivity.this, "Error Occured When Creating Account" + Message, Toast.LENGTH_LONG).show();
                                mProgress.dismiss();
                            }

                        }
                    });

                }
            }
        });
    }

    private void sendToProduct()
    {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }
}