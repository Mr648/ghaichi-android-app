package com.sorinaidea.ghaichi.webservice.model.requests;

/**
 * Created by mr-code on 6/17/2018.
 */

public class VerificationRequest {

    private String mobile;
    private String code;

    public VerificationRequest(String phone, String verificationCode) {
        this.setMobile(phone);
        this.setCode(verificationCode);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
