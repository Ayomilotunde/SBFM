package com.example.sbfm;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class TextViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout root;
    public TextView topic, description, Note, bibleRef, Name, post, textView9;
    public CircleImageView img;
    private FirebaseAuth mAuth;
    String currentUserID, post_key;
    DatabaseReference LikesRef, PostRef;
    private DatabaseReference PostRefs;


    public TextViewHolder(@NonNull View itemView) {
        super(itemView);

        topic = itemView.findViewById(R.id.topic);
        description = itemView.findViewById(R.id.description);
        Note = itemView.findViewById(R.id.Note);
        bibleRef = itemView.findViewById(R.id.bibleRef);
        Name = itemView.findViewById(R.id.Name);
        post = itemView.findViewById(R.id.post);
        img = itemView.findViewById(R.id.img);
        textView9 = itemView.findViewById(R.id.textView9);
        mAuth = FirebaseAuth.getInstance();
    }

}
