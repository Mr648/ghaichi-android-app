package com.sorinaidea.ghaichi.webservice.model.responses;

/**
 * Created by mr-code on 6/17/2018.
 */

public class LoginResponse extends  Response{


    protected String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

}
