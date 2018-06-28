package com.sorinaidea.arayeshgah.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.Reservation;
import com.sorinaidea.arayeshgah.model.Service;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ServiceSelectionAdabper extends RecyclerView.Adapter<ServiceSelectionAdabper.ViewHolder> {
    private static final String TAG = "ServiceSelection";

    private ArrayList<Service> mDataSet;
    private Context mContext;
    private Typeface fontMaterialIcons;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView serviceItem;
        private final TextView txtName;
        private final Switch swSelect;


        public ViewHolder(View v) {
            super(v);

            serviceItem = (CardView) v.findViewById(R.id.serviceItem);
            txtName = (TextView) v.findViewById(R.id.txtName);
            swSelect = (Switch) v.findViewById(R.id.swSelect);

        }

        public CardView getServiceItem() {
            return serviceItem;
        }

        public Switch getSwSelect() {
            return swSelect;
        }

        public TextView getTxtName() {
            return txtName;
        }
    }


    public ServiceSelectionAdabper(ArrayList<Service> services, Context context) {
        mDataSet = services;
        mContext = context;
        fontMaterialIcons = FontManager.getTypeface(mContext, FontManager.MATERIAL_ICONS);
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reservation_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        Service currentService = mDataSet.get(position);

        viewHolder.getTxtName().setText(currentService.getTitle());
        viewHolder.getSwSelect().setChecked(currentService.isSelected());

        View.OnClickListener listener = (v)->{
            currentService.setSelected(viewHolder.getSwSelect().isChecked());
        };
        viewHolder.getSwSelect().setOnClickListener(listener);
        viewHolder.getServiceItem().setOnClickListener(listener);

        FontManager.setFont(viewHolder.getTxtName(), fontMaterialIcons);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
