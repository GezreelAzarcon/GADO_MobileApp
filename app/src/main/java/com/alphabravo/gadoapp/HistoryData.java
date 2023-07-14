package com.alphabravo.gadoapp;

public class HistoryData {

    String date;
    String time;
    String budget;
    String expenses;
    String description;

    public HistoryData(String date, String time, String budget, String expenses, String description) {
        this.date = date;
        this.time = time;
        this.budget = budget;
        this.expenses = expenses;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getBudget() {
        return budget;
    }

    public String getExpenses() {
        return expenses;
    }

    public String getDescription() {
        return description;
    }
}
