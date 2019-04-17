package com.sorinaidea.ghaichi.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BarberShortInfo implements Parcelable {

    protected int id;
    protected String name;
    protected String avatar;


    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BarberShortInfo() {
    }

    private BarberShortInfo(Parcel in) {
        id = in.readInt();
        name = in.readString();
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
        dest.writeString(avatar);
    }

    public static final Creator<BarberShortInfo> CREATOR = new Creator<BarberShortInfo>() {
        public BarberShortInfo createFromParcel(Parcel in) {
            return new BarberShortInfo(in);
        }

        public BarberShortInfo[] newArray(int size) {
            return new BarberShortInfo[size];
        }
    };
}
