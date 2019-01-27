package com.sorinaidea.ghaichi.fast;

public class ServiceInfo {

    private int id;
    private String name;
    private String time;
    private String price;
    private String samples;
    private String barbers;
    private String createdAt;


    public ServiceInfo(
            int id,
            String name,
            String time,
            String price,
            String samples,
            String barbers,
            String createdAt
    ) {
        this.setId(id);
        this.setName(name);
        this.setTime(time);
        this.setPrice(price);
        this.setSamples(samples);
        this.setBarbers(barbers);
        this.setCreatedAt(createdAt);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSamples() {
        return samples;
    }

    public void setSamples(String samples) {
        this.samples = samples;
    }

    public String getBarbers() {
        return barbers;
    }

    public void setBarbers(String barbers) {
        this.barbers = barbers;
    }
}
