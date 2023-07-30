package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerificationPage extends AppCompatActivity {

    private Button continuebtn;
    FirebaseUser user;
    AuthCredential credential;
    private EditText password;
    private TextView gmail, forgotpass, switchs;
    String useremail, userpass;
    FirebaseAuth auth;

    //press again to exit
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
    //press again to exit


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.verificationpage);

        password = findViewById(R.id.passwordText2);


        continuebtn = (Button) findViewById(R.id.continueBtn);
        gmail = findViewById(R.id.gmail);
        forgotpass = (TextView) findViewById(R.id.forgotBtn);

        userpass = password.getText().toString();
        useremail = getIntent().getStringExtra("keytxtemail");
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
                continuebtn.setEnabled(false);
                operInputPage();
            }
        });







    }

    public void operInputPage(){
        Intent intent = new Intent(this, WelcomeuserPage.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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


