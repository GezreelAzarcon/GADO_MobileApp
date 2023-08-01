package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
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

    MyDatabaseHelper DB;

    private TextView contactus;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    //press again to exit
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;


    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
    //press again to exit




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.welcomeuser);

        wallet = (ImageView) findViewById(R.id.walletBtn);
        amount = (EditText) findViewById(R.id.amountEditText1);
        contactus = (TextView) findViewById(R.id.contactus);
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        DB = new MyDatabaseHelper(this);


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
                    Toast.makeText(WelcomeuserPage.this, "Enter your Budget to Proceed", Toast.LENGTH_SHORT).show();
                }else {
                    proceedUser(txtWallet);
                }

            }
        });
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsPage.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



    }


    public void proceedUser(String txtWallet) {
        Intent intent = new Intent(this, Almostthere_page.class);
        intent.putExtra("keytxtwallet", txtWallet);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }
    private void settingsCheck(){
        Cursor cursor = DB.readAllData();
        if (cursor.getCount()==0)
        {
            Toast.makeText(WelcomeuserPage.this, "Input a daily budget first!", Toast.LENGTH_SHORT).show();
        }else{
            openSettings();
        }
    }
}

