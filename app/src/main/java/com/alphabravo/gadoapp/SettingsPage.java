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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import io.paperdb.Paper;

public class SettingsPage extends AppCompatActivity {

    private Button logout;
    private ImageView reset;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    MyDatabaseHelper myDB; // SQLite
    FirebaseAuth fAuth;
    String userID;
    DatabaseReference firebaseWrite;
    DatabaseReference databaseHistoryData;

    ArrayList<String> date1 = new ArrayList<>();
    ArrayList<String> time1 = new ArrayList<>();
    ArrayList<String> budget1 = new ArrayList<>();
    ArrayList<String> expenses1 = new ArrayList<>();
    ArrayList<String> description1 = new ArrayList<>();

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
        setContentView(R.layout.settingspage);
        Paper.init(this);
        logout = findViewById(R.id.logoutButton1);
        myDB = new MyDatabaseHelper(SettingsPage.this);
        fAuth = FirebaseAuth.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        firebaseWrite = FirebaseDatabase.getInstance().getReference(userID);
        reset = findViewById(R.id.resetButton);


        //firebase

        //sql

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogLogout();
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_settings);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.bottom_budget) {
                startActivity(new Intent(getApplicationContext(), InputPage.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.bottom_home) {
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

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Back to Fund Page?");
        builder.setMessage("Your budget will reset if you proceed.");
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(SettingsPage.this, WelcomeuserPage.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

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

    private void alertDialogLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to logout?");
        builder.setMessage("Current daily budgeting resets.");
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout.setEnabled(false);
                Paper.book().destroy();
                firebaseWriteExecute();
                myDB.resetLocalDatabase();
                myDB.resetLocalHistoryDatabase();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(SettingsPage.this, "Account Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SigninPage.class));

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

    private void firebaseWrite() {

        int size = date1.size();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference(userID).removeValue();

        String dateT;
        String timeT;
        String budgetT;
        String expensesT;
        String descT;
        for (int i = 0; i < size; i++) {
            String id = firebaseWrite.push().getKey();
            dateT = date1.get(i);
            timeT = time1.get(i);
            budgetT =budget1.get(i);
            expensesT = expenses1.get(i);
            descT = description1.get(i);

            HistoryData historyData = new HistoryData(dateT, timeT, budgetT, expensesT, descT);
            firebaseWrite.child(id).setValue(historyData);
        }
    }

    private void firebaseWriteExecute(){
        Cursor cursor = myDB.getdata();
        if (cursor.getCount()==0)
        {
            ;

        }else{
            while (cursor.moveToNext()){
                date1.add(cursor.getString(0));
                time1.add(cursor.getString(1));
                budget1.add(cursor.getString(2));
                expenses1.add(cursor.getString(3));
                description1.add(cursor.getString(4));
            }
            firebaseWrite();
        }
    }
}
