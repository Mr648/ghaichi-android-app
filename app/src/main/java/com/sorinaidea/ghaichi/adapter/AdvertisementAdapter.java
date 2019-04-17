package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.BaseAdvertise;
import com.sorinaidea.ghaichi.ui.AdvertisementInfoActivity;
import com.sorinaidea.ghaichi.util.Util;

import java.util.List;


/**
 * Created by mr-code on 3/10/2018.
 */

public class AdvertisementAdapter extends BaseAdapter<AdvertisementAdapter.ViewHolder, BaseAdvertise> {


    public AdvertisementAdapter(List<BaseAdvertise> baseAdvertises, Context context) {
        super(baseAdvertises, context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtType;
        TextView txtStatus;
        TextView txtAmount;
        TextView txtPrice;
        AppCompatImageView imgProgress;
        CardView card;

        public ViewHolder(View v) {
            super(v);
            txtType = v.findViewById(R.id.txtType);
            txtStatus = v.findViewById(R.id.txtStatus);
            txtAmount = v.findViewById(R.id.txtAmount);
            txtPrice = v.findViewById(R.id.txtPrice);
            imgProgress = v.findViewById(R.id.imgProgress);
            card = v.findViewById(R.id.card);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_advertise, viewGroup, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        BaseAdvertise ad = mDataSet.get(position);

        viewHolder.txtType.setText(ad.getType() == 1 ? "تبلیغ بنری" : "تبلیغ ویژه");
        viewHolder.txtStatus.setText(ad.getStatus());
        viewHolder.txtAmount.setText(String.format(App.LOCALE, "بازدید مورد نظر: %d", ad.getViews()));
        viewHolder.txtPrice.setText(String.format(App.LOCALE, "%.2f تومان", ad.getPrice()));

        viewHolder.imgProgress.setColorFilter(Util.getSuitableColor(ad));
        viewHolder.card.setOnClickListener((view) -> {
            Intent intent = new Intent(mContext, AdvertisementInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Util.COMMUNICATION_KEYS.ADVERTISE_ID, String.valueOf(ad.getId()));
            mContext.startActivity(intent);
        });

        applyTextFont(
                viewHolder.txtType,
                viewHolder.txtStatus,
                viewHolder.txtAmount,
                viewHolder.txtPrice
        );

    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
