package com.example.sbfm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    private CircleImageView nav_image;
    private TextView profile_name, post;

    private RecyclerView recyclerView;
    private Toolbar mToolbar;

    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private String CurrentUSerID;

    FirebaseRecyclerOptions<User> options1;
    FirebaseRecyclerAdapter<User, TextViewHolder> adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        nav_image = findViewById(R.id.nav_post);
        profile_name = findViewById(R.id.profile_image);
        post = findViewById(R.id.post);


        mProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        CurrentUSerID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutmanager = new LinearLayoutManager(this);
        linearLayoutmanager.setReverseLayout(true);
        linearLayoutmanager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutmanager);


        Menu menu = mNavigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_profile).setVisible(false);

        mNavigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_home);

        UserRef.child(CurrentUSerID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {

                    String Name = snapshot.child("Name").getValue().toString();
                    String Post1 = snapshot.child("Post").getValue().toString();
                    String image1 = snapshot.child("ProfileImage").getValue().toString();

                    Picasso.get().load(image1).placeholder(R.drawable.profile).into(nav_image);

                    profile_name.setText(Name);
                    post.setText(Post1);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        loadDataText();
    }

    private void loadDataText() {

        Query sortPostInDescendingOrder = UserRef.orderByChild("Counter");
        options1 = new FirebaseRecyclerOptions.Builder<User>().setQuery(sortPostInDescendingOrder, User.class).build();
        adapter1 = new FirebaseRecyclerAdapter<User, TextViewHolder>(options1) {
            @Override
            protected void onBindViewHolder(@NonNull TextViewHolder holder, int position, @NonNull User model) {
                final String postKey = getRef(position).getKey();
                holder.description.setText(model.getNote());;
//                holder.postDate.setText(model.getDate());
                holder.topic.setText(model.getTopic());

                holder.bibleRef.setText(model.getBibleRefrences());
                holder.Name.setText(model.getName());
                holder.post.setText(model.getPost());




                Picasso.get()
                        .load(model.getProfileImage())
                        .into(holder.img);

//                holder.username.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view)
//                    {
//
//                        Intent CommentIntent = new Intent(PostTextActivity.this, PersonProfileActivity.class);
//                        CommentIntent.putExtra("postKey", postKey);
//                        startActivity(CommentIntent);
//
//                    }
//                });
//
            }


            @NonNull
            @Override
            public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                /* set and inflate the layout needed for displaying data */

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item, parent, false);
                return new TextViewHolder(view);


            }
        };


        adapter1.startListening();
        recyclerView.setAdapter(adapter1);



    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(this, ProfileShowActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_cycle:
                Toast.makeText(this, "clicked Cycle", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_bus:
                //setContentView(R.layout.activity_second);
                Intent intent1 = new Intent(this, MessageActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_car:
                Toast.makeText(this, "Clicked Car", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_login:
                Toast.makeText(this, "Clicked Login", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Clicked Logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_profile:
                Toast.makeText(this, "Clicked Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_share:
                Toast.makeText(this, "Clicked Share", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_rate:
                Toast.makeText(this, "Clicked Rate", Toast.LENGTH_SHORT).show();
                break;

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}