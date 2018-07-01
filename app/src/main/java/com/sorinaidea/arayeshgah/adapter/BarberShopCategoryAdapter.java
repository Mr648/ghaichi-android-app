package com.sorinaidea.arayeshgah.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.BarberShop;
import com.sorinaidea.arayeshgah.ui.BarberShopActivity;
import com.sorinaidea.arayeshgah.ui.BarberShopGridActivity;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by mr-code on 5/6/2018.
 */

public class BarberShopCategoryAdapter extends RecyclerView.Adapter<BarberShopCategoryAdapter.ViewHolder> {

    private  Context mContext;
    private ArrayList<String> mItems;
    Typeface fontIransans;

    public BarberShopCategoryAdapter(Context mContext,
                                     ArrayList<String> mItems) {

        this.mItems = mItems;
        this.mContext = mContext;
        fontIransans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final LinearLayout btnMore;
        private final TextView txtBarberShopCategory;
        private final TextView txtAll;
        private final RecyclerView recBarberShop;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            btnMore = (LinearLayout) view.findViewById(R.id.btnMore);
            txtBarberShopCategory = (TextView) view.findViewById(R.id.txtBarberShopCategory);
            txtAll = (TextView) view.findViewById(R.id.txtAll);
            recBarberShop = (RecyclerView) view.findViewById(R.id.recBarberShop);
        }

        public TextView getTxtAll() {
            return txtAll;
        }

        public View getView() {
            return view;
        }

        public LinearLayout getBtnMore() {
            return btnMore;
        }

        public RecyclerView getRecBarberShop() {
            return recBarberShop;
        }

        public TextView getTxtBarberShopCategory() {
            return txtBarberShopCategory;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.barbershop_category_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//
//       FontManager.setFont(holder.getTxtBuy(),fontMaterialIcons);
//       FontManager.setFont(holder.getTxtFavorite(),fontMaterialIcons);
//        FontManager.setFont(holder.getTxtShare(),fontMaterialIcons);
//        FontManager.setFont(holder.getTxtLike(),fontMaterialIcons);


        holder.getTxtBarberShopCategory().setText(mItems.get(position));


        holder.getRecBarberShop().setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        BarberShopMiniItemAdapter adapter = new BarberShopMiniItemAdapter(mContext, initBarberShops());
        holder.getRecBarberShop().setAdapter(adapter);


        holder.getBtnMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BarberShopGridActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

        FontManager.setFont(holder.getTxtBarberShopCategory(), fontIransans);
        FontManager.setFont(holder.getTxtAll(), fontIransans);


    }

    private ArrayList<BarberShop> initBarberShops() {
        ArrayList<BarberShop> list = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            list.add(new BarberShop(R.drawable.barbershop, "آرایشگاه #" + i));
        }
        return list;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
