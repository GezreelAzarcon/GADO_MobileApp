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

public class WelcomeuserPage extends AppCompatActivity {

    private ImageView wallet;
    private EditText amount;

    private TextView contactus;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.welcomeuser);

        wallet = (ImageView) findViewById(R.id.walletBtn);
        amount = (EditText) findViewById(R.id.amountEditText1);
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


        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wallet.setEnabled(true);
                String txtWallet = amount.getText().toString();
                if (TextUtils.isEmpty(txtWallet)){
                    Toast.makeText(WelcomeuserPage.this, "Enter your Budget to Proceed.", Toast.LENGTH_SHORT).show();
                }else {
                    proceedUser(txtWallet);
                }

            }
        });
    }

    public void proceedUser(String txtWallet) {
        Intent intent = new Intent(this, Almostthere_page.class);
        intent.putExtra("keytxtwallet", txtWallet);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }
}

