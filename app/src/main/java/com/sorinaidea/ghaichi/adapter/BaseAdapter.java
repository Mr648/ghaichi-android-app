package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sorinaidea.ghaichi.util.FontManager;

import java.util.List;

public abstract class BaseAdapter<Some extends RecyclerView.ViewHolder, Data> extends RecyclerView.Adapter<Some> {

    protected static final String TAG = "Adapter";

    protected List<Data> mDataSet;
    protected Context mContext;
    protected Typeface fontText;
    protected Typeface fontTextBold;
    protected Typeface fontIcon;

    public BaseAdapter(List<Data> data, Context context) {
        mDataSet = data;
        mContext = context;
        fontText = FontManager.getTypeface(mContext, FontManager.VAZIR_TEXTS);
        fontTextBold = FontManager.getTypeface(mContext, FontManager.VAZIR_BOLD_TEXTS);
        fontIcon = FontManager.getTypeface(mContext, FontManager.MATERIAL_ICONS);
    }

    protected void applyTextFont(View... views) {
        for (View view : views) {
            FontManager.setFont(view, fontText);
        }
    }

    protected void applyTextBoldFont(View... views) {
        for (View view : views) {
            FontManager.setFont(view, fontTextBold);
        }
    }

    protected void applyIconsFont(View... views) {
        for (View view : views) {
            FontManager.setFont(view, fontIcon);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
