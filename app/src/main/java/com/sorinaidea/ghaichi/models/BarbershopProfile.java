package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarbershopProfile extends BaseBarbershop {

    @SerializedName("about")
    private String about;
    @SerializedName("services_count")
    private int servicesCount;
    @SerializedName("barbers_count")
    private int barbersCount;
    @SerializedName("address")
    private String address;
    @SerializedName("rating")
    private String rating;
    @SerializedName("banners")
    private List<String> banners;
    @SerializedName("services")
    private List<BannerService> services;

    @SerializedName("barbers")
    private List<BarberShortInfo> barbers;


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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<String> getBanners() {
        return banners;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }

    public List<BannerService> getServices() {
        return services;
    }

    public void setServices(List<BannerService> services) {
        this.services = services;
    }

    public void setBarbersCount(int barbersCount) {
        this.barbersCount = barbersCount;
    }

    public int getBarbersCount() {
        return barbersCount;
    }

    public void setServicesCount(int servicesCount) {
        this.servicesCount = servicesCount;
    }

    public int getServicesCount() {
        return servicesCount;
    }

    public void setBarbers(List<BarberShortInfo> barbers) {
        this.barbers = barbers;
    }

    public List<BarberShortInfo> getBarbers() {
        return barbers;
    }
}
