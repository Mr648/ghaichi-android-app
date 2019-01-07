package com.sorinaidea.ghaichi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.model.ReserveTimeViewHolder;
import com.sorinaidea.ghaichi.model.Service;
import com.sorinaidea.ghaichi.model.ServiceList;
import com.sorinaidea.ghaichi.model.ServiceListViewHolder;
import com.sorinaidea.ghaichi.model.ServiceViewHolder;
import com.sorinaidea.ghaichi.ui.PriceUpdater;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ReserveTimeSelectionAdabper extends ExpandableRecyclerViewAdapter<ServiceListViewHolder, ReserveTimeViewHolder> {


    private PriceUpdater updater;

    public ReserveTimeSelectionAdabper(List<? extends ExpandableGroup> groups, PriceUpdater updater) {
        super(groups);
        this.updater = updater;
    }

    @Override
    public ServiceListViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reserve_service_list_item, parent, false);

        return new ServiceListViewHolder(view);
    }

    private static int counter = 0;

    @Override
    public ReserveTimeViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reserve_time_item, parent, false);

        return new ReserveTimeViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ReserveTimeViewHolder holder, int flatPosition,
                                      ExpandableGroup group, int childIndex) {
        final Service service = ((ServiceList) group).getItems().get(childIndex);
        holder.onBind(service);
    }

    @Override
    public void onBindGroupViewHolder(ServiceListViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {

        holder.setServiceListTitle(group);
    }
}

