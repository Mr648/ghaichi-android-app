package com.sorinaidea.ghaichi.webservice.image;

import okhttp3.MultipartBody;

public abstract class SingleImageUploader implements ImageUploader {

    private MultipartBody.Part image;


    public SingleImageUploader(MultipartBody.Part image) {
        this.image = image;
    }

    public boolean upload() {
        System.out.println("FUCKING UPLOAD IN SingleImageUploader");

        return this.upload(image);
    }

    protected abstract boolean upload(MultipartBody.Part image);

}
