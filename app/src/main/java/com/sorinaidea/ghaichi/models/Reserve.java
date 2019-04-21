package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;
import com.sorinaidea.ghaichi.App;

import java.util.List;

public class Reserve extends Model {


    @SerializedName("from")
    private String fromTime;
    @SerializedName("to")
    private String toTime;

    @SerializedName("code")
    private String code;

    @SerializedName("umsg")
    private String userMessage;
    @SerializedName("bmsg")
    private String barbershopMessage;
    @SerializedName("date")
    private String date;

    @SerializedName("price")
    private String price;

    @SerializedName("status")
    private String status;

    @SerializedName("alarm")
    private String alarm;

    @SerializedName("services")
    private List<BaseService> services;

    @SerializedName("user")
    private
    User user;


    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BaseService> getServices() {
        return services;
    }

    public void setServices(List<BaseService> services) {
        this.services = services;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTime() {
        return String.format(App.LOCALE, "از %s تا %s", this.fromTime, this.toTime);
    }

    public String getDate() {
        return date;
    }

    public String getBarbershopMessage() {
        return barbershopMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBarbershopMessage(String barbershopMessage) {
        this.barbershopMessage = barbershopMessage;
    }

    public void setUserMessage(String userMessage) {

        this.userMessage = userMessage;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
}
