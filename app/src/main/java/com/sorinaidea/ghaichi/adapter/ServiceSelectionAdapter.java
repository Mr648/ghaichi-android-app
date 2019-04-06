package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.ServiceMoreInfo;
import com.sorinaidea.ghaichi.ui.PriceUpdater;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ServiceSelectionAdapter extends BaseAdapter<ServiceSelectionAdapter.ViewHolder, ServiceMoreInfo> {

    PriceUpdater updater;

    public ServiceSelectionAdapter(List<ServiceMoreInfo> services, Context context, PriceUpdater updater) {
        super(services, context);
        this.updater = updater;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtPrice;
        TextView txtPercent;
        AppCompatImageView imgIcon;
        SwitchCompat swSelect;
        RelativeLayout relativeDiscount;
        Typeface fontIransans;
        boolean isSelected = false;
        RotateAnimation rotateAnimation;

        public ViewHolder(View v) {
            super(v);

            swSelect = v.findViewById(R.id.swSelect);
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatMode(Animation.REVERSE);
            rotateAnimation.setRepeatCount(RotateAnimation.INFINITE);
            rotateAnimation.setDuration(2500);
            txtName = itemView.findViewById(R.id.txtTime);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtPercent = itemView.findViewById(R.id.txtPercent);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            swSelect = itemView.findViewById(R.id.swSelect);
            relativeDiscount = itemView.findViewById(R.id.relativeDiscount);
        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reserve_service_item, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        ServiceMoreInfo service = mDataSet.get(position);
        holder.txtName.setText(service.getName());

//        if (service.hasDiscount()) {
//            holder.relativeDiscount.setVisibility(View.VISIBLE);
//            holder.imgIcon.startAnimation(rotateAnimation);
//            holder.txtPercent.setText(String.format(App.LOCALE, "%.1f%%", service.getDiscountPercent() * 100.0f));
//        } else {
        holder.relativeDiscount.setVisibility(View.GONE);
//        }


        holder.txtPrice.setText(String.format(App.LOCALE, "%s تومان", service.getPrice()));
        holder.swSelect.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                updater.add(service);
            } else {
                if (service.isSelected()) {
                    updater.delete(service);
                }
                service.setSelected(false);
            }
        });
        holder.swSelect.setChecked(service.isSelected());

        applyTextFont(
                holder.txtName,
                holder.txtPercent,
                holder.txtPrice
        );
    }

}

