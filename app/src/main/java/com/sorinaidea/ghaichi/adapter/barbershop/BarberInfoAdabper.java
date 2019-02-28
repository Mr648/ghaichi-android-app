package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.Barber;
import com.sorinaidea.ghaichi.fast.BarberInfo;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class BarberInfoAdabper extends RecyclerView.Adapter<BarberInfoAdabper.ViewHolder> {
    private static final String TAG = "BarberAdabper";


    private ArrayList<BarberInfo> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtMobile;
        public ViewHolder(View v) {
            super(v);

            txtName = v.findViewById(R.id.txtName);
            txtMobile = v.findViewById(R.id.txtMobile);
        }

        public TextView getTxtName() {
            return txtName;
        }

        public TextView getTxtMobile() {
            return txtMobile;
        }
    }


    public BarberInfoAdabper(Context context) {
        mDataSet = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BarberInfo b = new BarberInfo(i, "Name", "Family", "url", "09185397210");
            mDataSet.add(b);
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
                    .inflate(R.layout.item_barber_info, viewGroup, false);
            return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        BarberInfo b = mDataSet.get(position);
        viewHolder.getTxtName().setText(b.getName() + " " + b.getFamily() );
        viewHolder.getTxtMobile().setText(b.getName());
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
