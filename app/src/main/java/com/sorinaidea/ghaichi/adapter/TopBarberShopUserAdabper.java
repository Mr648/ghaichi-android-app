package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.model.BarberShop;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class TopBarberShopUserAdabper extends RecyclerView.Adapter<TopBarberShopUserAdabper.ViewHolder> {
    private static final String TAG = "TopBarberShopUserAdabper";

    private ArrayList<Integer> mDataSet;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgLogo;

        public ViewHolder(View v) {
            super(v);


            imgLogo = v.findViewById(R.id.imgLogo);
        }


        public ImageView getImgLogo() {
            return imgLogo;
        }
    }


    public TopBarberShopUserAdabper( Context context,ArrayList<Integer> transactions) {
        mDataSet = transactions;
        mContext = context;

        // setting fonts for icons
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.top_barbershop_item_user_home , viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getImgLogo().setImageResource( R.drawable.barbershop);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
