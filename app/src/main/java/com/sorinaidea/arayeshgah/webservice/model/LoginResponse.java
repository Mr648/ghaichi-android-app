package com.sorinaidea.arayeshgah.webservice.model;

/**
 * Created by mr-code on 6/17/2018.
 */

public class LoginResponse {
    private boolean error;
    private String message;

    private boolean isAuthResponse;
    private String authKey;


    public boolean hasError() {
        return isError();
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return hasError() + "\t::" + getMessage();
    }

    public boolean isError() {
        return error;
    }

    public boolean isAuthResponse() {
        return isAuthResponse;
    }

    public void setAuthResponse(boolean authResponse) {
        isAuthResponse = authResponse;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
