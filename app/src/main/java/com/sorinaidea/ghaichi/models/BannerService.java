package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

public class BannerService extends BaseService   {

    @SerializedName("banner")
    protected String banner;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

}
