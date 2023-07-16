package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HistoryPage extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> datentime, time, constamount, expenses, description;
    MyDatabaseHelper DB;
    MyAdapter adapter;

    //firebase
    FirebaseAuth fAuth;
    DatabaseReference databaseHistoryData;
    String userID;
    //firebase


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.historypage);
        DB = new MyDatabaseHelper(this);
        datentime = new ArrayList<>();
        time = new ArrayList<>();
        constamount = new ArrayList<>();
        expenses = new ArrayList<>();
        description = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleview);
        adapter = new MyAdapter(this, datentime, time, constamount, expenses, description);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();

        //firebase
        fAuth = FirebaseAuth.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        databaseHistoryData = FirebaseDatabase.getInstance().getReference(userID);
        //firebase

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_history);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), MainPage.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.bottom_history) {
                return true;
            } else if (item.getItemId() == R.id.bottom_settings) {
                startActivity(new Intent(getApplicationContext(), SettingsPage.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });


        Button backbtn = (Button) findViewById(R.id.back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmain_page();

            }
        });


    }

    public void openmain_page() {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);

    }

    private void displaydata(){
        Cursor cursor = DB.getdata();
        if (cursor.getCount()==0)
        {
            Toast.makeText(HistoryPage.this, "No Entry Exists.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                datentime.add(cursor.getString(0));
                time.add(cursor.getString(1));
                constamount.add(cursor.getString(2));
                expenses.add(cursor.getString(3));
                description.add(cursor.getString(4));
            }
        }
    }
}



