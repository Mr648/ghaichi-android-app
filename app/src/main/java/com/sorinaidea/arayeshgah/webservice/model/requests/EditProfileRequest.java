package com.sorinaidea.arayeshgah.webservice.model.requests;

/**
 * Created by mr-code on 6/17/2018.
 */

public class EditProfileRequest extends UserRequest {

    public String name;
    public String family;
    public boolean gender;

    public EditProfileRequest(String accessKey, String name, String family, boolean gender) {
        super(accessKey);
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

    public boolean isGender() {
        return gender;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }
}
