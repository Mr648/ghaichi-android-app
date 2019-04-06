package com.sorinaidea.ghaichi.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sorinaidea.ghaichi.App;

public class Pricing extends Model implements Parcelable {


    @SerializedName("views")
    private int views;
    @SerializedName("price")
    private int price;

    @SerializedName("description")
    private String description;

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.views);
        dest.writeInt(this.price);
        dest.writeString(this.description);
    }

    public Pricing() {
    }

    protected Pricing(Parcel in) {
        this.id = in.readInt();
        this.views = in.readInt();
        this.price = in.readInt();
        this.description = in.readString();
    }

    public static final Creator<Pricing> CREATOR = new Creator<Pricing>() {
        @Override
        public Pricing createFromParcel(Parcel source) {
            return new Pricing(source);
        }

        @Override
        public Pricing[] newArray(int size) {
            return new Pricing[size];
        }
    };

    @Override
    public String toString() {
        return String.format(App.LOCALE, "%d %s %d %s.", this.views, "بازدید", this.price, "تومان");
    }
}
