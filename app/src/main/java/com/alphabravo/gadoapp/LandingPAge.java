package com.alphabravo.gadoapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class LandingPAge extends AppCompatActivity{

    RelativeLayout relay1;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            relay1.setVisibility(View.VISIBLE);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relay1 = (RelativeLayout) findViewById(R.id.relay1);

        handler.postDelayed(runnable, 2000); //
    }
}

