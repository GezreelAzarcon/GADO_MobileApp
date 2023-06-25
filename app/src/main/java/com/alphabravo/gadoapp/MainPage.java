package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {

    private Button history;

    TextView amountTextView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.mainpage);

        history = (Button) findViewById(R.id.history_btn);

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
    public void openhistory_page(){
        Intent intent = new Intent(this, HistoryPage.class);
        startActivity(intent);

    }
}


