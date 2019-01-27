package com.sorinaidea.ghaichi.fast;

public class BarberInfo {

    private int id;
    private String name;
    private String family;
    private String imageUrl;
    private String mobile;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public BarberInfo(int id,
                      String name,
                      String family,
                      String imageUrl, String mobile) {

        this.id = id;
        this.name = name;
        this.family = family;
        this.imageUrl = imageUrl;
        this.mobile = mobile;
    }
}
