package com.sorinaidea.ghaichi.ui;

import android.widget.TextView;

import com.sorinaidea.ghaichi.model.Service;

import java.util.ArrayList;

/**
 * Created by mr-code on 7/1/2018.
 */


/// TODO Consider Using 58% for Rotation Degree!

public class PriceUpdater {

    private final TextView txtPrice;

    private final PriceData summer = new PriceData();

    protected final ArrayList<Service> selectedServices;

    public PriceUpdater(TextView txtPrice) {
        this.txtPrice = txtPrice;
        this.selectedServices = new ArrayList<>();
    }

    public void add
            (Service service) {

        if (!selectedServices.contains(service)) {
            selectedServices.add(service);
            summer.setSumOfPrices(service.getPrice());
            service.setSelected(true);
        }

        this.txtPrice.setText(String.format("%.2f تومان", summer.getSumOfPrices()));
    }


    public void delete(Service service) {

        if (selectedServices.contains(service)) {
            selectedServices.remove(service);
            summer.setSumOfPrices(-service.getPrice());
        }
        this.txtPrice.setText(String.format("%.2f تومان", summer.getSumOfPrices()));
    }

    private static final class PriceData {
        private float sumOfPrices = 0.0f;

        public void setSumOfPrices(float sumOfPrices) {
            this.sumOfPrices += sumOfPrices;
        }

        public float getSumOfPrices() {
            return sumOfPrices;
        }
    }
}
