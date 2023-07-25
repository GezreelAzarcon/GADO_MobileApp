package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button getstarted;
    private TextView aboutgado;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_main);

        getstarted = (Button) findViewById(R.id.get_started);
        aboutgado = (TextView) findViewById(R.id.aboutgado);

        aboutgado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGado();
            }
        });
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInputPage();

            }
        });

    }

    private void openGado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GĀDO App");
        builder.setMessage("What is GĀDO?\n" +
                "Inspired by the Japanese pronunciation of the word guard, GĀDO is a game-based and self-help Android application that aims to solve the budgeting problems of its users. It aims to help its users build budgeting skills and discipline on their own. The application targets audiences who are having a hard time maintaining and keeping track of their budget.\n" +
                "\n" +
                "How to use it?\n" +
                "The application will ask for the user’s daily budget and uses it as the game's life/health points, and the goods, foods, commodities, etc. that are bought by the user are considered the enemy. The app has a simple goal in mind, it is to “guard” the game’s life which is your budget.\n" +
                "\n" +
                "How will the user benefit?\n" +
                "The audience will benefit from the application by being reassured and by having their budget maintained and monitored, it may also help them self-develop budgeting skills and discipline as it is a self-help application. It is an app that is game-based so it might give positivity and enjoyment instead of stress and anxiety, it can also help them reevaluate their current budget for the optimal ‘game outcome’ that would directly translate to optimal budgeting in real life.");
        builder.setPositiveButton("Skip", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.create().show();
    }

    public void openInputPage(){
        Intent intent = new Intent(this, SignupPage.class);
        startActivity(intent);
        finish();

    }

}




