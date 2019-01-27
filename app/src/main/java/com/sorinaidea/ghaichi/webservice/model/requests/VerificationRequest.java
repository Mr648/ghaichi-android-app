package com.sorinaidea.ghaichi.webservice.model.requests;

/**
 * Created by mr-code on 6/17/2018.
 */

public class VerificationRequest {

    private String phone;
    private String code;

    public VerificationRequest(String phone, String verificationCode) {
        this.setPhone(phone);
        this.setCode(verificationCode);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
