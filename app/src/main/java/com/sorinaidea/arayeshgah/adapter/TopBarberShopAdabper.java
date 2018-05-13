package com.sorinaidea.arayeshgah.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.BarberShop;
import com.sorinaidea.arayeshgah.model.Transaction;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class TopBarberShopAdabper extends RecyclerView.Adapter<TopBarberShopAdabper.ViewHolder> {
    private static final String TAG = "FAQAdabper";

    private ArrayList<BarberShop> mDataSet;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgLogo;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });

            imgLogo = (ImageView) v.findViewById(R.id.imgLogo);
        }

        public ImageView getImgLogo() {
            return imgLogo;
        }
    }


    public TopBarberShopAdabper(ArrayList<BarberShop> transactions, Context context) {
        mDataSet = transactions;
        mContext = context;

        // setting fonts for icons
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.top_barbershop_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        viewHolder.getImgLogo().setImageResource(mDataSet.get(position).getLogo());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}