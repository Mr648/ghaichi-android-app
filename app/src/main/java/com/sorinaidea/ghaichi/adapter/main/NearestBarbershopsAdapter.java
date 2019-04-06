package com.sorinaidea.ghaichi.adapter.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BaseAdapter;
import com.sorinaidea.ghaichi.models.BarbershopNear;
import com.sorinaidea.ghaichi.ui.BarberShopActivity;
import com.sorinaidea.ghaichi.webservice.API;

import java.util.List;


/**
 * Created by mr-code on 5/6/2018.
 */

public class NearestBarbershopsAdapter extends BaseAdapter<NearestBarbershopsAdapter.ViewHolder, BarbershopNear> {


    public NearestBarbershopsAdapter(List<BarbershopNear> barbershopNears, Context context) {
        super(barbershopNears, context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView imgLogo;
        TextView txtName;
        TextView txtDistance;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.imgLogo = view.findViewById(R.id.imgLogo);
            this.txtName = view.findViewById(R.id.txtName);
            this.txtDistance = view.findViewById(R.id.txtDistance);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bshops_nearest, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        BarbershopNear barberShop = mDataSet.get(position);

        API.getPicasso(mContext)
                .load(barberShop.getLogo())
                .placeholder(R.drawable.preview_small)
                .error(R.drawable.preview_small)
                .into(holder.imgLogo);

        holder.txtName.setText(barberShop.getName());
        holder.txtDistance.setText(String.format(App.LOCALE, "%s: %.2f متر", "فاصله", barberShop.getDistance()));
        holder.view.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, BarberShopActivity.class);
            intent.putExtra("BARBERSHOP_ID", Integer.toString(barberShop.getId()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });

        applyTextFont(
                holder.txtName,
                holder.txtDistance
        );
    }
}
