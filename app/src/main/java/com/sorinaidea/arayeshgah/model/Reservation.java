package com.sorinaidea.arayeshgah.model;

/**
 * Created by mr-code on 3/10/2018.
 */

public class Reservation {
    private String title;
    private String date;
    private String time;
    private String address;
    private int imgLogo;


    public Reservation(int imgLogo, String title, String date, String time, String address){
        this.setDate(date);
        this.setTitle(title);
        this.setImgLogo(imgLogo);
        this.setTime(time);
        this.setAddress(address);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImgLogo() {
        return imgLogo;
    }

    public void setImgLogo(int imgLogo) {
        this.imgLogo = imgLogo;
    }
}

