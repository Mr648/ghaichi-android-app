package com.sorinaidea.ghaichi.webservice.model.responses;

/**
 * Created by mr-code on 6/17/2018.
 */

public class VerificationResponse extends Response {

    private String accessKey;
    private String userType;
    private boolean isProfileCompleted;


    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getUserType() {
        return userType;
    }

    public boolean isProfileCompleted() {
        return isProfileCompleted;
    }

    public void setProfileCompleted(boolean profileCompleted) {
        isProfileCompleted = profileCompleted;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
