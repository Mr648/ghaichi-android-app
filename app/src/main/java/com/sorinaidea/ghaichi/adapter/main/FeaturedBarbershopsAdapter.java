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
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.BarbershopCard;
import com.sorinaidea.ghaichi.ui.BarberShopActivity;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.webservice.API;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by mr-code on 5/6/2018.
 */

public class FeaturedBarbershopsAdapter extends RecyclerView.Adapter<FeaturedBarbershopsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<BarbershopCard> mItems;
    Typeface fontIransans;

    public FeaturedBarbershopsAdapter(Context mContext,
                                      ArrayList<BarbershopCard> mItems) {

        this.mItems = mItems;
        this.mContext = mContext;
        fontIransans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final CircleImageView imgLogo;
        private final TextView txtName;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imgLogo = (CircleImageView) view.findViewById(R.id.imgLogo);
            txtName = (TextView) view.findViewById(R.id.txtName);
        }



        public View getView() {
            return view;
        }

        public TextView getTxtName() {
            return txtName;
        }

        public CircleImageView getImgLogo() {
            return imgLogo;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bshops_featured, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//
//       FontManager.setFont(holder.getTxtBuy(),fontMaterialIcons);
//       FontManager.setFont(holder.getTxtFavorite(),fontMaterialIcons);
//        FontManager.setFont(holder.getTxtShare(),fontMaterialIcons);
//        FontManager.setFont(holder.getTxtLike(),fontMaterialIcons);
//        FontManager.setFont(holder.getTxtDislike(),fontMaterialIcons);


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

    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
