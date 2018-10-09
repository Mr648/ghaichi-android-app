package com.sorinaidea.ghaichi.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.model.FAQ;
import com.sorinaidea.ghaichi.util.FontManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ImagePreviewAdapter extends RecyclerView.Adapter<ImagePreviewAdapter.ViewHolder> {
    private static final String TAG = "FAQAdabper";

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


    public ImagePreviewAdapter(List<Uri> uris, Context context) {
        mDataSet = uris;
        mContext = context;

        // setting fonts for icons
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.img_item, viewGroup, false);

        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        //getUriRealPath(getApplicationContext(), fileUri);

        // Create content resolver.
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri fileUri = mDataSet.get(position);
        try {
            // Open the file input stream by the uri.
            InputStream inputStream = contentResolver.openInputStream(fileUri);

            // Get the bitmap.
            Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);

            viewHolder.getImgPreview().setImageBitmap(imgBitmap);

            inputStream.close();
        } catch (FileNotFoundException ex) {
            Log.e("Error", ex.getMessage(), ex);
        } catch (IOException ex) {
            Log.e("Error", ex.getMessage(), ex);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
