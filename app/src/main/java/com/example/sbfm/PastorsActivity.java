package com.example.sbfm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PastorsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView findFriendRecyclerList;

    private DatabaseReference UsersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastors);

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        findFriendRecyclerList = findViewById(R.id.find_friends_recycler_list);
        findFriendRecyclerList.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>().setQuery(UsersRef, User.class).build();

        FirebaseRecyclerAdapter<User, FindFriendViewHolder> adapter = new FirebaseRecyclerAdapter<User, FindFriendViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FindFriendViewHolder holder, final int position, @NonNull User model) {
                holder.userName.setText(model.getName());
                holder.userStatus.setText(model.getPost());
                Picasso.get().load(model.getProfileImage()).placeholder(R.drawable.profile).into(holder.profileImage);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String visit_user_id = getRef(position).getKey();

                        Intent profileIntent = new Intent(PastorsActivity.this, ProfileActivity.class);
                        profileIntent.putExtra("visit_user_id", visit_user_id);
                        startActivity(profileIntent);
                    }
                });

            }

            @NonNull
            @Override
            public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pastors_display, parent, false);
                FindFriendViewHolder viewHolder = new FindFriendViewHolder(view);
                return viewHolder;
            }
        };

        findFriendRecyclerList.setAdapter(adapter);

        adapter.startListening();
    }


    public static class FindFriendViewHolder extends RecyclerView.ViewHolder {

        TextView userName, userStatus;
        CircleImageView profileImage;

        public FindFriendViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.txt_user_name);
            userStatus = itemView.findViewById(R.id.txt_user_post);
            profileImage = itemView.findViewById(R.id.users_profile_image);
        }
    }
}



