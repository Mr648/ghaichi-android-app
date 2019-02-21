package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadImageResponse extends  Response{

    @SerializedName("images")
    List<Image> images;

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Image> getImages() {
        return images;
    }
}
