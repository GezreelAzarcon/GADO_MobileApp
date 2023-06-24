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

public class verification_page extends AppCompatActivity {

    private Button continuebtn;


    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.verificationpage);

        continuebtn = (Button) findViewById(R.id.continueBtn);
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSigninPage();

            }
        });





    }
    public void openSigninPage(){
        Intent intent = new Intent(this, signin_page.class);
        startActivity(intent);

    }

}


