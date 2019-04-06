package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.Message;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public class MessageAdapter extends BaseAdapter<MessageAdapter.ViewHolder, Message> {


    public MessageAdapter(List<Message> messages, Context context) {
        super(messages, context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMessage;
        TextView txtDate;

        public ViewHolder(View v) {
            super(v);

            txtMessage = v.findViewById(R.id.txtMessage);
            txtDate = v.findViewById(R.id.txtDate);
        }
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
        return mDataSet.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.txtDate.setText(String.format(App.LOCALE, "%s", mDataSet.get(position).getDate()));
        viewHolder.txtMessage.setText(mDataSet.get(position).getMessage());
        applyTextFont(viewHolder.txtDate, viewHolder.txtMessage);
    }
}
