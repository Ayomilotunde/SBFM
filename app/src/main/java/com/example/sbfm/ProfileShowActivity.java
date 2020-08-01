package com.example.sbfm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileShowActivity extends AppCompatActivity {

    private CircleImageView image;
    private TextView Profile, Post;

    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef, FormRef;
    private String CurrentUSerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_show);

        Init();
    }

    private void Init() {
        image = findViewById(R.id.image);
        Profile = findViewById(R.id.Profile);
        Post = findViewById(R.id.post);

        mProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        CurrentUSerID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUSerID);

        UserRef.child(CurrentUSerID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {

                        String Name = snapshot.child("Name").getValue().toString();
                        String Post1 = snapshot.child("Post").getValue().toString();
                        String image1 = snapshot.child("image").getValue().toString();



                        Picasso.get().load(image1).into(image);

                        Profile.setText(Name);
                        Post.setText(Post1);



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}