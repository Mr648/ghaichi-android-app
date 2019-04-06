package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

public class ServiceMoreInfo extends ServiceShortInfo {

    @SerializedName("price")
    float price;

    @SerializedName("time")
    int time;


    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public float getPrice() {
        return price;
    }

    public int getTime() {
        return time;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setTime(int time) {
        this.time = time;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceMoreInfo service = (ServiceMoreInfo) o;
        if (Float.compare(service.price, price) != 0) return false;
        return name.equals(service.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        return result;
    }
}
