package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;

import com.sorinaidea.ghaichi.models.Category;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public abstract class SCDCategoryAdapter extends SingleChoiceDialogAdapter<Category> {


    public SCDCategoryAdapter(ArrayList<Category> dataset, Context context) {
        super(dataset, context);
    }


    @Override
    protected void action(SingleChoiceDialogAdapter.ViewHolder viewHolder, Category pricing) {
        viewHolder.getItem().setText(pricing.getDescription());
    }
}
