package com.alphabravo.gadoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "booklibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_BUDGET = "budget";
    private static final String COLUMN_POINTS = "points";
    //public static final String DATE = "date"

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BUDGET + " TEXT, "
                + COLUMN_POINTS + " REAL)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addBook(String budget, String points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BUDGET, budget);
        cv.put(COLUMN_POINTS, points);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success Added", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //daily reset logic
    void resetLocalDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Data Reset", Toast.LENGTH_SHORT).show();
        }
    }



    void updateScore(String point, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_POINTS, point);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

}
