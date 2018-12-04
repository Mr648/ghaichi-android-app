package com.sorinaidea.ghaichi.adapter.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.BarbershopCard;
import com.sorinaidea.ghaichi.ui.BarberShopActivity;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.webservice.API;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;


/**
 * Created by mr-code on 5/6/2018.
 */

public class TopBarbershopsAdapter extends RecyclerView.Adapter<TopBarbershopsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<BarbershopCard> mItems;
    Typeface fontIransans;

    public TopBarbershopsAdapter(Context mContext,
                                 ArrayList<BarbershopCard> mItems) {

        this.mItems = mItems;
        this.mContext = mContext;
        fontIransans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final ImageView imgLogo;
        private final TextView txtName;
        private final TextView txtRating;
        private final RatingBar ratingBar;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imgLogo = (ImageView) view.findViewById(R.id.imgLogo);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtRating = (TextView) view.findViewById(R.id.txtRating);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        }



        public View getView() {
            return view;
        }

        public ImageView getImgLogo() {
            return imgLogo;
        }

        public TextView getTxtName() {
            return txtName;
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }

        public TextView getTxtRating() {
            return txtRating;
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bshops_top, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final BarbershopCard barberShop = mItems.get(position);
        try {

            API.getPicasso(mContext)
                    .load(API.BASE_URL
                            + URLDecoder.decode(barberShop.getIcon(), "UTF-8"))
                    .into(holder.imgLogo);

        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.getTxtName().setText(barberShop.getName());

        float rating = (float)(Math.random() * 5.0f);
        holder.getRatingBar().setRating(rating);
        holder.getTxtRating().setText(String.format("%.2f", rating));
        holder.getTxtName().setText(barberShop.getName());

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BarberShopActivity.class);
                intent.putExtra("BARBERSHOP_ID", Integer.toString(barberShop.getId()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        FontManager.setFont(holder.getTxtName(), fontIransans);
        FontManager.setFont(holder.getTxtRating(), fontIransans);

    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
