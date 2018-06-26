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
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.BarberShop;
import com.sorinaidea.arayeshgah.model.Empty;
import com.sorinaidea.arayeshgah.ui.BarberShopActivity;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class EmptyAdabper extends RecyclerView.Adapter<EmptyAdabper.ViewHolder> {
    private static final String TAG = "EmptyAdabper";

    private ArrayList<Empty> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView imgIcon;
        private final AppCompatTextView txtMessage;

        public ViewHolder(View v) {
            super(v);

            imgIcon = (AppCompatImageView) v.findViewById(R.id.imgIcon);
            txtMessage = (AppCompatTextView) v.findViewById(R.id.txtMessage);
        }

        public AppCompatImageView getImgIcon() {
            return imgIcon;
        }

        public AppCompatTextView getTxtMessage() {
            return txtMessage;
        }
    }


    public EmptyAdabper( Context context) {
        mDataSet = new ArrayList<>();
        mDataSet.add(new Empty("هنوز هیچ آیتمی به این لیست افزوده نشده است" ,R.drawable.ic_empty_list));
        mContext = context;
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.empty_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.getTxtMessage().setText(mDataSet.get(position).getMessage());
        viewHolder.getImgIcon().setImageResource(mDataSet.get(position).getImgIcon());

        FontManager.setFont(viewHolder.getTxtMessage(), fontIranSans);
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
