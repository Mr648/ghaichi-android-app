package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BaseAdapter;
import com.sorinaidea.ghaichi.models.Service;
import com.sorinaidea.ghaichi.webservice.API;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */


public abstract class ServiceAdapter extends BaseAdapter<ServiceAdapter.ViewHolder, Service> {

    public abstract void showImages(Service service);

    public abstract void delete(Service service);

    public abstract void addBarber(Service service);

    public ServiceAdapter(List<Service> services, Context context) {
        super(services, context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtTime;
        TextView txtPrice;
        TextView txtSamples;
        TextView txtBarbers;
        TextView txtDateCreated;


        AppCompatImageView imgEdit;
        AppCompatImageView imgAddSample;
        AppCompatImageView imgDelete;

        AppCompatImageView imgService;

        public ViewHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.txtName);
            txtTime = v.findViewById(R.id.txtTime);
            txtPrice = v.findViewById(R.id.txtPrice);
            txtSamples = v.findViewById(R.id.txtSamples);
            txtBarbers = v.findViewById(R.id.txtBarbers);
            txtDateCreated = v.findViewById(R.id.txtDateCreated);

            imgEdit = v.findViewById(R.id.imgEdit);
            imgAddSample = v.findViewById(R.id.imgAddSample);
            imgDelete = v.findViewById(R.id.imgDelete);
            imgService = v.findViewById(R.id.imgService);
        }


    }


    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_service, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Service service = mDataSet.get(position);

        viewHolder.txtName.setText(service.getName());
        viewHolder.txtTime.setText(String.valueOf(service.getTime()));
        viewHolder.txtPrice.setText(String.valueOf(service.getPrice()));
        viewHolder.txtSamples.setText(String.valueOf(service.getImages().size()));
        viewHolder.txtBarbers.setText(String.valueOf(service.getBarbers().size()));
        viewHolder.txtDateCreated.setText(service.getCreatedAt());


        viewHolder.imgAddSample.setOnClickListener(view -> showImages(service));
        viewHolder.imgDelete.setOnClickListener(view -> delete(service));


        API.getPicasso(mContext)
                .load(service.getBanner())
                .fit()
                .centerCrop()
                .error(R.drawable.ic_account_circle_white_24dp)
                .into(viewHolder.imgService);

        applyTextFont(
                viewHolder.txtName,
                viewHolder.txtTime,
                viewHolder.txtPrice,
                viewHolder.txtSamples,
                viewHolder.txtBarbers,
                viewHolder.txtDateCreated
        );
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
