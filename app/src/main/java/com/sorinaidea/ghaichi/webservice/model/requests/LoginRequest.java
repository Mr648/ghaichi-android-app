package com.sorinaidea.ghaichi.webservice.model.requests;

/**
 * Created by mr-code on 6/17/2018.
 */

public class LoginRequest {

    private String mobile;

    public LoginRequest(String mobile) {
        this.setMobile(mobile);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
