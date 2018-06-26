package com.sorinaidea.arayeshgah.model;

/**
 * Created by mr-code on 3/10/2018.
 */

public class Empty {

    private  int imgIcon;

    private String message;

    public Empty(String message, int imgIcon){
        this.imgIcon = imgIcon;
        this.message = message;
    }

    public int getImgIcon() {
        return imgIcon;
    }

    public String getMessage() {
        return message;
    }

    public void setImgIcon(int imgIcon) {
        this.imgIcon = imgIcon;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

