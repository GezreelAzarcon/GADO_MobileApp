package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import io.paperdb.Paper;

public class SettingsPage extends AppCompatActivity {

    private Button logout;
    MyDatabaseHelper myDB; // SQLite


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.settingspage);

        logout = findViewById(R.id.logoutButton1);
        myDB = new MyDatabaseHelper(SettingsPage.this);
        Paper.init(this);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Paper.book().destroy();
                myDB.resetLocalDatabase();
                myDB.resetLocalHistoryDatabase();
                Toast.makeText(SettingsPage.this, "Account Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SigninPage.class));
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_settings);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), MainPage.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.bottom_history) {
                startActivity(new Intent(getApplicationContext(), HistoryPage.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.bottom_settings) {
                return true;
            }
            return false;
        });

    }

}
