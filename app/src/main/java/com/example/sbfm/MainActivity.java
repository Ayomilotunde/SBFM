package com.example.sbfm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    private RecyclerView recyclerView;
    private ArrayList<DataSetFire> arrayList;
    private FirebaseRecyclerOptions<DataSetFire> options;
    private FirebaseRecyclerAdapter<DataSetFire,FirebaseViewHolder> adapter;
    private DatabaseReference databaseReference;


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<DataSetFire>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Sermons");
        databaseReference.keepSynced(true);












        options = new FirebaseRecyclerOptions.Builder<DataSetFire>().setQuery(databaseReference,DataSetFire.class).build();

        adapter
                = new FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int i, @NonNull final DataSetFire dataSetFire) {

                firebaseViewHolder.name.setText(dataSetFire.getName());
                firebaseViewHolder.topic.setText(dataSetFire.getTopic());
                firebaseViewHolder.note.setText(dataSetFire.getNote());
                firebaseViewHolder.bibleRefrence.setText(dataSetFire.getBibleRefrences());
                firebaseViewHolder.watchword.setText(dataSetFire.getWatchWord());
                /*firebaseViewHolder.watchword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        *//*Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        intent.putExtra("name", dataSetFire.getName());
                        intent.putExtra("phone",dataSetFire.getPhone());
                        intent.putExtra("occupation",dataSetFire.getOccupation());
                        intent.putExtra("address",dataSetFire.getAddress());
                        startActivity(intent);*//*
                    }
                });*/

            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.row,parent,false));
            }
        };



        recyclerView.setAdapter(adapter);





















        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        Menu menu = mNavigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_profile).setVisible(false);

        mNavigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_home);

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
                break;
            case R.id.nav_cycle:
                Toast.makeText(this, "clicked Cycle", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_bus:
                //setContentView(R.layout.activity_second);
                /*Intent intent = new Intent(this, BusActivity.class);
                startActivity(intent);*/
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