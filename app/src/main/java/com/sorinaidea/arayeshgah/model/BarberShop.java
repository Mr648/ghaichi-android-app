package com.sorinaidea.arayeshgah.model;

/**
 * Created by mr-code on 4/10/2018.
 */

public class BarberShop {
    private int id;
    private int logo;
    private String title;
    private String address;
    private int rating;
    private boolean isBookmarked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BarberShop(int logo){
        this.setLogo(logo);
    }
    public BarberShop(int logo, String title){
        this.setLogo(logo);
        this.setTitle(title);
    }
    public BarberShop(int logo,
                      String title,
                      String address,
                      int rating,
                      boolean isBookmarked) {
        this.setAddress(address);
        this.setBookmarked(isBookmarked);
        this.setTitle(title);
        this.setLogo(logo);
        this.setRating(rating);
    }


    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}
