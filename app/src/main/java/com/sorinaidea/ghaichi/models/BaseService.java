package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

public class BaseService extends Model   {


    @SerializedName("name")
    protected String name;

    @SerializedName("banner")
    protected String banner;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

}
