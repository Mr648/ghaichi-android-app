package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.BaseService;

import java.util.List;


/**
 * Created by mr-code on 5/6/2018.
 */

public class ReserveServiceAdapter extends BaseAdapter<ReserveServiceAdapter.ViewHolder, BaseService> {


    public ReserveServiceAdapter(List<BaseService> services, Context context) {
        super(services, context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;


        public ViewHolder(View view) {
            super(view);
            this.txtName = view.findViewById(R.id.txtName);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_reserve_service, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        BaseService barber = mDataSet.get(position);
        holder.txtName.setText(barber.getName());
        applyTextFont(holder.txtName);
    }

}
