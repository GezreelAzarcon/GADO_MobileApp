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

public class usercredential_page extends AppCompatActivity {

    private Button signin, create;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.usercredentialpage);


        create = (Button) findViewById(R.id.createBtn);
        signin = (Button) findViewById(R.id.signintextBtn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSigninPage();

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVerificationpage();

            }
        });




    }

    public void openSigninPage(){
        Intent intent = new Intent(this, signin_page.class);
        startActivity(intent);

    }

    public void openVerificationpage(){
        Intent intent = new Intent(this, verification_page.class);
        startActivity(intent);

    }


}


