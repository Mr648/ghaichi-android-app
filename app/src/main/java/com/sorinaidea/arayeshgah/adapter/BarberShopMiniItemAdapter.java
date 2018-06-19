package com.sorinaidea.arayeshgah.adapter;

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

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.BarberShop;
import com.sorinaidea.arayeshgah.ui.BarberShopActivity;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;


/**
 * Created by mr-code on 5/6/2018.
 */

public class BarberShopMiniItemAdapter extends RecyclerView.Adapter<BarberShopMiniItemAdapter.ViewHolder> {

    private static Context mContext;
    private ArrayList<BarberShop> mItems;
    Typeface fontIransans;

    public BarberShopMiniItemAdapter(Context mContext,
                                     ArrayList<BarberShop> mItems) {

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


        final BarberShop barberShop = mItems.get(position);
        holder.getImgProduct().setImageResource(R.drawable.preview_small);
        holder.getTxtProductName().setText(barberShop.getTitle());
        holder.getProductItem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BarberShopActivity.class);
                intent.putExtra("PRODUCT_ID", barberShop.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
