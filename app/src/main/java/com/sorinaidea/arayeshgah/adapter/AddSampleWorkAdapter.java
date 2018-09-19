package com.sorinaidea.arayeshgah.adapter;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sorinaidea.arayeshgah.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public class AddSampleWorkAdapter extends RecyclerView.Adapter<AddSampleWorkAdapter.ViewHolder> {
    private static final String TAG = "AddSampleWorkAdapter";

    private List<Uri> mDataSet;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPreview;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            imgPreview = (ImageView) v.findViewById(R.id.imgPreview);
        }

        public ImageView getImgPreview() {
            return imgPreview;
        }
    }

    //
    public AddSampleWorkAdapter(List<Uri> uris, Context context) {
        mDataSet = uris;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sample_work_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        try {
            viewHolder.getImgPreview().setImageBitmap(getOptimizedBitmap(mDataSet.get(position)));
        } catch (FileNotFoundException ex) {
            Log.e("ERROR", ex.getMessage(), ex);
        }
    }

    private Bitmap getOptimizedBitmap(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 140;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(selectedImage), null, o2);
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
