package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;



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

    Button enter, reset;

    TextView lifepoints, constamount;

    MyDatabaseHelper myDB; // SQLite

    String budget = ""; // constant budget variable
    String pointText = ""; // point variable, changes each arithmetic

    ImageView imageView;
    FloatingActionButton button;


    private ImageManager imageManager;
    private StorageReference storageReference;

    private DatabaseReference databaseReference;

    public Uri imageUri;
    private static final String IMAGE_URI_KEY = "imageUri"; // Key for storing the image URI

    private ProgressBar pointBar;
    private double maxBudget = 0;
    private double currentBudget = 0;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        enter = findViewById(R.id.EnterBtn);
        reset = findViewById(R.id.resetButton);
        expenses = findViewById(R.id.expensesText);
        datentime = findViewById(R.id.text_view_date);
        time = findViewById(R.id.text_view_time);
        lifepoints = findViewById(R.id.lifePoint);
        constamount = findViewById(R.id.constLife);
        description = findViewById(R.id.descriptionText);
        myDB = new MyDatabaseHelper(MainPage.this);
        pointBar = findViewById(R.id.progressBar);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.floatingActionButton);
        imageManager = ImageManager.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();




        getDBData(); // Displays Data

        //Code for bar progress
        String budgetString = constamount.getText().toString();
        if (!budgetString.isEmpty()) {
            maxBudget = Double.parseDouble(budgetString);
            currentBudget = maxBudget;
            pointBar.setMax((int) maxBudget);
            pointBar.setProgress((int) currentBudget);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(MainPage.this).crop().compress(1024).maxResultSize(1080,1080).start();
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String spendingString = expenses.getText().toString();
                if (!spendingString.isEmpty()) {
                    double spendingAmount = Double.parseDouble(spendingString);
                    currentBudget -= spendingAmount;
                    if (currentBudget < 0) {
                        currentBudget = 0;
                    }
                    pointBar.setProgress((int) currentBudget);
                }



                String datentimeTXT = datentime.getText().toString();
                String timeTXT = time.getText().toString();
                String constamountTXT = constamount.getText().toString();
                String expensesTXT = expenses.getText().toString();
                String descriptionTXT = description.getText().toString();
                Boolean checkinsertdata = myDB.insertuserdata(datentimeTXT, timeTXT, constamountTXT, expensesTXT, descriptionTXT);
                if (checkinsertdata) {
                    Toast.makeText(MainPage.this, "SAVED! Check history.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainPage.this, "Did not save.", Toast.LENGTH_SHORT).show();
                }
                updatePoint(); // Updates point each arithmetic
            }
        });

        // Restore the image URI if it is available
        if (imageManager.getImageUri() != null) {
            imageView.setImageURI(imageManager.getImageUri());
        }

        // Set current date and time
        long date = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy ");
        SimpleDateFormat stf = new SimpleDateFormat("hh:mm a");
        String currentDate = sdf.format(date);
        String currentTime = stf.format(date);
        datentime.setText(currentDate);
        time.setText(currentTime);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.bottom_home) {
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










        // Restore the image URI when the activity is recreated
        if (savedInstanceState != null) {
            imageUri = savedInstanceState.getParcelable(IMAGE_URI_KEY);
            if (imageUri != null) {
                imageView.setImageURI(imageUri);
            }
        }

        // daily reset logic button (temporary)
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.resetLocalDatabase();
                startActivity(new Intent(MainPage.this, InputPage.class));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the image URI when the activity is paused
        outState.putParcelable(IMAGE_URI_KEY, imageUri);
    }

    // SQLite Read and Display Data to TextViews
    void getDBData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            if (cursor.moveToFirst()) {
                budget = cursor.getString(1);
                pointText = cursor.getString(2);
                lifepoints.setText(pointText);
                constamount.setText(budget);
            } else {
                cursor.close();
            }
        }
    }

    // SQLite Update Point Each Arithmetic
    void updatePoint() {
        int expensesINT = Integer.valueOf(expenses.getText().toString());
        int pointINT = Integer.valueOf(pointText);
        pointText = String.valueOf(pointINT - expensesINT);
        myDB.updateScore(pointText, "1");
        lifepoints.setText(pointText);


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



}
