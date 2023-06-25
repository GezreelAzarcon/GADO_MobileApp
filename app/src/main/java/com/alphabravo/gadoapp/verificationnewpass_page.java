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

public class verificationnewpass_page extends AppCompatActivity {

    private Button verify;




    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.verificationnewpasspage);

        verify = (Button) findViewById(R.id.verifyBtn);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewcredential();

            }
        });

    }
    public void openNewcredential() {
        Intent intent = new Intent(this, newcredential_page.class);
        startActivity(intent);

    }


}


