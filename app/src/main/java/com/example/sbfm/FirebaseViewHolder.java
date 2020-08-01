package com.example.sbfm;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView name, topic, note, bibleRefrence, watchword;

    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.view_name);
        topic = itemView.findViewById(R.id.view_topic);
        note = itemView.findViewById(R.id.view_note);
        bibleRefrence = itemView.findViewById(R.id.view_bibleRefrences);
        watchword = itemView.findViewById(R.id.view_watchWord);

    }
}