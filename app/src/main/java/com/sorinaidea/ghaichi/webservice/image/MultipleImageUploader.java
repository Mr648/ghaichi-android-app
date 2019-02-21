package com.sorinaidea.ghaichi.webservice.image;

import okhttp3.MultipartBody;

public abstract class MultipleImageUploader implements ImageUploader {
    MultipartBody.Part[] images;

    public MultipleImageUploader(MultipartBody.Part[] images) {
        this.images = images;
    }

    public boolean upload() {
        System.out.println("FUCKING UPLOAD IN SingleImageUploader");
        for (MultipartBody.Part p:images
             ) {
            System.out.println(p.body().toString());
        }
        return this.upload(images);
    }

    public abstract boolean upload(MultipartBody.Part[] images);
}
