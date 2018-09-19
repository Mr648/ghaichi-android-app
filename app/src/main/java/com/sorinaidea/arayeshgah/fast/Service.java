package com.sorinaidea.arayeshgah.fast;

import java.util.List;

public class Service {

    private String name;
    private String price;
    private String time;
    private List<Photo> photos;

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
