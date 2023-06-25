package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class InputPage extends AppCompatActivity {

    private EditText amount;
    private Button save, logout;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_input_page);

        logout = findViewById(R.id.logoutButton1);
        Paper.init(this);
        save = findViewById(R.id.saveBtn);
        amount = findViewById(R.id.amountEditText);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(InputPage.this, "Account Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(InputPage.this, SigninPage.class));
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String m = amount.getText().toString();

                Intent intent = new Intent(InputPage.this, MainPage.class);

                intent.putExtra("amountUser", m);
                startActivity(intent);
            }
        });



    }


}


