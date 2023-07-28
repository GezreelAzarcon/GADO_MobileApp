package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class passwordupdated_page extends AppCompatActivity {

    private Button signin;
    private TextView gmail;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.passwordupdatedpage);

        signin = (Button) findViewById(R.id.signinPassUpdtdBtn);
        gmail = findViewById(R.id.usergmail);
        String useremail = getIntent().getStringExtra("keytxtemail1");
        gmail.setText(useremail);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignin();

            }
        });

    }
    public void openSignin(){
        Intent intent = new Intent(this, SigninPage.class);
        startActivity(intent);

    }


}


