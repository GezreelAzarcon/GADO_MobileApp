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

public class passwordupdated_page extends AppCompatActivity {

    private Button signin;




    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.passwordupdatedpage);

        signin = (Button) findViewById(R.id.signinPassUpdtdBtn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignin();

            }
        });

    }
    public void openSignin(){
        Intent intent = new Intent(this, signin_page.class);
        startActivity(intent);

    }


}


