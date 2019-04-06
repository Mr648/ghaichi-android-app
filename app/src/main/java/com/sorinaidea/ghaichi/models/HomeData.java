package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HomeData {
    @SerializedName("featured")
    private
    List<BaseBarbershop> featured;
    @SerializedName("nearest")
    private
    List<BarbershopNear> nearest;
    @SerializedName("newest")
    private
    List<BarbershopNewest> newest;
    @SerializedName("bests")
    private
    List<BarbershopTop> bests;
    @SerializedName("discounts")
    private
    List<BarbershopDiscount> discounts;


    public HomeData() {
        featured = new ArrayList<>();
        nearest = new ArrayList<>();
        newest = new ArrayList<>();
        bests = new ArrayList<>();
        discounts = new ArrayList<>();
    }

    public List<BaseBarbershop> getFeatured() {
        return featured;
    }

    public void setFeatured(List<BaseBarbershop> featured) {
        this.featured = featured;
    }

    public List<BarbershopNear> getNearest() {
        return nearest;
    }

    public void setNearest(List<BarbershopNear> nearest) {
        this.nearest = nearest;
    }

    public List<BarbershopNewest> getNewest() {
        return newest;
    }

    public void setNewest(List<BarbershopNewest> newest) {
        this.newest = newest;
    }

    public List<BarbershopTop> getBests() {
        return bests;
    }

    public void setBests(List<BarbershopTop> bests) {
        this.bests = bests;
    }

    public List<BarbershopDiscount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<BarbershopDiscount> discounts) {
        this.discounts = discounts;
    }
}
