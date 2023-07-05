package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryPage extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> lifepoints, datentime;
    MyDatabaseHelper DB;
    MyAdapter adapter;



    private Button backbtn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.historypage);
        DB = new MyDatabaseHelper(this);
        lifepoints = new ArrayList<>();
        datentime = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleview);
        adapter = new MyAdapter(this, lifepoints, datentime);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_history);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), MainPage.class));
                return true;
            } else if (item.getItemId() == R.id.bottom_history) {
                return true;
            } else if (item.getItemId() == R.id.bottom_settings) {
                startActivity(new Intent(getApplicationContext(), SettingsPage.class));
                return true;
            }
            return false;
        });


        backbtn = (Button) findViewById(R.id.back_btn);
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
            return;
        }else{
            while (cursor.moveToNext()){
                lifepoints.add(cursor.getString(0));
                datentime.add(cursor.getString(1));


            }
        }
    }
}



