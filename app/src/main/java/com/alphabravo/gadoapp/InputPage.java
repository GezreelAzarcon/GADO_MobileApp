package com.alphabravo.gadoapp;

import static com.alphabravo.gadoapp.R.id.amountText;
import static com.alphabravo.gadoapp.R.id.bottom_input;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class InputPage extends AppCompatActivity {

    private EditText amount;
    private Button save, logout;



    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_input_page);

        logout = findViewById(R.id.logoutButton1);
        Paper.init(this);
        save = findViewById(R.id.saveBtn);
        amount = findViewById(R.id.amountEditText);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_input);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_input) {
                return true;
            } else if (item.getItemId() == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), MainPage.class));
                return true;
            } else if (item.getItemId() == R.id.bottom_history) {
                startActivity(new Intent(getApplicationContext(), HistoryPage.class));
                return true;
            } else if (item.getItemId() == R.id.bottom_settings) {
                startActivity(new Intent(getApplicationContext(), SettingsPage.class));
                return true;
            }
            return false;
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(InputPage.this, "Account Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(InputPage.this, SigninPage.class));
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budget = amount.getText().toString();

                // SQLite Write Data
                MyDatabaseHelper myDB = new MyDatabaseHelper(InputPage.this);
                myDB.addBook(amount.getText().toString().trim(),
                             amount.getText().toString().trim());

                //Intent intent = new Intent(InputPage.this, MainPage.class);
                //intent.putExtra("amountUser", budget);
                //startActivity(intent);

                startActivity(new Intent(InputPage.this, MainPage.class));


            }
        });



        }

    }


