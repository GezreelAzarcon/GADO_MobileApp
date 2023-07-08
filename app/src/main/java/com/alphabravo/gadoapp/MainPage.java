package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.paperdb.Paper;


public class MainPage extends AppCompatActivity {

    EditText expenses, datentime, time, description;

    Button enter, reset;


    TextView lifepoints, constamount;


    MyDatabaseHelper myDB; // SQLite

    String budget = ""; //constant budget variable
    String pointText = ""; //point variable, changes each arithmetic

    // pointText / budget --> TextView format


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        enter = findViewById(R.id.EnterBtn);
        reset = findViewById(R.id.resetButton);
        expenses = findViewById(R.id.expensesText);
        datentime = findViewById(R.id.text_view_date);
        time = findViewById(R.id.text_view_time);
        lifepoints = findViewById(R.id.lifePoint);
        constamount = findViewById(R.id.constLife);
        description = findViewById(R.id.descriptionText);


        myDB = new MyDatabaseHelper(MainPage.this);

        getDBData(); // Displays Data

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String datentimeTXT = datentime.getText().toString();
                String timeTXT = time.getText().toString();
                String constamountTXT = constamount.getText().toString();
                String expensesTXT = expenses.getText().toString();
                String descriptionTXT = description.getText().toString();
                Boolean checkinsertdata = myDB.insertuserdata(datentimeTXT, timeTXT, constamountTXT, expensesTXT, descriptionTXT);
                if(checkinsertdata == true)
                {
                    Toast.makeText(MainPage.this, "SAVED! Check history.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainPage.this, "Did not saved.", Toast.LENGTH_SHORT).show();

                }

                updatePoint(); // Updates point each arithmetic

            }


        });



        long date = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy ");
        SimpleDateFormat stf = new SimpleDateFormat("hh:mm a");
        String currentDate = sdf.format(date);
        String currentTime = stf.format(date);

        EditText datentime = findViewById(R.id.text_view_date);
        EditText time = findViewById(R.id.text_view_time);
        datentime.setText(currentDate);
        time.setText(currentTime);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.bottom_home) {
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





        // removed
        //String userAmount = getIntent().getStringExtra("amountUser");


        //daily reset logic button (temporary)
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.resetLocalDatabase();
                startActivity(new Intent(MainPage.this, InputPage.class));
            }
        });


    }


    // SQLite Read and Display Data to TextViews
    void getDBData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            if (cursor.moveToFirst()){
                budget = cursor.getString(1);
                pointText = cursor.getString(2);
                lifepoints.setText(pointText);
                constamount.setText(budget);
            }else {
                cursor.close();
            }
        }
    }


    // SQLite Update Point Each Arithmetic
    void updatePoint() {
        int expensesINT = Integer.valueOf(expenses.getText().toString());
        int pointINT = Integer.valueOf(pointText);
        pointText = String.valueOf(pointINT - expensesINT);
        myDB.updateScore(pointText, "1");
        lifepoints.setText(pointText);
    }


    public void openhistory_page() {
        Intent intent = new Intent(this, HistoryPage.class);
        startActivity(intent);

    }









}


