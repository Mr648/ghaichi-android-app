package com.sorinaidea.arayeshgah.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mr-code on 6/20/2018.
 */

public class Service implements Parcelable{

    private String title;
    private boolean isSelected;

    protected Service(Parcel in) {
        title = in.readString();
        isSelected = in.readByte() != 0;
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

    public Service(String title){
        this.setTitle(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }
}
