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

public class newcredential_page extends AppCompatActivity {

    private Button update;




    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.newcredential);

        update = (Button) findViewById(R.id.updateBtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPasswordupdated();

            }
        });

    }
    public void openPasswordupdated() {
        Intent intent = new Intent(this, passwordupdated_page.class);
        startActivity(intent);

    }


}


