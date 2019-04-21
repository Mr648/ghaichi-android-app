package com.sorinaidea.ghaichi.webservice.image;

import okhttp3.MultipartBody;

public abstract class MultipleImageUploader implements ImageUploader {

    /**
     * Images tat should be downloaded.
     */
    MultipartBody.Part[] images;

    /**
     * constructor of class.
     * @param images
     */
    public MultipleImageUploader(MultipartBody.Part[] images) {
        /**
         * sets images to {@link #images}
         */
        this.images = images;
    }


    /**
     *
     * @return upload status i.e. true/false
     */
    public boolean upload() {
        return this.upload(images);
    }


    /**
     * Uploads multiple Images to the server
     * TODO implement this method using your rest api client like retrofit, volley, etc. to upload a single file to server.
     *
     * @param images array of image files surrounded with {@link MultipartBody.Part}.
     * @return upload status eg. true/false
     */
    public abstract boolean upload(MultipartBody.Part[] images);
}
