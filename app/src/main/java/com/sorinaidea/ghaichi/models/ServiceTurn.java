package com.sorinaidea.ghaichi.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ServiceTurn extends Model {


    @SerializedName("name")
    private String name;

    @SerializedName("morning")
    private List<Turn> morningTimes;

    @SerializedName("evening")
    private List<Turn> eveningTimes;

    boolean collapsed = false;

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public List<Turn> getEveningTimes() {
        return eveningTimes;
    }

    public List<Turn> getMorningTimes() {
        return morningTimes;
    }

    public List<Turn> getAllTurns() {
        List<Turn> all = new ArrayList<>();
        all.addAll(morningTimes);
        all.addAll(eveningTimes);
        return all;
    }
}
