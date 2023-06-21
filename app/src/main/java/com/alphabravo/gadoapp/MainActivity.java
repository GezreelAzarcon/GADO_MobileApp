package com.alphabravo.gadoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button button;

    RelativeLayout relay1;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            relay1.setVisibility(View.VISIBLE);

        }
    };


    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_main);

        relay1 = (RelativeLayout) findViewById(R.id.relay1);
        button = (Button) findViewById(R.id.get_started);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInputPage();

            }
        });
        handler.postDelayed(runnable, 2000); //
    }
    public void openInputPage(){
        Intent intent = new Intent(this, InputPage.class);
        startActivity(intent);

    }

}




