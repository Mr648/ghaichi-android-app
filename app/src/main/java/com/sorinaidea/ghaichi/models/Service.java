package com.sorinaidea.ghaichi.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Service extends Model implements Parcelable {


    @SerializedName("category")
    private Category category;


    @SerializedName("create_date")
    private String createdAt;


    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("discount")
    private String discount;


    @SerializedName("price")
    private String price;


    @SerializedName("time")
    private String time;


    @SerializedName("banner")
    private String banner;


    @SerializedName("barbers")
    private List<Barber> barbers;

    @SerializedName("images")
    private List<Image> images;


    public String getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getPrice() {
        return price;
    }

    public String getBanner() {
        return banner;
    }

    public Category getCategory() {
        return category;
    }

    public List<Barber> getBarbers() {
        return barbers;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getDescription() {
        return description;
    }


    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDiscount() {
        return discount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setBarbers(List<Barber> barbers) {
        this.barbers = barbers;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Service() {
    }

    private Service(Parcel in) {
        barbers = new ArrayList<>();
        images = new ArrayList<>();

        try {
            id = in.readInt();
        } catch (NullPointerException ignored) {
        }
        try {
            name = in.readString();
        } catch (NullPointerException ignored) {
        }
        try {
            time = in.readString();
        } catch (NullPointerException ignored) {
        }
        try {
            price = in.readString();
        } catch (NullPointerException ignored) {
        }
        try {
            banner = in.readString();
        } catch (NullPointerException ignored) {
        }
        try {
            category = in.readParcelable(Category.class.getClassLoader());
        } catch (NullPointerException ignored) {
        }
        try {
            in.readTypedList(barbers, Barber.CREATOR);
            in.readTypedList(images, Image.CREATOR);
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    public int describeContents() {
        // DON'T Modify this value.
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(price);
        dest.writeString(banner);
        dest.writeParcelable(category, flags);
        dest.writeTypedList(barbers);
        dest.writeTypedList(images);
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        public Service[] newArray(int size) {
            return new Service[size];
        }
    };
}
