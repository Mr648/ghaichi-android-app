package com.sorinaidea.arayeshgah.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Created by mr-code on 6/20/2018.
 */

public class Service implements Parcelable {

    private String title;
    private boolean isSelected;
    private float price;
    private final boolean hasDiscount;
    private float discountPercent = 1.0f;


    protected Service(Parcel in) {
        title = in.readString();
        isSelected = in.readByte() != 0;
        price = in.readFloat();
        discountPercent = in.readFloat();
        hasDiscount = in.readByte() != 0;
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {

        return hasDiscount ? price : price * discountPercent;
    }

    public boolean hasDiscount() {
        return hasDiscount;
    }


    public Service(String title) {
        this.setTitle(title);
        this.setPrice((float) Math.random() * 15000);
        this.hasDiscount = false;
    }

    /**
     * @param title
     * @param discountPercent should be between 0.0f and 1.0f
     */
    public Service(String title, float discountPercent) {
        this.setTitle(title);
        this.setDiscountPercent(discountPercent);
        this.setPrice((float) Math.random() * 15000);
        this.hasDiscount = true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
        parcel.writeFloat(price);
        parcel.writeFloat(hasDiscount ? discountPercent : 1.0f);
        parcel.writeByte((byte) (hasDiscount ? 1 : 0));
    }


    @Override
    public String toString() {
        return this.getTitle() + "::" + this.getPrice() + "::" + this.isSelected() + "::" + this.hasDiscount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        if (Float.compare(service.price, price) != 0) return false;
        return title.equals(service.title);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        return result;
    }
}
