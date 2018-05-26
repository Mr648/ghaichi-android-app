package com.sorinaidea.arayeshgah.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.Reservation;
import com.sorinaidea.arayeshgah.model.Transaction;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ReservationAdabper extends RecyclerView.Adapter<ReservationAdabper.ViewHolder> {
    private static final String TAG = "FAQAdabper";

    private ArrayList<Reservation> mDataSet;
    private Context mContext;
    private Typeface fontMaterialIcons;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgLogo;
        private final TextView txtTitle;
        private final TextView txtAddress;
        private final TextView txtDate;
        private final TextView txtTime;
        private final TextView txtIconTime;
        private final TextView txtIconDate;

        private final LinearLayout lnrBgr;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });

            imgLogo = (ImageView) v.findViewById(R.id.imgLogo);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
            txtTime = (TextView) v.findViewById(R.id.txtTime);
            txtAddress = (TextView) v.findViewById(R.id.txtAddress);
            txtIconTime = (TextView) v.findViewById(R.id.txtIconTime);
            txtIconDate = (TextView) v.findViewById(R.id.txtIconDate);
            lnrBgr = (LinearLayout) v.findViewById(R.id.lnrBgr);
        }

        public TextView getTxtIconDate() {
            return txtIconDate;
        }

        public TextView getTxtIconTime() {
            return txtIconTime;
        }

        public ImageView getImgLogo() {
            return imgLogo;
        }

        public TextView getTxtAddress() {
            return txtAddress;
        }

        public TextView getTxtTime() {
            return txtTime;
        }

        public TextView getTxtDate() {
            return txtDate;
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public LinearLayout getLnrBgr() {
            return lnrBgr;
        }
    }


    public ReservationAdabper(ArrayList<Reservation> transactions, Context context) {
        mDataSet = transactions;
        mContext = context;
        fontMaterialIcons = FontManager.getTypeface(mContext, FontManager.MATERIAL_ICONS);
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reservation_item , viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");


        viewHolder.getTxtTitle().setText(mDataSet.get(position).getTitle());
        viewHolder.getTxtDate().setText(mDataSet.get(position).getDate());
        viewHolder.getTxtTime().setText(mDataSet.get(position).getTime());
        viewHolder.getTxtAddress().setText(mDataSet.get(position).getAddress());
        viewHolder.getLnrBgr().setBackgroundResource(
                position%3==0?R.drawable.background_red:R.drawable.background_green
        );

        viewHolder.getImgLogo().setImageResource(mDataSet.get(position).getImgLogo());
        FontManager.setFont(viewHolder.getTxtTitle(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtAddress(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtDate(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtTime(), fontIranSans);

        FontManager.setFont(viewHolder.getTxtIconDate(), fontMaterialIcons);
        FontManager.setFont(viewHolder.getTxtIconTime(), fontMaterialIcons);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
