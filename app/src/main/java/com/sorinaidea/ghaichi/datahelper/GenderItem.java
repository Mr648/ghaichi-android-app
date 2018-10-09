package com.sorinaidea.ghaichi.datahelper;

/**
 * Created by mr-code on 6/18/2018.
 */

public class GenderItem {
    private int imgIconResource;
    private String title;



    public GenderItem(int imgIconResource, String title){
        this.setImgIconResource(imgIconResource);
        this.setTitle(title);
    }
    public int getImgIconResource() {
        return imgIconResource;
    }

    public String getTitle() {
        return title;
    }

    public void setImgIconResource(int imgIconResource) {
        this.imgIconResource = imgIconResource;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
