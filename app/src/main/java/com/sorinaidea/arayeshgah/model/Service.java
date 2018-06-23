package com.sorinaidea.arayeshgah.model;

/**
 * Created by mr-code on 6/20/2018.
 */

public class Service {

    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Service(String title){
        this.setTitle(title);
    }
}
