package com.sorinaidea.arayeshgah.ui;

import android.os.AsyncTask;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.model.Service;

import java.util.ArrayList;

/**
 * Created by mr-code on 7/1/2018.
 */

public class PriceUpdater {
    private final TextView txtPrice;
    private final PriceData sumOfPrices = new PriceData();
    private final ArrayList<Service> selectedServices;

    public PriceUpdater(TextView txtPrice) {
        this.txtPrice = txtPrice;
        this.selectedServices = new ArrayList<>();
    }

    public void update(final float price) {

        sumOfPrices.setSumOfPrices(price);
        this.txtPrice.setText(String.format("%.2f تومان", sumOfPrices.getSumOfPrices()));
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
