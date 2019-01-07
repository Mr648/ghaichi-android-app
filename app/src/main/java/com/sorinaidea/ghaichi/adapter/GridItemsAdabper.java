package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.BarbershopCard;
import com.sorinaidea.ghaichi.ui.BarberShopActivity;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mr-code on 3/10/2018.
 */

public class GridItemsAdabper extends RecyclerView.Adapter<GridItemsAdabper.ViewHolder> {
    private static final String TAG = "GridItemsAdabper";

    private List<BarbershopCard> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imgLogo;
        private final AppCompatTextView txtName;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            imgLogo = (CircleImageView) v.findViewById(R.id.imgLogo);
            txtName = (AppCompatTextView) v.findViewById(R.id.txtTime);
        }

        public TextView getTxtName() {
            return txtName;
        }

        public CircleImageView getImgLogo() {
            return imgLogo;
        }
    }


    public GridItemsAdabper(List<BarbershopCard> barberShops, Context context) {
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
        BarbershopCard barbershop = mDataSet.get(position);

        viewHolder.getTxtName().setText(barbershop.getName());
        try {
            API.getPicasso(mContext)
                    .load(API.BASE_URL
                            + URLDecoder.decode(barbershop.getIcon(), "UTF-8")).into(viewHolder.imgLogo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.getImgLogo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BarberShopActivity.class);
                intent.putExtra(Util.COMMUNICATION_KEYS.BARBERSHOP_ID, Integer.toString(barbershop.getId()));
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
