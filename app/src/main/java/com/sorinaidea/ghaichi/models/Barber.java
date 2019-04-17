package com.sorinaidea.ghaichi.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Barber extends Person implements Parcelable {
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
