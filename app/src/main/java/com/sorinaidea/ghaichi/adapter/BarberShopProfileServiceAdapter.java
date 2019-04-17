package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.BannerService;
import com.sorinaidea.ghaichi.ui.ImageSliderActivity;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public class BarberShopProfileServiceAdapter extends BaseAdapter<BarberShopProfileServiceAdapter.ViewHolder, BannerService> {
    private int barbershopId;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        AppCompatImageView imgImage;

        public ViewHolder(View v) {
            super(v);
            txtTitle = v.findViewById(R.id.txtTitle);
            imgImage = v.findViewById(R.id.imgImage);
        }

    }


    public BarberShopProfileServiceAdapter(List<BannerService> services, Context context, int barbershopId) {
        super(services, context);
        this.barbershopId = barbershopId;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.barbershop_profile_service_item, viewGroup, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        BannerService service = mDataSet.get(position);
        viewHolder.txtTitle.setText(service.getName());

        API.getPicasso(mContext)
                .load(service.getBanner())
                .centerCrop()
                .placeholder(R.drawable.preview_small)
                .fit()
                .into(viewHolder.imgImage);

        viewHolder.imgImage.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ImageSliderActivity.class);
            intent.putExtra(Util.COMMUNICATION_KEYS.SERVICE_ID, Integer.toString(service.getId()));
            intent.putExtra(Util.COMMUNICATION_KEYS.BARBERSHOP_ID, Integer.toString(barbershopId));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });

        applyTextFont(viewHolder.txtTitle);
    }

}
