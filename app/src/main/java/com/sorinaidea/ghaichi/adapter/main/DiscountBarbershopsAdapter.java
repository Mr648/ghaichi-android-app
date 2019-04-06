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
import com.sorinaidea.ghaichi.models.BarbershopDiscount;
import com.sorinaidea.ghaichi.ui.BarberShopActivity;
import com.sorinaidea.ghaichi.webservice.API;

import java.util.List;


/**
 * Created by mr-code on 5/6/2018.
 */

public class DiscountBarbershopsAdapter extends BaseAdapter<DiscountBarbershopsAdapter.ViewHolder, BarbershopDiscount> {


    public DiscountBarbershopsAdapter(List<BarbershopDiscount> barbershopDiscounts, Context context) {
        super(barbershopDiscounts, context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView imgLogo;
        TextView txtName;
        TextView txtMore;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.imgLogo = view.findViewById(R.id.imgLogo);
            this.txtName = view.findViewById(R.id.txtName);
            this.txtMore = view.findViewById(R.id.txtMore);
        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bshops_discount, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        BarbershopDiscount barberShop = mDataSet.get(position);

        API.getPicasso(mContext)
                .load(barberShop.getLogo())
                .placeholder(R.drawable.preview_small)
                .error(R.drawable.preview_small)
                .into(holder.imgLogo);

        holder.txtName.setText(barberShop.getName());
        holder.txtMore.setText(String.format(App.LOCALE, "%s %d", "تخفیفات ", barberShop.getDiscounts()));
        holder.view.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, BarberShopActivity.class);
            intent.putExtra("BARBERSHOP_ID", Integer.toString(barberShop.getId()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });


        applyTextFont(
                holder.txtMore,
                holder.txtName
        );

    }
}
