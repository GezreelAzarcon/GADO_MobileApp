package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class passwordupdated_page extends AppCompatActivity {

    private Button signin;
    private TextView gmail;

    ImageView imageView;

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
        setContentView(R.layout.passwordupdatedpage);

        signin = (Button) findViewById(R.id.signinPassUpdtdBtn);
        gmail = findViewById(R.id.usergmail);
        imageView = findViewById(R.id.google);
        String useremail = getIntent().getStringExtra("keytxtemail1");
        gmail.setText(useremail);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignin();

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mUri = Uri.parse("market://details?id=" + "com.google.android.gm");
                Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
                startActivity(mIntent );
            }
        });

    }
    public void openSignin(){
        Intent intent = new Intent(this, SigninPage.class);
        startActivity(intent);

    }


}


