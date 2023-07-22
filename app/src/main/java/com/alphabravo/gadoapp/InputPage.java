package com.alphabravo.gadoapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.paperdb.Paper;

public class InputPage extends AppCompatActivity {


    private TextView datentime, time, amount;
    private ImageView save, logout;



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


