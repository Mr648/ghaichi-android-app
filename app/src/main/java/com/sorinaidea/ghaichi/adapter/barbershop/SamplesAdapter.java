package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.Barber;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class SamplesAdapter extends RecyclerView.Adapter<SamplesAdapter.ViewHolder> {

    private static final String TAG = SamplesAdapter.class.getSimpleName();


    private ArrayList<String> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView imgSample;
        private AppCompatImageView imgSetSelected;
        private AppCompatImageView imgSetVisible;
        private AppCompatImageView imgDelete;

        public ViewHolder(View v) {
            super(v);
            imgSample = v.findViewById(R.id.imgSample);
            imgSetSelected = v.findViewById(R.id.imgSetSelected);
            imgSetVisible = v.findViewById(R.id.imgSetVisible);
            imgDelete = v.findViewById(R.id.imgDelete);
        }

        public AppCompatImageView getImgDelete() {
            return imgDelete;
        }

        public AppCompatImageView getImgSample() {
            return imgSample;
        }

        public AppCompatImageView getImgSetSelected() {
            return imgSetSelected;
        }

        public AppCompatImageView getImgSetVisible() {
            return imgSetVisible;
        }
    }


    public SamplesAdapter(Context context) {
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
                .inflate(R.layout.item_sample, viewGroup, false);
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
