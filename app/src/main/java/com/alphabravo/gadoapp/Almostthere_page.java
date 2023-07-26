package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Almostthere_page extends AppCompatActivity {

    private ImageView backbutton, proceed;
    private EditText amount1;
    private TextView contactus;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.almosttherepage);

        proceed = (ImageView) findViewById(R.id.proceedbtn);
        amount1 = (EditText) findViewById(R.id.amountEditText01);
        backbutton = (ImageView) findViewById(R.id.backbtn);
        String useramount = getIntent().getStringExtra("keytxtwallet");
        amount1.setText(useramount);
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




        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                proceed.setEnabled(true);
                String txtProceed = amount1.getText().toString();
                if (TextUtils.isEmpty(txtProceed)) {
                    Toast.makeText(Almostthere_page.this, "Enter your Budget to Proceed.", Toast.LENGTH_SHORT).show();
                } else {
                    proceedUser(txtProceed);
                }

            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInputPage();
            }
        });


    }

    private void goToInputPage() {
        Intent intent = new Intent(this, WelcomeuserPage.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void proceedUser(String txtProceed) {
        Intent intent = new Intent(this, InputPage.class);
        intent.putExtra("keytxtproceed", txtProceed);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }
}



