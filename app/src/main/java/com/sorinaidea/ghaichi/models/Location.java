package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

public class Location extends Model {


    @SerializedName("name")
    private String name;

    @SerializedName("logo")
    private String logo;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
