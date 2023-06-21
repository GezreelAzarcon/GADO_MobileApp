package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class main_page extends AppCompatActivity {

    TextView amountTextView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.mainpage);

        amountTextView = findViewById(R.id.amountText);

        String useramount = getIntent().getStringExtra("amountUser");

        amountTextView.setText(useramount);

    }
}


