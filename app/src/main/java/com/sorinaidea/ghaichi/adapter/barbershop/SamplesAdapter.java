package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BaseAdapter;
import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.webservice.API;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public abstract class SamplesAdapter extends BaseAdapter<SamplesAdapter.ViewHolder, Image> {

    protected abstract void onDeleteClicked(Image image);

    public SamplesAdapter(List<Image> images, Context context) {
        super(images, context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView imgSample;
        private AppCompatImageView imgSetSelected;
        private AppCompatImageView imgSetVisible;
        private AppCompatImageView imgDelete;

        public ViewHolder(View v) {
            super(v);
            imgSample = v.findViewById(R.id.imgSample);
            imgSetSelected = v.findViewById(R.id.imgSetSelected);
            imgSetVisible = v.findViewById(R.id.imgSetVisible);
            imgDelete = v.findViewById(R.id.imgDelete);
        }

        public AppCompatImageView getImgDelete() {
            return imgDelete;
        }

        public AppCompatImageView getImgSample() {
            return imgSample;
        }

        public AppCompatImageView getImgSetSelected() {
            return imgSetSelected;
        }

        public AppCompatImageView getImgSetVisible() {
            return imgSetVisible;
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_sample, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Image image = mDataSet.get(position);
        API.getPicasso(mContext).load(image.getPath())
                .fit()
                .centerInside()
                .error(R.drawable.background_green)
                .into(viewHolder.getImgSample());
        viewHolder.getImgDelete().setOnClickListener(v -> onDeleteClicked(image));
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
