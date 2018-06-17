package com.sorinaidea.arayeshgah.webservice.model;

/**
 * Created by mr-code on 6/17/2018.
 */

public class LoginRequest {
    private String phone;

    public LoginRequest(String phone) {
        this.setPhone(phone);
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
