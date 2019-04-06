package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

public class BaseBarbershop extends Model {
    @SerializedName("name")
    protected String name;
    @SerializedName("logo")
    protected String logo;

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
