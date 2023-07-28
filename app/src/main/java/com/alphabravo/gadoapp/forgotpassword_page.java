package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class forgotpassword_page extends AppCompatActivity {

    private Button send;
    private TextView SignIn;

    private EditText gmail;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.forgotpasswordpage);

        SignIn = findViewById(R.id.logintextBtn);
        send = (Button) findViewById(R.id.sendBtn);
        gmail = findViewById(R.id.emailText);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = gmail.getText().toString();
                openVerification(txtEmail);

            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSigninPage();

            }
        });

    }

    private void openSigninPage() {
        Intent intent = new Intent(this, SigninPage.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openVerification(String txtEmail) {
        Intent intent = new Intent(this, passwordupdated_page.class);
        intent.putExtra("keytxtemail1", txtEmail);
        startActivity(intent);
        overridePendingTransition(0, 0);


    }



}


