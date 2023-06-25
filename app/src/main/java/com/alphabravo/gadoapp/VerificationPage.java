package com.alphabravo.gadoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationPage extends AppCompatActivity {

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
        Intent intent = new Intent(this, SigninPage.class);
        startActivity(intent);

    }

}


