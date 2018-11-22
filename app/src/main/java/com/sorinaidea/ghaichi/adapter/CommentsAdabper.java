package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.BarbershopCard;
import com.sorinaidea.ghaichi.fast.Comment;
import com.sorinaidea.ghaichi.model.Empty;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.webservice.API;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mr-code on 3/10/2018.
 */

public class CommentsAdabper extends RecyclerView.Adapter<CommentsAdabper.ViewHolder> {
    private static final String TAG = "CommentsAdabper";

    private ArrayList<Comment> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imgProfile;
        private final TextView txtUsername;
        private final TextView txtMessage;
        private final RatingBar ratingBar;
        private final TextView txtDate;


        public ViewHolder(View v) {
            super(v);

            imgProfile = (CircleImageView) v.findViewById(R.id.imgProfile);
            txtUsername = (TextView) v.findViewById(R.id.txtUsername);
            txtMessage = (TextView) v.findViewById(R.id.txtMessage);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        }

        public CircleImageView getImgProfile() {
            return imgProfile;
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }

        public TextView getTxtDate() {
            return txtDate;
        }

        public TextView getTxtMessage() {
            return txtMessage;
        }

        public TextView getTxtUsername() {
            return txtUsername;
        }
    }


    public CommentsAdabper(Context context, List<Comment> comments) {
        mDataSet = new ArrayList<>();
        mDataSet.addAll(comments);
        mContext = context;
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);

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

        viewHolder.getTxtMessage().setText(comment.getText());
        viewHolder.getTxtUsername().setText(comment.getName());
        viewHolder.getTxtDate().setText(comment.getDate());
        viewHolder.getRatingBar().setRating(Integer.parseInt(comment.getRating()));

        try {
            API.getPicasso(mContext)
                    .load(API.BASE_URL
                            + URLDecoder.decode(comment.getImg(), "UTF-8"))
                    .into( viewHolder.getImgProfile());
        } catch (IOException e) {
            e.printStackTrace();
        }



        FontManager.setFont(viewHolder.getTxtUsername(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtMessage(), fontIranSans);
        FontManager.setFont(viewHolder.getTxtDate(), fontIranSans);
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
