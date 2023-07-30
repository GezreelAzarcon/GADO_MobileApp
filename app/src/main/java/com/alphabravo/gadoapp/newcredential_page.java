package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class newcredential_page extends AppCompatActivity {

    private Button update;
    private EditText pass1, pass2;
    private ImageView backbtn;

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


    @Override @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.newcredential);

        pass1 = findViewById(R.id.passwordnew1);
        pass2 = findViewById(R.id.passwordnew2);
        update = (Button) findViewById(R.id.updateBtn);
        backbtn = findViewById(R.id.backbtn);
        ImageView imageViewShowpass1 = findViewById(R.id.hidepass1);
        ImageView imageViewShowpass2 = findViewById(R.id.hidepass2);
        imageViewShowpass1.setImageResource(R.drawable.hide);
        imageViewShowpass2.setImageResource(R.drawable.hide);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVeriNewPass();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPasswordupdated();

            }
        });

        imageViewShowpass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass1.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    pass1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowpass1.setImageResource(R.drawable.hide);
                } else {
                    pass1.setTransformationMethod((HideReturnsTransformationMethod.getInstance()));
                    imageViewShowpass1.setImageResource(R.drawable.view);
                }
            }
        });
        imageViewShowpass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass2.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    pass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowpass2.setImageResource(R.drawable.hide);
                } else {
                    pass2.setTransformationMethod((HideReturnsTransformationMethod.getInstance()));
                    imageViewShowpass2.setImageResource(R.drawable.view);
                }
            }
        });

    }

    private void openVeriNewPass() {
        Intent intent = new Intent(this, verificationnewpass_page.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void openPasswordupdated() {
        Intent intent = new Intent(this, passwordupdated_page.class);
        startActivity(intent);

    }


}


