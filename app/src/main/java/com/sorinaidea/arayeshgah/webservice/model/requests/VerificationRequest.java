package com.sorinaidea.arayeshgah.webservice.model.requests;

/**
 * Created by mr-code on 6/17/2018.
 */

public class VerificationRequest {

    private String phone;
    private String code;
    private String userRole;

    public VerificationRequest(String phone, String verificationCode, String userRole) {
        this.setPhone(phone);
        this.setCode(verificationCode);
        this.setUserRole(userRole);
    }


    public void setUserRole(String user_role) {
        this.userRole = user_role;
    }

    public String getUserRole() {
        return userRole;
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
