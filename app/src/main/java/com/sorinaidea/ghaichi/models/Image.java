package com.sorinaidea.ghaichi.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image extends Model implements Parcelable  {

    @SerializedName("path")
    private String path;
    @SerializedName("tag")
    private String tag;


    public String getPath() {
        return path;
    }

    public String getTag() {
        return tag;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    private Image(Parcel in){
        id = in.readInt();
        path= in.readString();
        tag= in.readString();
    }

    @Override
    public int describeContents() {
        // DON'T Modify this value.
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(path);
        dest.writeString(tag);
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
