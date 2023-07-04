package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.util.Calendar;



public class MainPage extends AppCompatActivity {

    EditText expenses, datentime;

    Button history, enter;

    DatabaseReference databaseUsers;

    TextView amountTextView;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);


        enter = findViewById(R.id.EnterBtn);
        expenses = findViewById(R.id.expensesText);
        datentime = findViewById(R.id.text_view_date);

        databaseUsers = FirebaseDatabase.getInstance().getReference().child("User");


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                InsertData();
            }
        });



        long date = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy " + "hh:mm a");
        String currentDate = sdf.format(date);

        EditText datentime = findViewById(R.id.text_view_date);
        datentime.setText(currentDate);

        history = (Button) findViewById(R.id.history_btn);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_input) {
                startActivity(new Intent(getApplicationContext(), InputPage.class));
                return true;
            } else if (item.getItemId() == R.id.bottom_home) {
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


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openhistory_page();

            }
        });

        amountTextView = findViewById(R.id.amountText);

        String useramount = getIntent().getStringExtra("amountUser");

        amountTextView.setText(useramount);

    }

    public void openhistory_page() {
        Intent intent = new Intent(this, HistoryPage.class);
        startActivity(intent);

    }

    private void InsertData(){

        String expensesTXT = expenses.getText().toString();
        String datentimeTXT = datentime.getText().toString();
        String id = databaseUsers.push().getKey();

        User user = new User(expensesTXT, datentimeTXT);
        databaseUsers.child("gado-mobile-app").child(id).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainPage.this, "Saved!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }






}



