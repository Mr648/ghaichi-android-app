package com.sorinaidea.ghaichi.webservice.image;


public class UploadTask {


    private ImageUploader uploaderService;

    public boolean isUploadDone() {
        return getUploaderService().isDone();
    }

    public UploadTask() {

    }

    public UploadTask(ImageUploader uploaderService) {
        this.uploaderService = uploaderService;
    }

    public boolean upload() {
        return this.uploaderService != null && this.uploaderService.upload();
    }

    public void setUploaderService(ImageUploader uploaderService) {
        this.uploaderService = uploaderService;
    }

    public ImageUploader getUploaderService() {
        return uploaderService;
    }
}
