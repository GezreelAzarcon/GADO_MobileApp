package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import io.paperdb.Paper;

public class HistoryPage extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> datentime, time, constamount, expenses, description;
    MyDatabaseHelper DB;
    MyAdapter adapter;

    ImageView resetHistory;

    //firebase
    FirebaseAuth fAuth;
    DatabaseReference databaseHistoryData;
    String userID;
    boolean isEmpty;
    private TextView contactus;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;
    //firebase

    //try
    private ArrayList<String> dates, times, budgets, expense, descriptions;

    ArrayList<String> date1 = new ArrayList<>();
    ArrayList<String> time1 = new ArrayList<>();
    ArrayList<String> budget1 = new ArrayList<>();
    ArrayList<String> expenses1 = new ArrayList<>();
    ArrayList<String> desc1 = new ArrayList<>();

    ArrayList<String> date2 = new ArrayList<>();
    ArrayList<String> time2 = new ArrayList<>();
    ArrayList<String> budget2 = new ArrayList<>();
    ArrayList<String> expenses2 = new ArrayList<>();
    ArrayList<String> desc2 = new ArrayList<>();

    //try

    private Button backbtn;

    //press again to exit
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
    //press again to exit


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

        isEmpty = true;

        resetHistory = findViewById(R.id.resetHistory);

        recycleView();

        contactus = (TextView) findViewById(R.id.contactus);
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);



        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialAlertDialogBuilder.setTitle("Send us Feedback, Suggestions, or Concerns.");
                materialAlertDialogBuilder.setMessage("gezreelwazrcon@gmail.com\n" + "villarizaced@gmail.com\n");
                materialAlertDialogBuilder.show();
            }
        });

        resetHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogReset();
            }
        });

        //retrieveHistoryData();

        //firebase
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        databaseHistoryData = FirebaseDatabase.getInstance().getReference(userID);
        //firebase

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_history);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.bottom_budget) {
                startActivity(new Intent(getApplicationContext(), InputPage.class));
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.bottom_home) {
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





    }

    private void alertDialogReset() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset your History?");
        builder.setMessage("All of your history will be gone. You will be redirected to budget input.");
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userID = fAuth.getCurrentUser().getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference(userID).removeValue();
                DB.resetLocalHistoryDatabase();
                DB.resetLocalDatabase();
                startActivity(new Intent(HistoryPage.this, WelcomeuserPage.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();



            }
        });
        builder.create().show();
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

    private void recycleView() {
        displaydata();
        recyclerView = findViewById(R.id.recycleview);
        adapter = new MyAdapter(this, datentime, time, constamount, expenses, description);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}





