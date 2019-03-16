package com.sorinaidea.ghaichi.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Barber extends Model implements Parcelable {

    @Nullable
    @SerializedName("name")
    private String name;

    @Nullable
    @SerializedName("family")
    private String family;

    @Nullable
    @SerializedName("mobile")
    private String mobile;

    @Nullable
    @SerializedName("avatar")
    private String avatar;


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

    public Barber() {
    }

    private Barber(Parcel in) {
        id = in.readInt();
        name = in.readString();
        family = in.readString();
        mobile = in.readString();
        avatar = in.readString();
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
        dest.writeString(family);
        dest.writeString(mobile);
        dest.writeString(avatar);
    }

    public static final Creator<Barber> CREATOR = new Creator<Barber>() {
        public Barber createFromParcel(Parcel in) {
            return new Barber(in);
        }

        public Barber[] newArray(int size) {
            return new Barber[size];
        }
    };
}
