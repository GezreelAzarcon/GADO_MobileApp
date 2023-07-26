package com.alphabravo.gadoapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.paperdb.Paper;

public class InputPage extends AppCompatActivity {


    private TextView datentime, time, amount;
    private ImageView save, logout;
    private TextView contactus;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;



    @SuppressLint({"NonConstantResourceId", "MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_input_page);

        Paper.init(this);
        save = findViewById(R.id.saveBtn);
        datentime = (TextView) findViewById(R.id.text_view_date1);
        time = (TextView) findViewById(R.id.text_view_time1);
        amount = findViewById(R.id.amountEditText);
        String useramount = getIntent().getStringExtra("keytxtproceed");
        amount.setText(useramount);
        amount.setFocusable(false);
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



        long date = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("hh:mm a");
        String currentDate = sdf.format(date);
        String currentTime = stf.format(date);

        TextView datentime = findViewById(R.id.text_view_date1);
        TextView time = findViewById(R.id.text_view_time1);
        datentime.setText(currentDate);
        time.setText(currentTime);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_budget);

        bottomNavigationView.setOnItemSelectedListener(item -> {


            if (item.getItemId() == R.id.bottom_budget) {
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
                startActivity(new Intent(getApplicationContext(), SettingsPage.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });






        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });



        }

        private void checkInput() {
            // SQLite Write Data
            String budget = amount.getText().toString();
            if (budget.matches("")) {
                Toast.makeText(this, "There's no Value!", Toast.LENGTH_SHORT).show();
            }else {
                MyDatabaseHelper myDB = new MyDatabaseHelper(InputPage.this);
                myDB.addBook(amount.getText().toString().trim(),
                        amount.getText().toString().trim());

                //Intent intent = new Intent(InputPage.this, MainPage.class);
                //intent.putExtra("amountUser", budget);
                //startActivity(intent);

                startActivity(new Intent(InputPage.this, MainPage.class));
            }

        }

    }


