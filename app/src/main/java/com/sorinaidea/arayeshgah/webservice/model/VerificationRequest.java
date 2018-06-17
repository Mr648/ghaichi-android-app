package com.sorinaidea.arayeshgah.webservice.model;

/**
 * Created by mr-code on 6/17/2018.
 */

public class VerificationRequest {

    private String phone;
    private String verificationCode;

    public VerificationRequest(String phone, String verificationCode) {
        this.setPhone(phone);
        this.setVerificationCode(verificationCode);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
