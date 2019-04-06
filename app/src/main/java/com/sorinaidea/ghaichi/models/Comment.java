package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

public class Comment extends Model{


    @SerializedName("img")
    private String img;
    @SerializedName("rating")
    private String rating;
    @SerializedName("text")
    private String text;
    @SerializedName("name")
    private String name;
    @SerializedName("date")
    private String date;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
