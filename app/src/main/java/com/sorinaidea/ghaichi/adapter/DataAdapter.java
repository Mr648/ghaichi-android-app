package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.Data;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public class DataAdapter extends BaseAdapter<DataAdapter.ViewHolder, Data> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imgEdit;
        AppCompatTextView txtKey;
        AppCompatTextView txtValue;

        public ViewHolder(View v) {
            super(v);
            imgEdit = v.findViewById(R.id.imgEdit);
            txtKey = v.findViewById(R.id.txtKey);
            txtValue = v.findViewById(R.id.txtValue);
        }

    }

    public DataAdapter(List<Data> data, Context context) {
        super(data, context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_data, viewGroup, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        applyTextFont(viewHolder.txtKey, viewHolder.txtValue);
        Data data = mDataSet.get(position);
        viewHolder.txtKey.setText(data.getKey());
        viewHolder.txtValue.setText(data.getValue());
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
