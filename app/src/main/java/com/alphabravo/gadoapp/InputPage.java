package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InputPage extends AppCompatActivity {

    private EditText amount;
    private Button save;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_input_page);

        save = findViewById(R.id.saveBtn);
        amount = findViewById(R.id.amountEditText);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String m = amount.getText().toString();

                Intent intent = new Intent(InputPage.this, main_page.class);

                intent.putExtra("amountUser", m);
                startActivity(intent);
            }
        });



    }


}


