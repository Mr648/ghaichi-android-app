package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.Comment;
import com.sorinaidea.ghaichi.webservice.API;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mr-code on 3/10/2018.
 */

public class CommentsAdapter extends BaseAdapter<CommentsAdapter.ViewHolder, Comment> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgProfile;
        TextView txtUsername;
        TextView txtMessage;
        RatingBar ratingBar;
        TextView txtDate;

        public ViewHolder(View v) {
            super(v);
            imgProfile = v.findViewById(R.id.imgProfile);
            txtUsername = v.findViewById(R.id.txtTime);
            txtMessage = v.findViewById(R.id.txtMessage);
            txtDate = v.findViewById(R.id.txtDate);
            ratingBar = v.findViewById(R.id.ratingBar);
        }
    }


    public CommentsAdapter( List<Comment> comments,Context context) {
        super(comments, context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comments_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Comment comment = mDataSet.get(position);

        viewHolder.txtMessage.setText(comment.getText());
        viewHolder.txtUsername.setText(comment.getName());
        viewHolder.txtDate.setText(comment.getDate());
        viewHolder.ratingBar.setRating(Float.parseFloat(comment.getRating()));

        API.getPicasso(mContext)
                .load(comment.getImg())
                .centerCrop()
                .fit()
                .placeholder(R.drawable.preview_small)
                .error(R.drawable.preview_small)
                .into(viewHolder.imgProfile);

        applyTextFont(
                viewHolder.txtUsername,
                viewHolder.txtMessage,
                viewHolder.txtDate
        );
    }
}
