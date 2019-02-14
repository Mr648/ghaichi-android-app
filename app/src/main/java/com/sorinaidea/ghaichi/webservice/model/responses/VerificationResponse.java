package com.sorinaidea.ghaichi.webservice.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mr-code on 6/17/2018.
 */

public class VerificationResponse extends Response {


    @SerializedName("access_key")
    private String accessKey;

    @SerializedName("user_role")
    private String userRole;

    @SerializedName("expiration")
    private String expiration;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getExpiration() {
        return expiration;
    }
}
