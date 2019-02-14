package com.sorinaidea.ghaichi.fast;

import java.util.List;

public class Category {

    private int id;
    private String name;
    private List<Service> services;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
