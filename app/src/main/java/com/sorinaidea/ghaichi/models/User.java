package com.sorinaidea.ghaichi.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User extends Person implements Parcelable {

    public User() {
    }

    private User(Parcel in) {
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

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getFullName(){
        return new StringBuilder().append(this.name==null?"":this.name).append(" ").append(this.family==null?"":this.family).toString();
    }
}
