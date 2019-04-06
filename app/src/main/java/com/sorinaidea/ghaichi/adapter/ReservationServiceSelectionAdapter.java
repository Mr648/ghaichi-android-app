package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.ServiceCategory;
import com.sorinaidea.ghaichi.ui.PriceUpdater;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ReservationServiceSelectionAdapter extends BaseAdapter<ReservationServiceSelectionAdapter.ViewHolder, ServiceCategory> {


    PriceUpdater updater;

    public ReservationServiceSelectionAdapter(List<ServiceCategory> serviceCategories, Context context, PriceUpdater updater) {
        super(serviceCategories, context);
        this.updater = updater;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        AppCompatImageView imgCollapse;
        RecyclerView recServices;

        public ViewHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.txtName);
            imgCollapse = v.findViewById(R.id.imgCollapse);
            recServices = v.findViewById(R.id.recServices);
        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reservation_service_list_item, viewGroup, false);
        return new ViewHolder(v);

    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        applyTextFont(
                viewHolder.txtName
        );

        ServiceCategory category = mDataSet.get(position);
        viewHolder.txtName.setText(category.getName());


        viewHolder.imgCollapse.setOnClickListener(view -> {
            if (category.isCollapsed()) {
                viewHolder.recServices.setVisibility(View.GONE);
                category.setCollapsed(false);
            } else {
                viewHolder.recServices.setVisibility(View.VISIBLE);
                category.setCollapsed(true);
            }
        });

        viewHolder.recServices.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        viewHolder.recServices.setAdapter(new ServiceSelectionAdapter(category.getServices(), mContext, updater));
    }
}
