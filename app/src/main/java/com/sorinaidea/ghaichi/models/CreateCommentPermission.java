package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

public class CreateCommentPermission {

    @SerializedName("granted")
    protected boolean granted;

    public boolean isGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }
}
