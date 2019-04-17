package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.Turn;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public class TurnsAdapter extends BaseAdapter<TurnsAdapter.ViewHolder, Turn> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTime;
        SwitchCompat swSelect;

        public ViewHolder(View v) {
            super(v);
            txtTime = v.findViewById(R.id.txtTime);
            swSelect = v.findViewById(R.id.swSelect);
        }
    }


    public TurnsAdapter(List<Turn> turns, Context context) {
        super(turns, context);
    }


    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).getType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        @LayoutRes int layout = -1;

        switch (viewType) {
            case 1:
                layout = R.layout.item_turn_morning;
                break;
            case 2:
                layout = R.layout.item_turn_evening;
                break;
        }

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(layout, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Turn turn = mDataSet.get(position);

        viewHolder.txtTime.setText(turn.getTime());
        viewHolder.swSelect.setChecked(turn.isSelected());

        viewHolder.swSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (Turn t : mDataSet) t.setSelected(false);
                turn.setSelected(true);
                notifyDataSetChanged();
            } else {
                turn.setSelected(false);
                notifyDataSetChanged();
            }
        });

        applyTextFont(
                viewHolder.txtTime
        );
    }
}
