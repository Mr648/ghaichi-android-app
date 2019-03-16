package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;

import com.sorinaidea.ghaichi.models.Pricing;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public abstract class SCDPricesAdapter extends SingleChoiceDialogAdapter<Pricing> {


    public SCDPricesAdapter(ArrayList<Pricing> dataset, Context context) {
        super(dataset, context);
    }


    @Override
    protected void action(SingleChoiceDialogAdapter.ViewHolder viewHolder, Pricing pricing) {
        viewHolder.getItem().setText(pricing.getDescription());
    }
}
