package com.sorinaidea.ghaichi.model;

/**
 * Created by mr-code on 3/10/2018.
 */

public class Transaction {
    private String title;
    private String date;
    private int cashValue;

    public String getTitle() {
        return title;
    }

    public int getCashValue() {
        return cashValue;
    }

    public String getDate() {
        return date;
    }

    public void setCashValue(int cashValue) {
        this.cashValue = cashValue;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Transaction(int cashValue, String title, String date){
        this.date = date;
        this.title = title;
        this.cashValue = cashValue;
    }
}

