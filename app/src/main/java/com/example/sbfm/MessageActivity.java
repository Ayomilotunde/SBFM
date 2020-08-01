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
    private DatabaseReference UserRef, FormRef;
    private String CurrentUSerID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);



        mProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        CurrentUSerID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUSerID);
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


        saveAccountInformation();
    }
    private void saveAccountInformation() {
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String note1 = note.getText().toString();
                String bibleRef = bibleRefrence.getText().toString();
                String Watchword = watchword.getText().toString();
                String topic1 = topic.getText().toString();


                if (TextUtils.isEmpty(bibleRef)) {

                    Toast.makeText(MessageActivity.this, "Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(note1)) {

                    Toast.makeText(MessageActivity.this, "Post ?", Toast.LENGTH_SHORT).show();
                }
                else {

                    mProgress.show();
                    mProgress.setCanceledOnTouchOutside(true);


                    HashMap userMap= new HashMap();

                    userMap.put("UserId", CurrentUSerID);
                    userMap.put("note", note1);
                    userMap.put("bibleRef", bibleRef);
                    userMap.put("watchWord", Watchword);
                    userMap.put("topic", topic1);


                    UserRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                sendToProduct();
                                Toast.makeText(MessageActivity.this, "Form Saved", Toast.LENGTH_LONG).show();
                                mProgress.dismiss();
                            } else {

                                String Message = Objects.requireNonNull(task.getException()).getMessage();
                                Toast.makeText(MessageActivity.this, "Error Occured When Creating Account" + Message, Toast.LENGTH_LONG).show();
                                mProgress.dismiss();
                            }

                        }
                    });

                }
            }
        });
    }

    private void sendToProduct() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getValues(){

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        String uniqueId = ref.push().getKey();

        User user = new User();
        user.setName(name.getText().toString());
        user.setTopic(topic.getText().toString());
        user.setNote(note.getText().toString());
        user.setBibleRefrences(bibleRefrence.getText().toString());
        user.setBibleRefrences(watchword.getText().toString());



        ref.child(uniqueId).setValue(user);
        Toast.makeText(MessageActivity.this, "Data Inputed...",Toast.LENGTH_SHORT).show();

    }

    public void btnView(View view) {
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity(intent);
    }

    public void btnSubmit(View view) {

//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//
//        Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
    }
}