package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

public class Turn extends Model {

    @SerializedName("time")
    String time;

    @SerializedName("type")
    byte type;

    boolean selected;


    public byte getType() {
        return type;
    }


    public void setType(byte type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }


    public void setTime(String time) {
        this.time = time;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
