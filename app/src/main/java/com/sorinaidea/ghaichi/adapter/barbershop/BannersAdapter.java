package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class BannersAdapter extends RecyclerView.Adapter<BannersAdapter.ViewHolder> {

    private static final String TAG = BannersAdapter.class.getSimpleName();


    private ArrayList<String> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView imgBanner;
        private AppCompatImageView imgSetSelected;
        private AppCompatImageView imgSetVisible;
        private AppCompatImageView imgDelete;

        public ViewHolder(View v) {
            super(v);
            imgBanner = (AppCompatImageView) v.findViewById(R.id.imgBanner);
            imgSetSelected = (AppCompatImageView) v.findViewById(R.id.imgSetSelected);
            imgSetVisible = (AppCompatImageView) v.findViewById(R.id.imgSetVisible);
            imgDelete = (AppCompatImageView) v.findViewById(R.id.imgDelete);
        }

        public AppCompatImageView getImgDelete() {
            return imgDelete;
        }

        public AppCompatImageView getImgBanner() {
            return imgBanner;
        }

        public AppCompatImageView getImgSetSelected() {
            return imgSetSelected;
        }

        public AppCompatImageView getImgSetVisible() {
            return imgSetVisible;
        }
    }


    public BannersAdapter(Context context) {
        mDataSet = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDataSet.add("");
        }
        mContext = context;
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_banner, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
