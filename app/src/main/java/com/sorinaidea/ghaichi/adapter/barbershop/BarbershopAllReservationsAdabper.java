package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.model.Empty;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class BarbershopAllReservationsAdabper extends RecyclerView.Adapter<BarbershopAllReservationsAdabper.ViewHolder> {
    private static final String TAG = "BarbershopReservationsAdabper";

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_ARCHIVE = 1;
    private static final int TYPE_REJECTED = 2;

    private ArrayList<Empty> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;
    private int defaultViewType;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }


    public BarbershopAllReservationsAdabper(Context context) {
        mDataSet = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            mDataSet.add(new Empty("هنوز هیچ آیتمی به این لیست افزوده نشده است", R.drawable.ic_empty_list));
        }
        mContext = context;
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.

        @LayoutRes int layout = -1;

        switch (defaultViewType) {
            case TYPE_NORMAL:
                layout = R.layout.item_category_normal;
                break;
            case TYPE_ARCHIVE:
                layout = R.layout.item_category_archive;
                break;
            case TYPE_REJECTED:
                layout = R.layout.item_category_rejected;
                break;
        }
        if (layout != -1) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(layout, viewGroup, false);
            return new ViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
