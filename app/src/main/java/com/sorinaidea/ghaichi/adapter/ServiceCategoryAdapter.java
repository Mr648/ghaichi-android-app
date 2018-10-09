package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.model.ServiceCategory;
import com.sorinaidea.ghaichi.ui.AddServiceCategoryActivity;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.ViewHolder> {
    private static final String TAG = "ServiceCategoryAdapter";

    private ArrayList<ServiceCategory> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;
    private AddServiceCategoryActivity mActivity;
    private int lastPosition = -1;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitle;
        private RelativeLayout relBackground,
                relForeground;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            relBackground = (RelativeLayout) v.findViewById(R.id.relBackground);
            relForeground = (RelativeLayout) v.findViewById(R.id.relForeground);
        }


        public RelativeLayout getRelBackground() {
            return relBackground;
        }

        public RelativeLayout getRelForeground() {
            return relForeground;
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }
    }


    public ServiceCategoryAdapter(ArrayList<ServiceCategory> serviceCategories, Context context, AddServiceCategoryActivity activity) {
        mDataSet = serviceCategories;
        mContext = context;
        mActivity = activity;
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.barbershop_service_category_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.getTxtTitle().setText(mDataSet.get(position).getName());

        FontManager.setFont(viewHolder.getTxtTitle(), fontIranSans);
        setAnimation(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void removeItem(int position) {
        mDataSet.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(ServiceCategory item, int position) {
        mDataSet.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
