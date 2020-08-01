package com.example.sbfm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

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
    private StorageReference userProfileImagesRef;
    private Button Save;
    private Uri imageUri;

    final static int Gallery_Pick = 1;
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
        userProfileImagesRef = FirebaseStorage.getInstance().getReference().child("Profile Images");


        saveForm = findViewById(R.id.saveFormW);

        Name = findViewById(R.id.aaa);
        Post = findViewById(R.id.bbb);
        Upload = findViewById(R.id.imageUpload);
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Pick );
//                CropImage.activity().
//                        setAspectRatio(1, 1).start(ProfileActivity.this);

            }
        });
        saveAccountInformation();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==Gallery_Pick && resultCode == RESULT_OK && data != null) {



            imageUri = data.getData();

            Upload.setImageURI(imageUri);

            Save();
        }

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

    private void Save()
    {


        mProgress.setTitle("Profile Image");
        mProgress.setMessage("Please wait while We Update Your Profile");
        mProgress.show();
        mProgress.setCanceledOnTouchOutside(false);





        // You ommited this codes before

        StorageReference filePath = userProfileImagesRef.child(CurrentUSerID + ".jpg");


        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final String downloadUrl = uri.toString();

                        UserRef.child("ProfileImage")
                                .setValue(downloadUrl)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ProfileActivity.this, "Profile Picture Updated", Toast.LENGTH_SHORT).show();
                                            mProgress.dismiss();
                                        } else {
                                            String message = task.getException().toString();
                                            Toast.makeText(ProfileActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                            mProgress.dismiss();
                                        }

                                    }
                                });
                    }

                });

            }
        });



    }




    private void sendToProduct()
    {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }
}