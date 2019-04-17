package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.ServiceTurn;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ReservationTurnSelectionAdapter extends BaseAdapter<ReservationTurnSelectionAdapter.ViewHolder, ServiceTurn> {

    private final int SPAN_COUNT = 2;

    public ReservationTurnSelectionAdapter(List<ServiceTurn> serviceCategories, Context context) {
        super(serviceCategories, context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        AppCompatImageView imgCollapse;
        RecyclerView recTurns;

        public ViewHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.txtName);
            imgCollapse = v.findViewById(R.id.imgCollapse);
            recTurns = v.findViewById(R.id.recTurns);
        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reservation_turn_list_item, viewGroup, false);
        return new ViewHolder(v);

    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        applyTextFont(
                viewHolder.txtName
        );

        ServiceTurn serviceTurn = mDataSet.get(position);
        viewHolder.txtName.setText(serviceTurn.getName());

        viewHolder.imgCollapse.setOnClickListener(view -> {
            if (serviceTurn.isCollapsed()) {
                viewHolder.recTurns.setVisibility(View.GONE);
                serviceTurn.setCollapsed(false);
            } else {
                viewHolder.recTurns.setVisibility(View.VISIBLE);
                serviceTurn.setCollapsed(true);
            }
        });

        viewHolder.recTurns.setLayoutManager(new GridLayoutManager(mContext, SPAN_COUNT));
        viewHolder.recTurns.addItemDecoration(new ItemOffsetDecoration(mContext, R.dimen._4dp));

        viewHolder.recTurns.setAdapter(new TurnsAdapter(serviceTurn.getAllTurns(), mContext));
    }
}
