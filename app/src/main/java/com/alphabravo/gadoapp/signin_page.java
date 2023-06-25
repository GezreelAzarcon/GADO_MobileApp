package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class signin_page extends AppCompatActivity {

    private Button start;
    private EditText email, pass;
    private CheckBox check;
    private FirebaseAuth auth;

    private static final String UserEmail = "UserEmail";
    private static final String UserPass = "UserPass";

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.signinpage);

        email = findViewById(R.id.emailText2);
        pass = findViewById(R.id.passwordText2);
        check = (CheckBox) findViewById(R.id.SignInCheckBox);
        Paper.init(this);
        start = (Button) findViewById(R.id.startBtn);
        auth = FirebaseAuth.getInstance();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = email.getText().toString();
                String txtPass = pass.getText().toString();
                loginUser(txtEmail, txtPass);
            }
        });

        String UserEmail1 = Paper.book().read(UserEmail);
        String UserPass1 = Paper.book().read(UserPass);
        if (UserPass1 != "" && UserPass1 != ""){
            if (!TextUtils.isEmpty(UserEmail1) && !TextUtils.isEmpty(UserPass1)){
                AllowAccess(UserEmail1, UserPass1);
            }
        }



    }

    private void AllowAccess(String userEmail1, String userPass1) {
        auth.signInWithEmailAndPassword(userEmail1, userPass1).addOnCompleteListener(signin_page.this , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(signin_page.this, "Log In Successful!", Toast.LENGTH_SHORT).show();
                    openInputPage();
                    finish();
                }else if (task.isSuccessful()){

                }else{
                    Toast.makeText(signin_page.this, "Log In Failed! Check Credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(String email, String pass) {

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(signin_page.this , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && check.isChecked()) {

                    Paper.book().write(UserEmail, email);
                    Paper.book().write(UserPass, pass);

                    Toast.makeText(signin_page.this, "Log In Successful!", Toast.LENGTH_SHORT).show();
                    openInputPage();
                    finish();
                }else if (task.isSuccessful()){
                    Toast.makeText(signin_page.this, "Log In Successful!", Toast.LENGTH_SHORT).show();
                    openInputPage();
                    finish();
                }else{
                    Toast.makeText(signin_page.this, "Log In Failed! Check Credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void openInputPage(){
        Intent intent = new Intent(this, InputPage.class);
        startActivity(intent);

    }

}


