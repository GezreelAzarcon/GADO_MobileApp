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

public class signin_page extends AppCompatActivity {

    private Button start, forgot;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.signinpage);


        forgot = (Button) findViewById(R.id.forgotBtn);
        start = (Button) findViewById(R.id.startBtn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInputPage();

            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgetpassword();

            }
        });




    }
    public void openInputPage(){
        Intent intent = new Intent(this, InputPage.class);
        startActivity(intent);

    }

    public void openForgetpassword(){
        Intent intent = new Intent(this, forgotpassword_page.class);
        startActivity(intent);

    }

}


