package com.alphabravo.gadoapp;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

class ImageManager {
    private static ImageManager instance;
    private Uri imageUri;

    private ImageManager() {
        // Private constructor to prevent instantiation
    }

    public static ImageManager getInstance() {
        if (instance == null) {
            instance = new ImageManager();
        }
        return instance;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}


public class MainPage extends AppCompatActivity {
    EditText expenses, datentime, time, description;


    TextView lifepoints, constamount;


    MyDatabaseHelper myDB; // SQLite

    String budget = ""; //constant budget variable
    String pointText = ""; //point variable, changes each arithmetic

    // pointText / budget --> TextView format

    //firebase
    FirebaseAuth fAuth;
    String userID;

    DatabaseReference firebaseWrite;

    private ProgressBar pointBar;
    private double maxBudget = 0;
    private double currentBudget = 0;

    ImageView imageView, button, enter;
    private ImageManager imageManager;
    private StorageReference storageReference;

    private DatabaseReference databaseReference;

    public Uri imageUri;
    private static final String IMAGE_URI_KEY = "imageUri"; // Key for storing the image URI
    private LottieAnimationView anim;

    private TextView contactus;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;
    private boolean animationPlayed = false;


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




    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        enter = findViewById(R.id.EnterBtn);
        expenses = findViewById(R.id.expensesText);
        datentime = findViewById(R.id.text_view_date);
        time = findViewById(R.id.text_view_time);
        lifepoints = findViewById(R.id.lifePoint);
        constamount = findViewById(R.id.constLife);
        description = findViewById(R.id.descriptionText);
        anim = findViewById(R.id.fallingcoin);

        //firebase
        fAuth = FirebaseAuth.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        firebaseWrite = FirebaseDatabase.getInstance().getReference(userID);


        //firebase

        //sql
        myDB = new MyDatabaseHelper(MainPage.this);

        pointBar = findViewById(R.id.progressBar);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.floatingActionButton);
        imageManager = ImageManager.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();


        getDBData(); // Displays Data
        contactus = (TextView) findViewById(R.id.contactus);
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);




        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialAlertDialogBuilder.setTitle("Send us Feedback, Suggestions, or Concerns.");
                materialAlertDialogBuilder.setMessage("gezreelwazrcon@gmail.com\n" + "villarizaced@gmail.com\n");
                materialAlertDialogBuilder.show();
            }
        });





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(MainPage.this).crop().compress(1024).maxResultSize(1080,1080).start();
            }
        });



        enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!animationPlayed) {
                    anim.setRepeatCount(0); // Set the repeat count to 0 (play once)
                    expensesCheck(); // Updates point each arithmetic
                    animationPlayed = true; // Set the flag to true after playing the animation
                } else {
                    anim.setRepeatCount(0); // Set the repeat count to 1 (play once again)
                    anim.playAnimation();
                }

            }


        });
        // Add an AnimationListener to the animation
        anim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Animation starts
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation ends
                if (animationPlayed) {
                    anim.setRepeatCount(0); // Set the repeat count to 0 if animation is played once
                    animationPlayed = false; // Reset the animationPlayed flag to allow playing on the next click
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Animation canceled
                stopAnimation(); // Ensure the animation is stopped if it's canceled
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Animation repeats (if set to repeat)
            }
        });


// Method to stop the animation


        long date = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("hh:mm a");
        String currentDate = sdf.format(date);
        String currentTime = stf.format(date);

        EditText datentime = findViewById(R.id.text_view_date);
        EditText time = findViewById(R.id.text_view_time);
        datentime.setText(currentDate);
        time.setText(currentTime);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.bottom_budget) {
                startActivity(new Intent(getApplicationContext(), InputPage.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.bottom_home) {
                return true;
            } else if (item.getItemId() == R.id.bottom_history) {
                startActivity(new Intent(getApplicationContext(), HistoryPage.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.bottom_settings) {
                startActivity(new Intent(getApplicationContext(), SettingsPage.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

        // Restore the image URI if it is available
        if (imageManager.getImageUri() != null) {
            imageView.setImageURI(imageManager.getImageUri());
        }

        // Restore the image URI when the activity is recreated
        if (savedInstanceState != null) {
            imageUri = savedInstanceState.getParcelable(IMAGE_URI_KEY);
            if (imageUri != null) {
                imageView.setImageURI(imageUri);
            }
        }

        // removed
        //String userAmount = getIntent().getStringExtra("amountUser");


        //daily reset logic button (temporary)


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the image URI when the activity is paused
        outState.putParcelable(IMAGE_URI_KEY, imageUri);
    }

    private void expensesCheck() {
        String expense = expenses.getText().toString();
        String desc = description.getText().toString();
        if (expense.matches("") || desc.matches("")) {
            Toast.makeText(this, "There's no value!", Toast.LENGTH_SHORT).show();
        }else {
            updatePoint();
            addHistoryData();
            anim.playAnimation();
        }
    }

    private void addHistoryData() {
        String date = datentime.getText().toString().trim();
        String timeHistory = time.getText().toString().trim();
        String budget1 = constamount.getText().toString().trim();
        String budget = pointText + "/" + budget1;
        String expense = expenses.getText().toString().trim();
        String historyDescription = description.getText().toString().trim();

        myDB.insertuserdata(date, timeHistory, budget, expense, historyDescription);
    }

    private void addProgressData() {
        String spendingString = expenses.getText().toString();
        if (spendingString.matches("")) {
            double spendingAmount = 0;
            currentBudget -= spendingAmount;
            pointBar.setProgress((int) currentBudget);
        }else {
            double spendingAmount = Double.parseDouble(spendingString);
            currentBudget -= spendingAmount;
            if (currentBudget < 0) {
                currentBudget = 0;
            }
            pointBar.setProgress((int) currentBudget);
        }

    }

    private void pointLife() {
        String budgetString = budget;
        String currentString = pointText;
        maxBudget = Double.parseDouble(budgetString);
        currentBudget = Double.parseDouble(currentString);
        pointBar.setMax((int) maxBudget);
        pointBar.setProgress((int) currentBudget);
    }



    // SQLite Read and Display Data to TextViews
    void getDBData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            if (cursor.moveToFirst()){
                budget = cursor.getString(1);
                pointText = cursor.getString(2);
                lifepoints.setText(pointText);
                constamount.setText(budget);
                pointLife();
            }else {
                cursor.close();
            }
        }
    }


    // SQLite Update Point Each Arithmetic
    void updatePoint() {
        String expensesSTR = expenses.getText().toString();
        int pointINT = Integer.parseInt(pointText);

        if (expensesSTR.matches("")) {
            expensesSTR = "0";
            int expensesINT = Integer.valueOf(expensesSTR);
            pointText = String.valueOf(pointINT - expensesINT);
            myDB.updateScore(pointText, "1");
            getDBData();
        }else {
            int expensesINT = Integer.valueOf(expenses.getText().toString());
            pointText = String.valueOf(pointINT - expensesINT);
            myDB.updateScore(pointText, "1");
            lifepoints.setText(pointText);
            getDBData();
        }
    }


    public void openhistory_page() {
        Intent intent = new Intent(this, HistoryPage.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

            // Store the image URI in the ImageManager
            imageManager.setImageUri(imageUri);

            uploadPicture();
        }
    }


    private void stopAnimation() {
        anim.cancelAnimation(); // Stops the animation
        animationPlayed = false; // Reset the animationPlayed flag to allow playing on the next click
        enter.setEnabled(true);

    }

    // Code to save the picture to Firebase Storage and Realtime database
    private void uploadPicture() {
        if (imageUri != null) {
            String key = databaseReference.child("images").push().getKey();
            StorageReference imageRef = storageReference.child("images/" + key + ".jpg");
            UploadTask uploadTask = imageRef.putFile(imageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // Image upload success
                // Get the download URL of the uploaded image
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageURL = uri.toString();
                    // Store the image URL in the Realtime Database
                    databaseReference.child("images").child(key).setValue(imageURL)
                            .addOnSuccessListener(aVoid -> {
                                // Image URL stored successfully
                            })
                            .addOnFailureListener(e -> {
                                // Handle any errors that occurred while storing the image URL
                            });
                }).addOnFailureListener(e -> {
                    // Handle any errors that occurred while retrieving the download URL
                });
            }).addOnFailureListener(e -> {
                // Handle any errors that occurred during image upload
            });
        }
    }

}