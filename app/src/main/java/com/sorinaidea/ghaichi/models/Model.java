package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("id")
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
