package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.BarbershopCard;
import com.sorinaidea.ghaichi.model.BarberShop;
import com.sorinaidea.ghaichi.ui.BarberShopActivity;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.picasso.CircleTransformation;
import com.sorinaidea.ghaichi.webservice.API;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;


/**
 * Created by mr-code on 5/6/2018.
 */

public class BarberShopMiniItemAdapter extends RecyclerView.Adapter<BarberShopMiniItemAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<BarbershopCard> mItems;
    Typeface fontIransans;

    public BarberShopMiniItemAdapter(Context mContext,
                                     ArrayList<BarbershopCard> mItems) {

        this.mItems = mItems;
        this.mContext = mContext;
        fontIransans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final ImageView imgProduct;
        private final CardView productItem;
        private final TextView txtProductName;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imgProduct = (ImageView) view.findViewById(R.id.imgProduct);
            txtProductName = (TextView) view.findViewById(R.id.txtProductCategory);
            productItem = (CardView) view.findViewById(R.id.productItem);
        }

        public CardView getProductItem() {
            return productItem;
        }

        public View getView() {
            return view;
        }

        public ImageView getImgProduct() {
            return imgProduct;
        }

        public TextView getTxtProductName() {
            return txtProductName;
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.barbershop_item_mini, viewGroup, false);

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

            Picasso.with(mContext)
                    .load(API.BASE_URL
                            + URLDecoder.decode(barberShop.getIcon(), "UTF-8"))
                    .into(holder.imgProduct);

        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.getTxtProductName().setText(barberShop.getName());
        holder.getProductItem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BarberShopActivity.class);
                intent.putExtra("BARBERSHOP_ID", Integer.toString(barberShop.getId()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        FontManager.setFont(holder.getTxtProductName(), fontIransans);

    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
