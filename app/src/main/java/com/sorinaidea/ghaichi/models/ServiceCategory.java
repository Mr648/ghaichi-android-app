package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceCategory extends Model {

    @SerializedName("name")
    String name;
    @SerializedName("services")
    List<ServiceMoreInfo> services;


    boolean collapsed = false;

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public String getName() {
        return name;
    }

    public List<ServiceMoreInfo> getServices() {
        return services;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServices(List<ServiceMoreInfo> services) {
        this.services = services;
    }
}
