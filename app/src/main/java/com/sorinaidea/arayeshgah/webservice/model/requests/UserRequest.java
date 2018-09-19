package com.sorinaidea.arayeshgah.webservice.model.requests;

public class UserRequest {

    public UserRequest(String accessKey) {
        this.accessKey = accessKey;
    }

    public String accessKey;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

}
