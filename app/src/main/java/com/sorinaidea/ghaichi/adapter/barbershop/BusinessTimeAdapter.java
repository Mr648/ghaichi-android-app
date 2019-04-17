package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BaseAdapter;
import com.sorinaidea.ghaichi.models.BusinessTime;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public abstract class BusinessTimeAdapter extends BaseAdapter<BusinessTimeAdapter.ViewHolder, BusinessTime> {


    public abstract void selectTime(AppCompatButton btn, int position, BusinessTime.FIELD field);


    public BusinessTimeAdapter(List<BusinessTime> businessTimes, Context context) {
        super(businessTimes, context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        SwitchCompat swSelect;
        TextView txtDay;
        LinearLayout lnrTime;
        TextView txtMo;
        TextView txtMoFrom;
        TextView txtMoUntil;
        TextView txtEv;
        TextView txtEvUntil;
        TextView txtEvFrom;

        AppCompatButton btnMoFrom;
        AppCompatButton btnMoUntil;
        AppCompatButton btnEvFrom;
        AppCompatButton btnEvUntil;

        public ViewHolder(View v) {
            super(v);

            swSelect = v.findViewById(R.id.swSelect);
            txtDay = v.findViewById(R.id.txtDay);

            lnrTime = v.findViewById(R.id.lnrTime);

            txtMo = v.findViewById(R.id.txtMo);
            txtMoFrom = v.findViewById(R.id.txtMoFrom);
            txtMoUntil = v.findViewById(R.id.txtMoUntil);
            txtEv = v.findViewById(R.id.txtEv);
            txtEvUntil = v.findViewById(R.id.txtEvUntil);
            txtEvFrom = v.findViewById(R.id.txtEvFrom);

            btnMoFrom = v.findViewById(R.id.btnMoFrom);
            btnMoUntil = v.findViewById(R.id.btnMoUntil);
            btnEvFrom = v.findViewById(R.id.btnEvFrom);
            btnEvUntil = v.findViewById(R.id.btnEvUntil);

        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_business_time, viewGroup, false);
        return new ViewHolder(v);

    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        applyTextFont(
                viewHolder.txtDay,
                viewHolder.txtMo,
                viewHolder.txtMoFrom,
                viewHolder.txtMoUntil,
                viewHolder.txtEv,
                viewHolder.txtEvUntil,
                viewHolder.txtEvFrom,
                viewHolder.btnMoFrom,
                viewHolder.btnMoUntil,
                viewHolder.btnEvFrom,
                viewHolder.btnEvUntil
        );


        BusinessTime bt = mDataSet.get(position);
        viewHolder.txtDay.setText(bt.getDayName());


        viewHolder.swSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            bt.setOpen(isChecked);
            if (isChecked) {
                viewHolder.lnrTime.setVisibility(View.VISIBLE);
                bt.setHidden(false);
                bt.setOpen(true);
            } else {
                viewHolder.lnrTime.setVisibility(View.GONE);
                bt.setOpen(false);
                bt.setHidden(true);
            }
        });

        viewHolder.swSelect.setChecked(bt.isOpen());

        viewHolder.btnMoFrom.setText(String.format(App.LOCALE, "%s", bt.getMorningOpenTime()));
        viewHolder.btnMoUntil.setText(String.format(App.LOCALE, "%s", bt.getMorningCloseTime()));

        viewHolder.btnEvFrom.setText(String.format(App.LOCALE, "%s", bt.getEveningOpenTime()));
        viewHolder.btnEvUntil.setText(String.format(App.LOCALE, "%s", bt.getEveningCloseTime()));


        viewHolder.btnMoFrom.setOnClickListener(view -> selectTime(viewHolder.btnMoFrom, position, BusinessTime.FIELD.MOT));
        viewHolder.btnMoUntil.setOnClickListener(view -> selectTime(viewHolder.btnMoUntil, position, BusinessTime.FIELD.MCT));
        viewHolder.btnEvFrom.setOnClickListener(view -> selectTime(viewHolder.btnEvFrom, position, BusinessTime.FIELD.EOT));
        viewHolder.btnEvUntil.setOnClickListener(view -> selectTime(viewHolder.btnEvUntil, position, BusinessTime.FIELD.ECT));
    }
}
