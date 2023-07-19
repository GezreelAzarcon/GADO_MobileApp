package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.security.auth.login.LoginException;

import io.paperdb.Paper;


public class MainPage extends AppCompatActivity {
    EditText expenses, datentime, time, description;

    Button enter, reset;

    TextView lifepoints, constamount;


    MyDatabaseHelper myDB; // SQLite

    String budget = ""; //constant budget variable
    String pointText = ""; //point variable, changes each arithmetic

    // pointText / budget --> TextView format

    //firebase
    FirebaseAuth fAuth;
    String userID;

    DatabaseReference firebaseWrite;

    private ProgressBar pointBar;
    private double maxBudget = 0;
    private double currentBudget = 0;


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

        //firebase
        fAuth = FirebaseAuth.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        firebaseWrite = FirebaseDatabase.getInstance().getReference(userID);


        //firebase

        //sql
        myDB = new MyDatabaseHelper(MainPage.this);

        pointBar = findViewById(R.id.progressBar);



        getDBData(); // Displays Data


        //try




        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                expensesCheck(); // Updates point each arithmetic

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
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            } else if (item.getItemId() == R.id.bottom_settings) {
                startActivity(new Intent(getApplicationContext(), SettingsPage.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
    private void expensesCheck() {
        String expense = expenses.getText().toString();
        String desc = description.getText().toString();
        if (expense.matches("") || desc.matches("")) {
            Toast.makeText(this, "There's no value!", Toast.LENGTH_SHORT).show();
        }else {
            updatePoint();
            addHistoryData();
        }
    }

    private void addHistoryData() {
        String date = datentime.getText().toString().trim();
        String timeHistory = time.getText().toString().trim();
        String budget = constamount.getText().toString().trim();
        String expense = expenses.getText().toString().trim();
        String historyDescription = description.getText().toString().trim();

        myDB.insertuserdata(date, timeHistory, budget, expense, historyDescription);
    }

    private void addProgressData() {
        String spendingString = expenses.getText().toString();
        if (spendingString.matches("")) {
            double spendingAmount = 0;
            currentBudget -= spendingAmount;
            pointBar.setProgress((int) currentBudget);
        }else {
            double spendingAmount = Double.parseDouble(spendingString);
            currentBudget -= spendingAmount;
            if (currentBudget < 0) {
                currentBudget = 0;
            }
            pointBar.setProgress((int) currentBudget);
        }

    }

    private void pointLife() {
        String budgetString = budget;
        String currentString = pointText;
        maxBudget = Double.parseDouble(budgetString);
        currentBudget = Double.parseDouble(currentString);
        pointBar.setMax((int) maxBudget);
        pointBar.setProgress((int) currentBudget);
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
                pointLife();
            }else {
                cursor.close();
            }
        }
    }


    // SQLite Update Point Each Arithmetic
    void updatePoint() {
        String expensesSTR = expenses.getText().toString();
        int pointINT = Integer.parseInt(pointText);

        if (expensesSTR.matches("")) {
            expensesSTR = "0";
            int expensesINT = Integer.valueOf(expensesSTR);
            pointText = String.valueOf(pointINT - expensesINT);
            myDB.updateScore(pointText, "1");
            getDBData();
        }else {
            int expensesINT = Integer.valueOf(expenses.getText().toString());
            pointText = String.valueOf(pointINT - expensesINT);
            myDB.updateScore(pointText, "1");
            lifepoints.setText(pointText);
            getDBData();
        }
    }


    public void openhistory_page() {
        Intent intent = new Intent(this, HistoryPage.class);
        startActivity(intent);

    }
}