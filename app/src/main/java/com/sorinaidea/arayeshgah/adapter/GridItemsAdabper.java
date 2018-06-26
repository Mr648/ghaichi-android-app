package com.sorinaidea.arayeshgah.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.BarberShop;
import com.sorinaidea.arayeshgah.model.FAQ;
import com.sorinaidea.arayeshgah.ui.BarberShopActivity;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class GridItemsAdabper extends RecyclerView.Adapter<GridItemsAdabper.ViewHolder> {
    private static final String TAG = "GridItemsAdabper";

    private ArrayList<BarberShop> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView imgLogo;
        private final AppCompatTextView txtName;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            imgLogo = (AppCompatImageView) v.findViewById(R.id.imgLogo);
            txtName = (AppCompatTextView) v.findViewById(R.id.txtName);
        }

        public TextView getTxtName() {
            return txtName;
        }

        public AppCompatImageView getImgLogo() {
            return imgLogo;
        }
    }


    public GridItemsAdabper(ArrayList<BarberShop> barberShops, Context context) {
        mDataSet = barberShops;
        mContext = context;
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.all_barbershops_grid_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.getTxtName().setText(mDataSet.get(position).getTitle());
        viewHolder.getImgLogo().setImageResource(mDataSet.get(position).getLogo());
        viewHolder.getImgLogo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BarberShopActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        FontManager.setFont(viewHolder.getTxtName(), fontIranSans);
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
