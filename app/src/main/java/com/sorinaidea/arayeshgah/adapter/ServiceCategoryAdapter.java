package com.sorinaidea.arayeshgah.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.FAQ;
import com.sorinaidea.arayeshgah.model.ServiceCategory;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.ViewHolder> {
    private static final String TAG = "FAQAdabper";

    private ArrayList<ServiceCategory> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitle;
        private final ImageView imgDelete;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            imgDelete = (ImageView) v.findViewById(R.id.imgDelete);
        }

        public ImageView getImgDelete() {
            return imgDelete;
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }
    }


    public ServiceCategoryAdapter(ArrayList<ServiceCategory> serviceCategories, Context context) {
        mDataSet = serviceCategories;
        mContext = context;
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hairdresser_service_category_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getTxtTitle().setText(mDataSet.get(position).getName());
        viewHolder.getImgDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataSet.remove(position);
                notifyDataSetChanged();
            }
        });

        FontManager.setFont(viewHolder.getTxtTitle(), fontIranSans);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
