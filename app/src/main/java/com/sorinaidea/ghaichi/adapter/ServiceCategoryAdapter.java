package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.Category;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.ViewHolder> {

    private ArrayList<Category> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void deleteItem(Category category);

        void updateItem(Category category);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitle;
        private final TextView txtDescription;
        private final AppCompatImageView imgDelete;
        private final AppCompatImageView imgEdit;


        public ViewHolder(View v ) {
            super(v);
            this.txtTitle = v.findViewById(R.id.txtTitle);
            this.txtDescription = v.findViewById(R.id.txtDescription);
            this.imgDelete = v.findViewById(R.id.imgDelete);
            this.imgEdit = v.findViewById(R.id.imgEdit);
        }

        public AppCompatImageView getImgDelete() {
            return imgDelete;
        }

        public AppCompatImageView getImgEdit() {
            return imgEdit;
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public TextView getTxtDescription() {
            return txtDescription;
        }
    }


    public ServiceCategoryAdapter(ArrayList<Category> serviceCategories, Context context, OnItemClickListener editListener) {
        mDataSet = serviceCategories;
        mContext = context;
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);
        this.onItemClickListener = editListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_category, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Category category = mDataSet.get(position);
        viewHolder.getTxtTitle().setText(category.getName());
        viewHolder.getTxtDescription().setText(category.getDescription());

        viewHolder.getImgDelete().setOnClickListener(view -> {
            onItemClickListener.deleteItem(category);
        });

        viewHolder.getImgEdit().setOnClickListener(view -> {
            onItemClickListener.updateItem(category);
        });

        FontManager.setFont(viewHolder.getTxtTitle(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtDescription(), fontIranSans);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


}


//        setAnimation(viewHolder.itemView, position);

//    /**
//     * Here is the key method to apply the animation
//     */
//    private void setAnimation(View viewToAnimate, int position)
//    {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition)
//        {
//            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
//            viewToAnimate.startAnimation(animation);
//            lastPosition = position;
//        }
//    }
//
//    public void removeItem(int position) {
//        mDataSet.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    public void restoreItem(Category item, int position) {
//        mDataSet.add(position, item);
//        notifyItemInserted(position);
//    }