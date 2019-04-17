package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

public class BaseService extends Model   {


    @SerializedName("name")
    protected String name;

   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
