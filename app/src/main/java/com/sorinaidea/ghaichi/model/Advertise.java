package com.sorinaidea.ghaichi.model;

import com.google.gson.JsonObject;
import com.sorinaidea.ghaichi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mr-code on 7/11/2018.
 */

public class Advertise implements Serializable {

    private int amount;
    private int views;
    private int clicks;
    private float price;
    private boolean finished;

    public int getAmount() {
        return amount;
    }

    public float getPrice() {
        return price;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getClicks() {
        return clicks;
    }

    public int getViews() {
        return views;
    }


    public Advertise(int amount,
                     int views,
                     int clicks,
                     float price,
                     boolean finished) {

        this.amount = amount;
        this.views = views;
        this.clicks = clicks;
        this.price = price;
        this.finished = finished;
    }

    public ArrayList<JsonObject> getInfo() {
        ArrayList<JsonObject> list = new ArrayList<>();
        JsonObject obj = new JsonObject();
        obj.addProperty("title", "تعداد بازدید درخواست شده");
        obj.addProperty("value", String.valueOf(getAmount()));
        obj.addProperty("icon", R.drawable.ic_remove_red_eye_black_18dp);
        list.add(obj);
//
        obj = new JsonObject();
        obj.addProperty("title", "درصد بازدید");
        obj.addProperty("value", String.valueOf((getViews() * 1.0f / getAmount() * 100)) + "%");
        obj.addProperty("icon", R.drawable.ic_remove_red_eye_black_18dp);
        list.add(obj);

        obj = new JsonObject();
        obj.addProperty("title", "تعداد کلیک");
        obj.addProperty("value", String.valueOf(getClicks()));
        obj.addProperty("icon", R.drawable.ic_remove_red_eye_black_18dp);
        list.add(obj);

        obj = new JsonObject();
        obj.addProperty("title", "هزینه پرداخت شده(تومان)");
        obj.addProperty("value", String.valueOf(getPrice()));
        obj.addProperty("icon", R.drawable.ic_remove_red_eye_black_18dp);
        list.add(obj);

        return list;
    }
}
