package com.sorinaidea.ghaichi.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BusinessTime implements Serializable {




    @SerializedName("day")
    private String day;

    @SerializedName("open")
    private boolean isOpen;

    @SerializedName("mot")
    private String morningOpenTime;

    @SerializedName("mct")
    private String morningCloseTime;

    @SerializedName("eot")
    private String eveningOpenTime;

    @SerializedName("ect")
    private String eveningCloseTime;

    private boolean hidden = true;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getMorningOpenTime() {
        return morningOpenTime;
    }

    public void setMorningOpenTime(String morningOpenTime) {
        this.morningOpenTime = morningOpenTime;
    }

    public String getMorningCloseTime() {
        return morningCloseTime;
    }

    public void setMorningCloseTime(String morningCloseTime) {
        this.morningCloseTime = morningCloseTime;
    }

    public String getEveningOpenTime() {
        return eveningOpenTime;
    }

    public void setEveningOpenTime(String eveningOpenTime) {
        this.eveningOpenTime = eveningOpenTime;
    }

    public String getEveningCloseTime() {
        return eveningCloseTime;
    }

    public void setEveningCloseTime(String eveningCloseTime) {
        this.eveningCloseTime = eveningCloseTime;
    }

    public String getDayName() {
        return Day.valueOf(this.day.toUpperCase()).getName();
    }

    public static enum FIELD{
        MOT,
        MCT,
        EOT,
        ECT
    }

    private enum Day {
        SAT("شنبه", "sat"),
        SUN("یکشنبه", "sun"),
        MON("دوشنبه", "mon"),
        TUE("سه‌شنبه", "tue"),
        WED("چهارشنبه", "wed"),
        THU("پنجشنبه", "thu"),
        FRI("جمعه", "fri");

        Day(String name, String day) {
            this.name = name;
            this.key = key;
        }

        public String name;
        public String key;

        public void setKey(String key) {
            this.key = key;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

    }

    public JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();
        obj.addProperty("day", day);
        obj.addProperty("open", isOpen);
        obj.addProperty("mot", morningOpenTime);
        obj.addProperty("mct", morningCloseTime);
        obj.addProperty("eot", eveningOpenTime);
        obj.addProperty("ect", eveningCloseTime);
        return obj;
    }
}
