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

public class forgotpassword_page extends AppCompatActivity {

    private Button send;





    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.forgotpasswordpage);

        send = (Button) findViewById(R.id.sendBtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVerification();

            }
        });

    }
    public void openVerification() {
        Intent intent = new Intent(this, verificationnewpass_page.class);
        startActivity(intent);

    }



}


