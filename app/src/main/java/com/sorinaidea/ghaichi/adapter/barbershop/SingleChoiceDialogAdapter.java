package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BaseAdapter;
import com.sorinaidea.ghaichi.models.Model;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public abstract class SingleChoiceDialogAdapter<Item extends Model> extends BaseAdapter<SingleChoiceDialogAdapter.ViewHolder, Item> {

    protected abstract void checked(Item price);

    protected abstract void action(SingleChoiceDialogAdapter.ViewHolder viewHolder, Item item);//viewHolder.getItem().setText(price.getDescription());

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatRadioButton item;

        public ViewHolder(View v) {
            super(v);
            item = v.findViewById(R.id.item);
        }

        public AppCompatRadioButton getItem() {
            return item;
        }
    }


    public SingleChoiceDialogAdapter(ArrayList<Item> dataset, Context context) {
        super(dataset, context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_dialog_single_choice, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        applyTextFont(viewHolder.getItem());
        Item item = mDataSet.get(position);
        action(viewHolder, item);
        viewHolder.getItem().setOnClickListener(view -> checked(item));
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
