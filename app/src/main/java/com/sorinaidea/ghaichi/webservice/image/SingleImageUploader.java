package com.sorinaidea.ghaichi.webservice.image;

import okhttp3.MultipartBody;


/**
 * Single Image Uploader Class
 * implements {@link ImageUploader}
 * TODO when you extend this class you should implement {@link SingleImageUploader#upload(MultipartBody.Part)} method.
 *
 */
public abstract class SingleImageUploader implements ImageUploader {

    private MultipartBody.Part image;


    public SingleImageUploader(MultipartBody.Part image) {
        this.image = image;
    }

    /**
     *
     * @return upload status eg. true/false
     */
    public boolean upload() {
        return this.upload(image);
    }

    /**
     * Uploads Image to the server
     * TODO implement this method using your rest api client like retrofit, volley, etc. to upload a single file to server.
     *
     * @param image
     * @return upload status eg. true/false
     */
    protected abstract boolean upload(MultipartBody.Part image);

}
