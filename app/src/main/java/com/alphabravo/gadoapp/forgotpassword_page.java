package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword_page extends AppCompatActivity {

    private Button send;
    private TextView SignIn;
    private EditText gmail;
    FirebaseAuth fauth;
    String strEmail;

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
        setContentView(R.layout.forgotpasswordpage);

        SignIn = findViewById(R.id.logintextBtn);
        send = (Button) findViewById(R.id.sendBtn);
        gmail = findViewById(R.id.emailText);
        fauth = FirebaseAuth.getInstance();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = gmail.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)) {
                    ResetPassword();
                }else {
                    Toast.makeText(forgotpassword_page.this, "Email does not exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSigninPage();

            }
        });

    }

    private void ResetPassword() {
        fauth.sendPasswordResetEmail(strEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(forgotpassword_page.this, "Email has been sent!", Toast.LENGTH_SHORT).show();
                        openVerification(strEmail);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(forgotpassword_page.this, "Password Reset Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openSigninPage() {
        Intent intent = new Intent(this, SigninPage.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openVerification(String txtEmail) {
        Intent intent = new Intent(this, passwordupdated_page.class);
        intent.putExtra("keytxtemail1", txtEmail);
        startActivity(intent);
        overridePendingTransition(0, 0);


    }



}


