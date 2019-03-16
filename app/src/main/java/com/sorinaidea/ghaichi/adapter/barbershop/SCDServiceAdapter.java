package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;

import com.sorinaidea.ghaichi.models.Service;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public abstract class SCDServiceAdapter extends SingleChoiceDialogAdapter<Service> {


    public SCDServiceAdapter(ArrayList<Service> dataset, Context context) {
        super(dataset, context);
    }


    @Override
    protected void action(SingleChoiceDialogAdapter.ViewHolder viewHolder, Service service) {
        viewHolder.getItem().setText(service.getName());
    }
}
