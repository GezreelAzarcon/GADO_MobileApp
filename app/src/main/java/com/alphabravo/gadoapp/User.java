package com.alphabravo.gadoapp;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    private String expenses;

    private String datentime;

    public User() {
    }

    public User(String expenses, String datentime) {
        this.expenses = expenses;
        this.datentime = datentime;
    }

    public String getExpenses() {

        return expenses;
    }

    public String getDatentime() {

        return datentime;
    }
}
