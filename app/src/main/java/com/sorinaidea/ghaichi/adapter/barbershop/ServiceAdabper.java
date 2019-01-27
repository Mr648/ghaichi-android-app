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
import com.sorinaidea.ghaichi.fast.ServiceInfo;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ServiceAdabper extends RecyclerView.Adapter<ServiceAdabper.ViewHolder> {
    private static final String TAG = "BarberAdabper";


    private ArrayList<ServiceInfo> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTime;
        private TextView txtPrice;
        private TextView txtSamples;
        private TextView txtBarbers;
        private TextView txtName;

        private AppCompatImageView imgAddBarber;
        private AppCompatImageView imgEdit;
        private AppCompatImageView imgAddSample;
        private AppCompatImageView imgDelete;

        private AppCompatImageView imgService;

        public ViewHolder(View v) {
            super(v);
            txtTime = (TextView) v.findViewById(R.id.txtTime);
            txtPrice = (TextView) v.findViewById(R.id.txtPrice);
            txtSamples = (TextView) v.findViewById(R.id.txtSamples);
            txtBarbers = (TextView) v.findViewById(R.id.txtBarbers);
            txtName = (TextView) v.findViewById(R.id.txtName);

            imgAddBarber = (AppCompatImageView) v.findViewById(R.id.imgAddBarber);
            imgEdit = (AppCompatImageView) v.findViewById(R.id.imgEdit);
            imgAddSample = (AppCompatImageView) v.findViewById(R.id.imgAddSample);
            imgDelete = (AppCompatImageView) v.findViewById(R.id.imgDelete);
            imgService = (AppCompatImageView) v.findViewById(R.id.imgService);
        }

        public TextView getTxtName() {
            return txtName;
        }

        public AppCompatImageView getImgAddBarber() {
            return imgAddBarber;
        }

        public AppCompatImageView getImgAddSample() {
            return imgAddSample;
        }

        public TextView getTxtBarbers() {
            return txtBarbers;
        }

        public TextView getTxtPrice() {
            return txtPrice;
        }

        public TextView getTxtSamples() {
            return txtSamples;
        }

        public TextView getTxtTime() {
            return txtTime;
        }

        public AppCompatImageView getImgDelete() {
            return imgDelete;
        }

        public AppCompatImageView getImgEdit() {
            return imgEdit;
        }

        public AppCompatImageView getImgService() {
            return imgService;
        }
    }


    public ServiceAdabper(Context context) {
        mDataSet = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ServiceInfo service = new ServiceInfo(i, "Name", i * 2 + "'", String.valueOf((i + 1) * 1000), String.valueOf(i * 2), String.valueOf(i % 3), new Date().toString());
            mDataSet.add(service);
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
                .inflate(R.layout.item_service, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        ServiceInfo b = mDataSet.get(position);
        viewHolder.getTxtName().setText(b.getName());
        viewHolder.getTxtTime().setText(b.getTime());
        viewHolder.getTxtPrice().setText(b.getPrice());
        viewHolder.getTxtSamples().setText(b.getSamples());
        viewHolder.getTxtBarbers().setText(b.getBarbers());

        FontManager.setFont(viewHolder.getTxtName(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtTime(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtPrice(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtSamples(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtBarbers(), fontIranSans);
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
