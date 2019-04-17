package com.sorinaidea.ghaichi.webservice.model.requests;

/**
 * Created by mr-code on 6/17/2018.
 */

public class EditProfileRequest extends UserRequest {

    public String name;
    public String family;
    public String gender;

    public EditProfileRequest(String name, String family, String gender) {
        super(null);
        this.name = name;
        this.family = family;
        this.gender = gender;
    }

    public String getFamily() {
        return family;
    }

    public String getName() {
        return name;
    }

    public String isGender() {
        return gender;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }
}
