package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;


public class SigninPage extends AppCompatActivity {

    private Button start;
    private EditText email, pass;
    private CheckBox check, privacy;
    private FirebaseAuth auth;
    TextView forgotpass;
    private ImageView question;

    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    public static final String UserEmail = "UserEmail";
    public static final String UserPass = "UserPass";

    private TextView Login;


    //firebase & sqlite
    MyDatabaseHelper myDatabase; // SQLite
    FirebaseAuth fAuth;
    DatabaseReference databaseHistoryData;
    String userID;
    //firebase

    //try
    TextView signInText;
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();
    ArrayList<String> budgets = new ArrayList<>();
    ArrayList<String> expense = new ArrayList<>();
    ArrayList<String> descriptions = new ArrayList<>();

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
        setContentView(R.layout.signinpage);

        //sql
        myDatabase = new MyDatabaseHelper(SigninPage.this);

        email = findViewById(R.id.emailText2);
        pass = findViewById(R.id.passwordText2);
        check = (CheckBox) findViewById(R.id.SignInCheckBox);
        privacy = (CheckBox) findViewById(R.id.PrivacyCheckBox);
        Paper.init(this);
        start = (Button) findViewById(R.id.startBtn);
        forgotpass = (TextView) findViewById(R.id.forgotBtn);
        auth = FirebaseAuth.getInstance();

        String UserEmail1 = Paper.book().read("UserEmail");
        String UserPass1 = Paper.book().read("UserPass");
        if (UserPass1 != "" && UserPass1 != ""){
            if (!TextUtils.isEmpty(UserEmail1) && !TextUtils.isEmpty(UserPass1)){
                AllowAccess(UserEmail1, UserPass1);
            }
        }

        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        start.setEnabled(false);

        //firebase
        fAuth = FirebaseAuth.getInstance();
        //firebase
        Login = (TextView) findViewById(R.id.logintextBtn);
        question= (ImageView) findViewById(R.id.questionButton);

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignupPage();

            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotpass();
            }
        });


        privacy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton CompoundButton, boolean b) {
                if (b) {
                    materialAlertDialogBuilder.setTitle("Privacy policy");
                    materialAlertDialogBuilder.setMessage("Last updated June 27, 2023\n" +
                            "This privacy notice for QuadroZENlX (\"Company,\" \"we,\" \"us,\" or \"our\"), describes how and why we might collect, store, use, and/or share (\"process\") your information when you use our services (\"Services\"), such as when you:\n" +
                            "•\tDownload and use our mobile application (GADO (jj— F)), or any other application of ours that links to this privacy notice.\n" +
                            "•\tEngage with us in other related ways, including any sales, marketing, or events.\n" +
                            "Questions or concerns? Reading this privacy notice will help you understand your privacy rights and choices. If you do not agree with our policies and practices, please do not use our Services. If you still have any questions or concerns, please contact us at gezreelwazarcon@gmail.com.\n" +
                            "\n" +
                            "SUMMARY OF KEY POINTS\n" +
                            "This summary provides key points from our privacy notice, but you can find out more details about any of these topics by clicking the link following each key point or by using our table of contents below to find the section you are looking for.\n" +
                            "What personal information do we process? When you visit, use, or navigate our Services, we may process personal information depending on how you interact with QuadroZENIX and the Services, the choices you make, and the products and features you use. Learn more about personal information you disclose to us.\n" +
                            "Do we process any sensitive personal information? We do not process sensitive personal information.\n" +
                            "Do we receive any information from third parties? We do not receive any information from third parties.\n" +
                            "How do we process your information? We process your information to provide, improve, and administer our Services, communicate with you, for security and fraud prevention, and to comply with law. We may also process your information for other purposes with your consent. We process your information only when we have a valid legal reason to do so. Learn more about how we process your information.\n" +
                            "In what situations and with which parties do we share personal information? We may share information in specific situations and with specific third parties. Learn more about when and with whom we share your personal information.\n" +
                            "How do we keep your information safe? We have organizational and technical processes and procedures in place to protect your personal information. However, no electronic transmission over the internet or information storage technology can be guaranteed to be 100% secure, so we cannot promise or guarantee that hackers, cybercriminals, or other unauthorized third parties will not be able to defeat our security and improperly collect, access, steal, or modify your information. Learn more about how we keep your information safe.\n" +
                            "What are your rights? Depending on where you are located geographically, the applicable privacy law may mean you have certain rights regarding your personal information. Learn more about your privacy rights.\n" +
                            "How do you exercise your rights? The easiest way to exercise your rights is by submitting a data subject access request, or by contacting us. We will consider and act upon any request in accordance with applicable data protection laws.\n" +
                            "Want to learn more about what QuadroZENlX does with any information we collect? Review the privacy notice in full.\n" +
                            "\n" +
                            "TABLE OF CONTENTS\n" +
                            "1. WHAT INFORMATION DO WE COLLECT?\n" +
                            "2. HOW DO WE PROCESS YOUR INFORMATION?\n" +
                            "3. WHEN AND WITH WHOM DO WE SHARE YOUR PERSONAL INFORMATION?\n" +
                            "4. WHAT IS OUR STANCE ON THIRD-PARTY WEBSITES?\n" +
                            "5. HOW DO WE HANDLE YOUR SOCIAL LOGINS?\n" +
                            "6. HOW LONG DO WE KEEP YOUR INFORMATION?\n" +
                            "7. HOW DO WE KEEP YOUR INFORMATION SAFE?\n" +
                            "8. WHAT ARE YOUR PRIVACY RIGHTS?\n" +
                            "9. CONTROLS FOR DO-NOT-TRACK FEATURES\n" +
                            "10. DO CALIFORNIA RESIDENTS HAVE SPECIFIC PRIVACY RIGHTS?\n" +
                            "11. DO WE MAKE UPDATES TO THIS NOTICE?\n" +
                            "12. HOW CAN YOU CONTACT US ABOUT THIS NOTICE?\n" +
                            "13. HOW CAN YOU REVIEW, UPDATE, OR DELETE THE DATA WE COLLECT FROM YOU?\n" +
                            "\n" +
                            "1. WHAT INFORMATION DO WE COLLECT?\n" +
                            "Personal information you disclose to us\n" +
                            "In Short: We collect personal information that you provide to us.\n" +
                            "We collect personal information that you voluntarily provide to us when you register on the Services, express an interest in obtaining information about us or our products and Services, when you participate in activities on the Services, or otherwise when you contact us.\n" +
                            "Personal Information Provided by You. The personal information that we collect depends on the context of your interactions with us and the Services, the choices you make, and the products and features you use. The personal information we collect may include the following:\n" +
                            "•\temail addresses\n" +
                            "•\tusernames\n" +
                            "•\tpasswords\n" +
                            "•\tcontact or authentication data.\n" +
                            "Sensitive Information. We do not process sensitive information.\n" +
                            "Social Media Login Data. We may provide you with the option to register with us using your existing social media account details, like your Facebook, Twitter, or other social media account. If you choose to register in this way, we will collect the information described in the section called \"HOW DO WE HANDLE YOUR SOCIAL LOGINS?\" below.\n" +
                            "All personal information that you provide to us must be true, complete, and accurate, and you must notify us of any changes to such personal information.\n" +
                            "\n" +
                            "2. HOW DO WE PROCESS YOUR INFORMATION?\n" +
                            "In Short: We process your information to provide, improve, and administer our Services, communicate with you, for security and fraud prevention, and to comply with law. We may also process your information for other purposes with your consent.\n" +
                            "We process your personal information for a variety of reasons, depending on how you interact with our Services, including:\n" +
                            "•\tTo facilitate account creation and authentication and otherwise manage user accounts. We may process your information so you can create and log in to your account, as well as keep your account in working order.\n" +
                            "\n" +
                            "3. WHEN AND WITH WHOM DO WE SHARE YOUR PERSONAL INFORMATION?\n" +
                            "In Short: We may share information in specific situations described in this section and/or with the following third parties.\n" +
                            "We may need to share your personal information in the following situations:\n" +
                            "•\tBusiness Transfers. We may share or transfer your information in connection with, or during negotiations of, any merger, sale of company assets, financing, or acquisition of all or a portion of our business to another company.\n" +
                            "4. WHAT IS OUR STANCE ON THIRD-PARTY WEBSITES?\n" +
                            "In Short: We are not responsible for the safety of any information that you share with third parties that we may link to or who advertise on our Services, but are not affiliated with, our Services.\n" +
                            "The Services may link to third-party websites, online services, or mobile applications and/or contain advertisements from third parties that are not affiliated with us and which may link to other websites, services, or applications. Accordingly, we do not make any guarantee regarding any such third parties, and we will not be liable for any loss or damage caused by the use of such third-party websites, services, or applications. The inclusion of a link towards a third-party website, service, or application does not imply an endorsement by us. We cannot guarantee the safety and privacy of data you provide to any third parties. Any data collected by third parties is not covered by this privacy notice. We are not responsible for the content or privacy and security practices and policies of any third parties, including other websites, services, or applications that may be linked to or from the Services. You should review the policies of such third parties and contact them directly to respond to your questions.\n" +
                            "\n" +
                            "5. HOW DO WE HANDLE YOUR SOCIAL LOGINS?\n" +
                            "In Short: If you choose to register or log in to our Services using a social media account, we may have access to certain information about you.\n" +
                            "Our Services offer you the ability to register and log in using your third-party social media account details (like your Facebook or Twitter logins). Where you choose to do this, we will receive certain profile information about you from your social media provider. The profile information we receive may vary depending on the social media provider concerned, but will often include your name, email address, friends list, and profile picture, as well as other information you choose to make public on such a social media platform. \n" +
                            "We will use the information we receive only for the purposes that are described in this privacy notice or that are otherwise made clear to you on the relevant Services. Please note that we do not control, and are not responsible for, other uses of your personal information by your third-party social media provider. We recommend that you review their privacy notice to understand how they collect, use, and share your personal information, and how you can set your privacy preferences on their sites and apps.\n" +
                            "\n" +
                            "6. HOW LONG DO WE KEEP YOUR INFORMATION?\n" +
                            "In Short: We keep your information for as long as necessary to fulfil/ the purposes outlined in this privacy notice unless otherwise required by law.\n" +
                            "We will only keep your personal information for as long as it is necessary for the purposes set out in this privacy notice, unless a longer retention period is required or permitted by law (such as tax, accounting, or other legal requirements). No purpose in this notice will require us keeping your personal information for longer than the period of time in which users have an account with us.\n" +
                            "When we have no ongoing legitimate business need to process your personal information, we will either delete or anonymize such information, or, if this is not possible (for example, because your personal information has been stored in backup archives), then we will securely store your personal information and isolate it from any further processing until deletion is possible.\n" +
                            "\n" +
                            "7. HOW DO WE KEEP YOUR INFORMATION SAFE?\n" +
                            "In Short: We aim to protect your personal information through a system of organizational and technical security measures.\n" +
                            "We have implemented appropriate and reasonable technical and organizational security measures designed to protect the security of any personal information we process. However, despite our safeguards and efforts to secure your information, no electronic transmission over the Internet or information storage technology can be guaranteed to be 100% secure, so we cannot promise or guarantee that hackers, cybercriminals, or other unauthorized third parties will not be able to defeat our security and improperly collect, access, steal, or modify your information. Although we will do our best to protect your personal information, transmission of personal information to and from our Services is at your own risk. You should only access the Services within a secure environment.\n" +
                            "\n" +
                            "8. WHAT ARE YOUR PRIVACY RIGHTS?\n" +
                            "In Short: You may review, change, or terminate your account at any time.\n" +
                            "If you are located in the EEA or UK and you believe we are unlawfully processing your personal information, you also have the right to complain to your Member State data protection authority or UK data protection authority.\n" +
                            "If you are located in Switzerland, you may contact the Federal Data Protection and Information Commissioner.\n" +
                            "Withdrawing your consent: If we are relying on your consent to process your personal information, which may be express and/or implied consent depending on the applicable law, you have the right to withdraw your consent at any time. You can withdraw your consent at any time by contacting us by using the contact details provided in the section \"HOW CAN YOU CONTACT US ABOUT THIS NOTICE?\" below.\n" +
                            "However, please note that this will not affect the lawfulness of the processing before its withdrawal nor, when applicable law allows, will it affect the processing of your personal information conducted in reliance on lawful processing grounds other than consent.\n" +
                            "Account Information\n" +
                            "If you would at any time like to review or change the information in your account or terminate your account, you can:\n" +
                            "•\tLog in to your account settings and update your user account.\n" +
                            "Upon your request to terminate your account, we will deactivate or delete your account and information from our active databases. However, we may retain some information in our files to prevent fraud, troubleshoot problems, assist with any investigations, enforce our legal terms and/or comply with applicable legal requirements.\n" +
                            "If you have questions or comments about your privacy rights, you may email us at gezreelwazarcon@gmail.com.\n" +
                            "\n" +
                            "9. CONTROLS FOR DO-NOT-TRACK FEATURES\n" +
                            "Most web browsers and some mobile operating systems and mobile applications include a Do-Not-Track (\"DNT\") feature or setting you can activate to signal your privacy preference not to have data about your online browsing activities monitored and collected. At this stage no uniform technology standard for recognizing and implementing DNT signals has been finalized. As such, we do not currently respond to DNT browser signals or any other mechanism that automatically communicates your choice not to be tracked online. If a standard for online tracking is adopted that we must follow in the future, we will inform you about that practice in a revised version of this privacy notice.\n" +
                            "\n" +
                            "10. DO CALIFORNIA RESIDENTS HAVE SPECIFIC PRIVACY RIGHTS?\n" +
                            "In Short: Yes, if you are a resident of California, you are granted specific rights regarding access to your personal information.\n" +
                            "California Civil Code Section 1798.83, also known as the \"Shine The Light\" law, permits our users who are California residents to request and obtain from us, once a year and free of charge, information about categories of personal information (if any) we disclosed to third parties for direct marketing purposes and the names and addresses of all third parties with which we shared personal information in the immediately preceding calendar year. If you are a California resident and would like to make such a request, please submit your request in writing to us using the contact information provided below.\n" +
                            "If you are under 18 years of age, reside in California, and have a registered account with Services, you have the right to request removal of unwanted data that you publicly post on the Services. To request removal of such data, please contact us using the contact information provided below and include the email address associated with your account and a statement that you reside in California. We will make sure the data is not publicly displayed on the Services, but please be aware that the data may not be completely or comprehensively removed from all our systems (e.g., backups, etc.).\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "11. DO WE MAKE UPDATES TO THIS NOTICE?\n" +
                            "In Short: Yes, we will update this notice as necessary to stay compliant with relevant laws.\n" +
                            "We may update this privacy notice from time to time. The updated version will be indicated by an updated \"Revised\" date and the updated version will be effective as soon as it is accessible. If we make material changes to this privacy notice, we may notify you either by prominently posting a notice of such changes or by directly sending you a notification. We encourage you to review this privacy notice frequently to be informed of how we are protecting your information.\n" +
                            "\n" +
                            "12. HOW CAN YOU CONTACT US ABOUT THIS NOTICE?\n" +
                            "If you have questions or comments about this notice, you may email us at villarizaced@gmail.com or contact us by post at:\n" +
                            "QuadroZENlX\n" +
                            "Anonas cor.\n" +
                            "Pureza Street\n" +
                            "Sta. Mesa, Manila, Metro Manila 1016\n" +
                            "Philippines\n" +
                            "\n" +
                            "13. HOW CAN YOU REVIEW, UPDATE, OR DELETE THE DATA WE COLLECT FROM YOU?\n" +
                            "Based on the applicable laws of your country, you may have the right to request access to the personal information we collect from you, change that information, or delete it. To request to review, update, or delete your personal information, please fill out and submit a data subject access request.\n ");
                    materialAlertDialogBuilder.setPositiveButton("I Agree!", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            start.setEnabled(true);
                            dialogInterface.dismiss();

                        }
                    });
                    materialAlertDialogBuilder.show();
                }else {
                    start.setEnabled(true);


                }

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setEnabled(false);
                String txtEmail = email.getText().toString();
                String txtPass = pass.getText().toString();

                if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPass) && check.isChecked()) {
                    Toast.makeText(SigninPage.this, "Credentials are Empty!", Toast.LENGTH_SHORT).show();
                }else if (!privacy.isChecked()){
                    Toast.makeText(SigninPage.this, "Check Privacy Policy!", Toast.LENGTH_SHORT).show();
                }else {
                    myDatabase.resetLocalDatabase();
                    myDatabase.resetLocalHistoryDatabase();
                    loginUser(txtEmail, txtPass);
                }
            }
        });
        }

    private void alertDialog() {
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
                start.setEnabled(true);
                dialogInterface.dismiss();

            }
        });
        builder.create().show();

    }



    private void retrieveHistoryData() {

        userID = fAuth.getCurrentUser().getUid();
        databaseHistoryData = FirebaseDatabase.getInstance().getReference(userID);
        databaseHistoryData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot zoneSnapshot: snapshot.getChildren()) {
                    String date = zoneSnapshot.child("date").getValue(String.class);
                    dates.add(date);

                    String time = zoneSnapshot.child("time").getValue(String.class);
                    times.add(time);

                    String budget = zoneSnapshot.child("budget").getValue(String.class);
                    budgets.add(budget);

                    String expenses = zoneSnapshot.child("expenses").getValue(String.class);
                    expense.add(expenses);

                    String description = zoneSnapshot.child("description").getValue(String.class);
                    descriptions.add(description);
                }
                addHistoryDataToSQLite();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SigninPage.this, "Failed", Toast.LENGTH_SHORT).show();

            }

        });
    }


    private void addHistoryDataToSQLite() {
        int size = dates.size();
        for (int i = 0; i < size ; i++) {
            myDatabase.insertuserdata(dates.get(i), times.get(i), budgets.get(i), expense.get(i), descriptions.get(i));
        }
    }

    private void AllowAccess(String userEmail1, String userPass1) {
        auth.signInWithEmailAndPassword(userEmail1, userPass1).addOnCompleteListener(SigninPage.this , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SigninPage.this, "Log In Successful!", Toast.LENGTH_SHORT).show();
                    openMainPage();
                }else{
                    Toast.makeText(SigninPage.this, "Log In Failed! Check Credentials!", Toast.LENGTH_SHORT).show();
                    start.setEnabled(true);
                }
            }
        });
    }

    private void loginUser(String email, String pass) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(SigninPage.this , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && check.isChecked()) {
                    Paper.book().write("UserEmail", email);
                    Paper.book().write("UserPass", pass);
                    Toast.makeText(SigninPage.this, "Log In Successful!", Toast.LENGTH_SHORT).show();
                    openInputPage();
                }else if (task.isSuccessful()){
                    Toast.makeText(SigninPage.this, "Log In Successful!", Toast.LENGTH_SHORT).show();
                    openInputPage();
                }else {
                    Toast.makeText(SigninPage.this, "Log In Failed! Check Credentials!", Toast.LENGTH_SHORT).show();
                    start.setEnabled(true);
                }
            }
        });
    }

    public void openInputPage(){
        retrieveHistoryData();
        Intent intent = new Intent(this, WelcomeuserPage.class);
        startActivity(intent);
        finish();

    }

    public void openForgotpass() {
        Intent intent = new Intent(this, forgotpassword_page.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


    }

    public void openSignupPage(){
        Intent intent = new Intent(this, SignupPage.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openMainPage() {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
        finish();
    }

}


