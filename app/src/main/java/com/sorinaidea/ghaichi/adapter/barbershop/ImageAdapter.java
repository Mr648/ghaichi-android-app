package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BaseAdapter;
import com.sorinaidea.ghaichi.models.Image;

import java.util.List;

public abstract class ImageAdapter extends BaseAdapter<ImageAdapter.ViewHolder, Image> {

    protected Handler handler;

    protected abstract void onDeleteClicked(Image image);

    protected ImageAdapter(List<Image> data, Context context) {
        super(data, context);
        handler = new Handler();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgImage;
        TextView txtDateCreated;
        AppCompatImageView imgDelete;

        public ViewHolder(View v) {
            super(v);
            imgImage = v.findViewById(R.id.imgImage);
            imgDelete = v.findViewById(R.id.imgDelete);
            txtDateCreated = v.findViewById(R.id.txtDateCreated);
        }
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_image, viewGroup, false);
        return new ViewHolder(v);

    }
}
