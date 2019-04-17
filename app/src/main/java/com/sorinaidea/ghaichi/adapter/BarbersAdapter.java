package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.BarberShortInfo;
import com.sorinaidea.ghaichi.webservice.API;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by mr-code on 5/6/2018.
 */

public class BarbersAdapter extends BaseAdapter<BarbersAdapter.ViewHolder, BarberShortInfo> {


    public BarbersAdapter(List<BarberShortInfo> barbers, Context context) {
        super(barbers, context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        CircleImageView imgLogo;
        TextView txtName;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.imgLogo = view.findViewById(R.id.imgLogo);
            this.txtName = view.findViewById(R.id.txtName);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bshops_featured, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        BarberShortInfo barber = mDataSet.get(position);

        API.getPicasso(mContext)
                .load(barber.getAvatar())
                .centerCrop()
                .fit()
                .placeholder(R.drawable.preview_small)
                .error(R.drawable.preview_small)
                .into(holder.imgLogo);
        holder.txtName.setText(barber.getName());
        applyTextFont(holder.txtName);
    }

}
