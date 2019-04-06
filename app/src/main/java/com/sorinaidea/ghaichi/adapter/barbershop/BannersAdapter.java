package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.webservice.API;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public abstract class BannersAdapter extends ImageAdapter {

    public BannersAdapter(List<Image> images, Context context) {
        super(images, context);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image image = mDataSet.get(position);
        handler.postDelayed(
                () -> API.getPicasso(mContext).load(image.getPath())
                        .fit()
                        .centerCrop()
                        .error(R.drawable.background_green)
                        .into(holder.imgImage)
                , 1000);
        holder.txtDateCreated.setText(image.getDate());
        holder.imgDelete.setOnClickListener(v -> onDeleteClicked(image));
    }
}
