package com.alphabravo.gadoapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class InputPage extends AppCompatActivity {

    private EditText amount;
    private Button save, logout;



    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_input_page);

        Paper.init(this);
        save = findViewById(R.id.saveBtn);
        amount = findViewById(R.id.amountEditText);
        String useramount = getIntent().getStringExtra("keytxtwallet");
        amount.setText(useramount);






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


