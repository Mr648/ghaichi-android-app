package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

public class Message extends Model {


    @SerializedName("message")
    private String message;

    @SerializedName("date")
    private String date;

    @SerializedName("type")
    private int viewType;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
