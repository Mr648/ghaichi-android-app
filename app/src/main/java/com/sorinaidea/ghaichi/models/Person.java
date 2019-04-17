package com.sorinaidea.ghaichi.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Person extends Model   {

    @Nullable
    @SerializedName("name")
    protected String name;

    @Nullable
    @SerializedName("family")
    protected String family;

    @Nullable
    @SerializedName("mobile")
    protected String mobile;

    @Nullable
    @SerializedName("avatar")
    protected String avatar;


    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


}
