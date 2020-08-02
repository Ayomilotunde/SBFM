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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class MessageActivity extends AppCompatActivity {

    EditText name, topic, note, bibleRefrence, watchword;
    Button btnsubmit;

    FirebaseDatabase database;
    DatabaseReference ref;

    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef, Ref;
    private String CurrentUSerID, daj;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);



        mProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        CurrentUSerID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        Ref = FirebaseDatabase.getInstance().getReference().child("Sermon");
        topic = findViewById(R.id.edt_topic);
        note = findViewById(R.id.edt_note);
        bibleRefrence = findViewById(R.id.edt_bibleRefrence);
        watchword = findViewById(R.id.edt_watchword);
        btnsubmit = findViewById(R.id.btnsubmit);

        //ref = database.getReference();

//        btnsubmit.setOnClickListener(new View.OnClickListener() {
//
//
//            @Override
//            public void onClick(View v) {
//
//                if(!(name.getText().toString().equals("") || topic.getText().toString().equals("")
//                        || note.getText().toString().equals("") || bibleRefrence.getText().toString().equals("")|| watchword.getText().toString().equals(""))){
//                    getValues();
//                    setContentView(R.layout.activity_main);
//                } else{
//                    Toast.makeText(MessageActivity.this, "All field required", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveAccountInformation();

                //startActivity(new Intent(MessageActivity.this, MainActivity.class));
            }
        });





    }
    private void saveAccountInformation() {
        UserRef.child(CurrentUSerID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    daj = Ref.push().getKey();
                    final String PI = dataSnapshot.child("ProfileImage").getValue(String.class);
                        final String N = dataSnapshot.child("Name").getValue(String.class);
                        final String P = dataSnapshot.child("Post").getValue(String.class);

                    String note1 = note.getText().toString();
                    String bibleRef = bibleRefrence.getText().toString();
                    String Watchword = watchword.getText().toString();
                    String topic1 = topic.getText().toString();

                        HashMap PostsMap = new HashMap();
                        PostsMap.put("UserId", CurrentUSerID);
                        PostsMap.put("note", note1);
                        PostsMap.put("ProfileImage", PI);
                        PostsMap.put("Name", N);
                        PostsMap.put("Post", P);
                        PostsMap.put("bibleRef", bibleRef);
                        PostsMap.put("watchWord", Watchword);
                        PostsMap.put("topic", topic1);

                        if (note1.isEmpty() || bibleRef.isEmpty() || topic1.isEmpty()){
                            Toast.makeText(MessageActivity.this, "Field Required", Toast.LENGTH_SHORT).show();
                        }else {

                            Ref.child(daj).updateChildren(PostsMap)
                                    .addOnSuccessListener(new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            Toast.makeText(MessageActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                            mProgress.dismiss();
                                            sendToProduct();
                                        }
                                    });
                        }



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
//        UserRef.child(CurrentUSerID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot)
//            {
//                if(snapshot.exists())
//                {
//
//                    final String PI = snapshot.child("ProfileImage").getValue(String.class);
//                    final String N = snapshot.child("Name").getValue(String.class);
//                    final String P = snapshot.child("Post").getValue(String.class);
//
//
//
//
//                    String note1 = note.getText().toString();
//                    String bibleRef = bibleRefrence.getText().toString();
//                    String Watchword = watchword.getText().toString();
//                    String topic1 = topic.getText().toString();
//
//
//                    if (TextUtils.isEmpty(bibleRef)) {
//
//                        Toast.makeText(MessageActivity.this, "Name", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (TextUtils.isEmpty(note1)) {
//
//                        Toast.makeText(MessageActivity.this, "Post ?", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//
//                        mProgress.show();
//                        mProgress.setCanceledOnTouchOutside(true);
//
//
//                        HashMap userMap= new HashMap();
//
//                        userMap.put("UserId", CurrentUSerID);
//                        userMap.put("note", note1);
//                        userMap.put("ProfileImage", PI);
//                        userMap.put("Name", N);
//                        userMap.put("Post", P);
//                        userMap.put("bibleRef", bibleRef);
//                        userMap.put("watchWord", Watchword);
//                        userMap.put("topic", topic1);
//
//
//                        Ref.child(CurrentUSerID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
//                            @Override
//                            public void onComplete(@NonNull Task task) {
//                                if (task.isSuccessful()) {
//                                    sendToProduct();
//                                    Toast.makeText(MessageActivity.this, "Form Saved", Toast.LENGTH_LONG).show();
//                                    mProgress.dismiss();
//                                } else {
//
//                                    String Message = Objects.requireNonNull(task.getException()).getMessage();
//                                    Toast.makeText(MessageActivity.this, "Error Occured When Creating Account" + Message, Toast.LENGTH_LONG).show();
//                                    mProgress.dismiss();
//                                }
//
//                            }
//                        });
//
//                    }
//                }
//
//            }
//
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    private void sendToProduct() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

//    private void getValues(){
//
//        database = FirebaseDatabase.getInstance();
//        ref = database.getReference("Users");
//
//        String uniqueId = ref.push().getKey();
//
//        User user = new User();
//        user.setName(name.getText().toString());
//        user.setTopic(topic.getText().toString());
//        user.setNote(note.getText().toString());
//        user.setBibleRef(bibleRefrence.getText().toString());
//        user.setWatchWord(watchword.getText().toString());
//
//
//
//        ref.child(uniqueId).setValue(user);
//        Toast.makeText(MessageActivity.this, "Data Inputed...",Toast.LENGTH_SHORT).show();
//
//    }
//
//    public void btnView(View view) {
//        Intent intent = new Intent(this, ViewActivity.class);
//        startActivity(intent);
//    }
//
//    public void btnSubmit(View view) {

//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//
//        Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
//    }
}