package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("key_en")
    private String keyEn;
    @SerializedName("key_fa")
    private String keyFa;

    private String value;
    private boolean editable;


    public String getKeyEn() {
        return keyEn;
    }

    public void setKeyEn(String keyEn) {
        this.keyEn = keyEn;
    }

    public String getKeyFa() {
        return keyFa;
    }

    public void setKeyFa(String keyFa) {
        this.keyFa = keyFa;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
