package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.model.Advertise;
import com.sorinaidea.ghaichi.model.Transaction;
import com.sorinaidea.ghaichi.ui.AdvertismentInfoActivity;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.Util;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by mr-code on 3/10/2018.
 */

public class AdvertisementAdabper extends RecyclerView.Adapter<AdvertisementAdabper.ViewHolder> {
    private static final String TAG = "FAQAdabper";

    private ArrayList<Advertise> mDataSet;
    private Context mContext;
    private Typeface fontMaterialIcons;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView imgIsDone;
        private final TextView txtAmount;
        private final TextView txtPrice;
        private final CardView cardAdvertise;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            cardAdvertise = v.findViewById(R.id.cardAdvertise);
            txtAmount = v.findViewById(R.id.txtAmount);
            txtPrice = v.findViewById(R.id.txtPrice);
            imgIsDone = v.findViewById(R.id.imgIsDone);

        }

        public AppCompatImageView getImgIsDone() {
            return imgIsDone;
        }

        public TextView getTxtAmount() {
            return txtAmount;
        }

        public TextView getTxtPrice() {
            return txtPrice;
        }

        public CardView getCardAdvertise() {
            return cardAdvertise;
        }
    }


    public AdvertisementAdabper(ArrayList<Advertise> transactions, Context context) {
        mDataSet = transactions;
        mContext = context;
        fontMaterialIcons = FontManager.getTypeface(mContext, FontManager.MATERIAL_ICONS);
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hairdresser_advertise_item, viewGroup, false);

        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        FontManager.setFont(viewHolder.getTxtAmount(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtPrice(), fontIranSans);

        Advertise currentAd = mDataSet.get(position);
        viewHolder.getTxtAmount().setText(String.format(new Locale("fa"),"تعداد: %d", currentAd.getAmount()));
        viewHolder.getTxtPrice().setText(String.format(new Locale("fa"),"%.2f تومان", currentAd.getPrice()));
        viewHolder.getImgIsDone().setColorFilter(Util.getSuitableColor(currentAd));
        viewHolder.getCardAdvertise().setOnClickListener((view)->{
            Intent intent = new Intent(mContext, AdvertismentInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ADVERTISE", currentAd);
            mContext.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
