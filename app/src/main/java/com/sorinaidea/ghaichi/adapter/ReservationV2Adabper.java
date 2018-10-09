package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.model.Reservation;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ReservationV2Adabper extends RecyclerView.Adapter<ReservationV2Adabper.ViewHolder> {
    private static final String TAG = "ReservationV2Adabper";

    private ArrayList<Reservation> mDataSet;
    private Context mContext;
    private Typeface fontMaterialIcons;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imgIcon;
        private final TextView txtUsername,
                txtDate,
                txtTime,
                txtServices;

        private final CardView cardView;

        public RelativeLayout relBackground,
                relForeground;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });

            imgIcon = (CircleImageView) v.findViewById(R.id.imgIcon);
            txtUsername = (TextView) v.findViewById(R.id.txtUsername);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
            txtTime = (TextView) v.findViewById(R.id.txtTime);
            txtServices = (TextView) v.findViewById(R.id.txtServices);
            cardView = (CardView) v.findViewById(R.id.cardView);
            relBackground = (RelativeLayout) v.findViewById(R.id.relBackground);
            relForeground = (RelativeLayout) v.findViewById(R.id.relForeground);
        }

        public CircleImageView getImgIcon() {
            return imgIcon;
        }

        public TextView getTxtDate() {
            return txtDate;
        }

        public TextView getTxtTime() {
            return txtTime;
        }

        public TextView getTxtServices() {
            return txtServices;
        }

        public TextView getTxtUsername() {
            return txtUsername;
        }

        //        public LinearLayout getLnrBgr() {
//            return lnrBgr;
//        }
    }


    public ReservationV2Adabper(ArrayList<Reservation> transactions, Context context) {
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
                .inflate(R.layout.reservation_item_v2, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");


        viewHolder.getTxtUsername().setText(mDataSet.get(position).getTitle());
        viewHolder.getTxtDate().setText(mDataSet.get(position).getDate());
        viewHolder.getTxtTime().setText(mDataSet.get(position).getTime());
        String text = "مانیکور، ";
        text += "کاشت ناخن" + "، ";
        text += "اپیلاسیون" + "، ";
        text += "رنگ مو" + "، ";
        text += "گریم صورت" + "، ";
        text += "حالت دادن مو" + "، ";
        text += "کوتاه کردن مو" + "، ";
        text += "تزریق بوتاکس و ژل" + "، ";
        text += "پروتز لب" + "، ";
        text += "ماساژ صورت" + "، ";
        text += "پدیکور";

        viewHolder.getTxtServices().setText(text);
//        viewHolder.getLnrBgr().setBackgroundResource(
//                        position%3==0?R.drawable.background_red:R.drawable.background_green
//        );

//        viewHolder.getImgLogo().setImageResource(mDataSet.get(position).getImgLogo());

        FontManager.setFont(viewHolder.getTxtDate(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtServices(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtDate(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtUsername(), fontIranSans);

        setAnimation(viewHolder.itemView, position);

    }

    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
