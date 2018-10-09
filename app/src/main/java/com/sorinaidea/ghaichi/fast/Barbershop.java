package com.sorinaidea.ghaichi.fast;

import java.util.List;

public class Barbershop {

    private int id;
    private String name;
    private String description;
    private String address;
    private String icon;
    private String rating;
    private List<Photo> banners;
    private List<Service> services;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Photo> getBanners() {
        return banners;
    }

    public void setBanners(List<Photo> banners) {
        this.banners = banners;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
