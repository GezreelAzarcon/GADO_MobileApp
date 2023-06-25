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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class usercredential_page extends AppCompatActivity {

    private Button signin, create;
    private EditText email, password;
    private CheckBox check;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.usercredentialpage);

        email = findViewById(R.id.emailText1);
        password = findViewById(R.id.passwordText1);
        check = (CheckBox) findViewById(R.id.signUpCheckBox);
        create = (Button) findViewById(R.id.createBtn);
        signin = (Button) findViewById(R.id.signintextBtn);
        auth = FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSigninPage();

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = email.getText().toString();
                String txtPass = password.getText().toString();

                if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPass) && check.isChecked()) {
                    Toast.makeText(usercredential_page.this, "Credentials are Empty!", Toast.LENGTH_SHORT).show();
                }else if (!check.isChecked()){
                    Toast.makeText(usercredential_page.this, "Accept Terms and Conditions!", Toast.LENGTH_SHORT).show();
                }else if (txtPass.length() < 6){
                    Toast.makeText(usercredential_page.this, "Password should be greater than 6", Toast.LENGTH_SHORT).show();
                }else if (txtPass.length() > 20){
                    Toast.makeText(usercredential_page.this, "Password should be less than 20", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(txtEmail, txtPass);
                }
            }
        });




    }

    private void registerUser(String email, String pass) {

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(usercredential_page.this , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(usercredential_page.this, "User Registration Successful!", Toast.LENGTH_SHORT).show();
                    openVerificationpage();
                    finish();
                }else{
                    Toast.makeText(usercredential_page.this, "User Registration Failed! Check Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openSigninPage(){
        Intent intent = new Intent(this, signin_page.class);
        startActivity(intent);
        finish();

    }

    public void openVerificationpage(){
        Intent intent = new Intent(this, verification_page.class);
        startActivity(intent);

    }


}


