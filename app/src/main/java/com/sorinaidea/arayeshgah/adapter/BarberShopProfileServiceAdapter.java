package com.sorinaidea.arayeshgah.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.ChatItem;
import com.sorinaidea.arayeshgah.model.Service;
import com.sorinaidea.arayeshgah.model.ServiceCategory;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.webservice.API;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public class BarberShopProfileServiceAdapter extends RecyclerView.Adapter<BarberShopProfileServiceAdapter.ViewHolder> {
    private static final String TAG = "BarberShopProfile";

    private ArrayList<Service> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtTitle;
        private final ViewPager pager;


        public ViewHolder(View v) {
            super(v);

            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            pager = (ViewPager) v.findViewById(R.id.pager);
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public ViewPager getPager() {
            return pager;
        }
    }


    private static final List<String> images = Arrays.asList(
            "asdasd",
            "asdasd",
            "asdasd",
            "asdasd"
    );

    private ArrayList<String> imageList = new ArrayList<>();


    public BarberShopProfileServiceAdapter(ArrayList<Service> chatItems, Context context) {
        mDataSet = chatItems;
        mContext = context;
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);
        imageList.addAll(images);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.barbershop_profile_service_item, viewGroup, false);;

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getTxtTitle().setText(mDataSet.get(position).getTitle());
        viewHolder.getPager().setAdapter(new ImageSliderAdapter(mContext, imageList));

        FontManager.setFont(viewHolder.getTxtTitle(), fontIranSans);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
