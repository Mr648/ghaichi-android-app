package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.Service;
import com.sorinaidea.ghaichi.ui.ImageSliderActivity;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class BarberShopProfileServiceAdapter extends RecyclerView.Adapter<BarberShopProfileServiceAdapter.ViewHolder> {
    private static final String TAG = "BarberShopProfile";

    private ArrayList<Service> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;
    private int barbershopId;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtTitle;
        private final AppCompatImageView imgSelectedImage;


        public ViewHolder(View v) {
            super(v);

            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            imgSelectedImage = (AppCompatImageView) v.findViewById(R.id.imgSelectedImage);
        }


        public AppCompatImageView getImgSelectedImage() {
            return imgSelectedImage;
        }

        public TextView getTxtTitle() {

            return txtTitle;
        }
    }


    public BarberShopProfileServiceAdapter(ArrayList<Service> chatItems, Context context, int barbershopId) {
        mDataSet = chatItems;
        mContext = context;
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);
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
        Log.d(TAG, "Element " + position + " set.");
        Service service = mDataSet.get(position);
        viewHolder.getTxtTitle().setText(service.getName());

        try {
            API.getPicasso(mContext)
                    .load(API.BASE_URL
                            + URLDecoder.decode(service.getPhotos().get(0).getPath(), "UTF-8"))
                    .fit()
                    .into(viewHolder.getImgSelectedImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.getImgSelectedImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ImageSliderActivity.class);
                intent.putExtra(Util.COMMUNICATION_KEYS.SERVICE_ID, Integer.toString(service.getId()));
                intent.putExtra(Util.COMMUNICATION_KEYS.BARBERSHOP_ID, Integer.toString(barbershopId));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        FontManager.setFont(viewHolder.getTxtTitle(), fontIranSans);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
