package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.webservice.API;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public abstract class SamplesAdapter extends ImageAdapter {


    public SamplesAdapter(List<Image> images, Context context) {
        super(images, context);
        handler = new Handler();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Image image = mDataSet.get(position);

        handler.postDelayed(
                () -> API.getPicasso(mContext).load(image.getPath())
                        .fit()
                        .centerCrop()
                        .error(R.drawable.background_green)
                        .into(holder.imgImage)
                , 1000);

        holder.txtDateCreated.setText(String.format(App.LOCALE, "%s", image.getDate()));
        holder.imgDelete.setOnClickListener(v -> onDeleteClicked(image));
        applyTextFont(holder.txtDateCreated);
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
