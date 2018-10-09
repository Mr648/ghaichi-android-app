package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.model.ChatItem;
import com.sorinaidea.ghaichi.model.Reservation;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.webservice.API;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mr-code on 3/10/2018.
 */

public class SupportChatAdapter extends RecyclerView.Adapter<SupportChatAdapter.ViewHolder> {
    private static final String TAG = "SupportChatAdapter";

    private ArrayList<ChatItem> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imgProfile;
        private final TextView txtMessage;
        private final TextView txtDate;


        public ViewHolder(View v) {
            super(v);

            imgProfile = (CircleImageView) v.findViewById(R.id.imgIcon);
            txtMessage = (TextView) v.findViewById(R.id.txtMessage);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
        }

        public ImageView getImgProfile() {
            return imgProfile;
        }

        public TextView getTxtDate() {
            return txtDate;
        }

        public TextView getTxtMessage() {
            return txtMessage;
        }

    }


    public SupportChatAdapter(ArrayList<ChatItem> chatItems, Context context) {
        mDataSet = chatItems;
        mContext = context;
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);
    }


    private static final int VIEW_TYPE_USER = 1;
    private static final int VIEW_TYPE_SUPPORT = 2;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = null;
        switch (viewType) {
            case VIEW_TYPE_USER:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.chat_item_user, viewGroup, false);
                break;

            case VIEW_TYPE_SUPPORT:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.chat_item_support, viewGroup, false);
                break;
        }



        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return mDataSet.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getTxtDate().setText(mDataSet.get(position).getDate());
        viewHolder.getTxtMessage().setText(mDataSet.get(position).getMessage());


        switch (mDataSet.get(position).getViewType()) {
            case VIEW_TYPE_USER:
                viewHolder.getImgProfile().setImageResource(R.drawable.ic_account_circle_white_24dp);
//                try {
//                    Picasso.with(mContext).load(API.BASE_URL
//                            + URLDecoder.decode(mDataSet.get(position).getImageUrl(), "UTF-8")).into(viewHolder.getImgProfile());
//                } catch (UnsupportedEncodingException e) {
//                }
                break;

            case VIEW_TYPE_SUPPORT:
                viewHolder.getImgProfile().setImageResource(R.drawable.ic_headset_mic_white_24dp);
                break;
        }

        FontManager.setFont(viewHolder.getTxtDate(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtMessage(), fontIranSans);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
