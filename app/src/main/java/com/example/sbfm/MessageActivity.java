package com.example.sbfm;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessageActivity extends AppCompatActivity {

    EditText name, topic, note, bibleRefrence, watchword;
    Button btnsubmit;

    FirebaseDatabase database;
    DatabaseReference ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        name = findViewById(R.id.edt_name);
        topic = findViewById(R.id.edt_topic);
        note = findViewById(R.id.edt_note);
        bibleRefrence = findViewById(R.id.edt_bibleRefrence);
        watchword = findViewById(R.id.edt_watchword);
        btnsubmit = findViewById(R.id.btnsubmit);

        //ref = database.getReference();

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(name.getText().toString().equals("") || topic.getText().toString().equals("")
                        || note.getText().toString().equals("") || bibleRefrence.getText().toString().equals("")|| watchword.getText().toString().equals(""))){
                    getValues();
                    setContentView(R.layout.activity_main);
                } else{
                    Toast.makeText(MessageActivity.this, "All field required", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void getValues(){

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Sermons");

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