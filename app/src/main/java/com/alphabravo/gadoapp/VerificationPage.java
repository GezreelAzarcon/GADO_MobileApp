package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationPage extends AppCompatActivity {

    private Button continuebtn;
    private TextView nickname, gmail, forgotpass, switchs;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.verificationpage);

        continuebtn = (Button) findViewById(R.id.continueBtn);
        nickname = findViewById(R.id.nickname);
        gmail = findViewById(R.id.gmail);
        forgotpass = (TextView) findViewById(R.id.forgotBtn);
        String usernickname = getIntent().getStringExtra("keytxtnickname");
        String useremail = getIntent().getStringExtra("keytxtemail");
        nickname.setText(usernickname);
        gmail.setText(useremail);
        switchs = findViewById(R.id.switchBtn);




        switchs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignup();
            }
        });


        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotpass();
            }
        });


        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSigninPage();

            }
        });





    }

    private void openSignup() {
        Intent intent = new Intent(this, SignupPage.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void openSigninPage(){
        Intent intent = new Intent(this, SigninPage.class);
        startActivity(intent);

    }

    public void openForgotpass() {
        Intent intent = new Intent(this, forgotpassword_page.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


    }

}


