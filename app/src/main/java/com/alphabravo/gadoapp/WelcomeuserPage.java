package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeuserPage extends AppCompatActivity {

    private ImageView wallet;
    private EditText amount;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.welcomeuser);

        wallet = (ImageView) findViewById(R.id.walletBtn);
        amount = (EditText) findViewById(R.id.amountEditText1);


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
        Intent intent = new Intent(this, InputPage.class);
        intent.putExtra("keytxtwallet", txtWallet);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }
}

