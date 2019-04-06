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
        private TextView txtName;
        private TextView txtTime;
        private TextView txtPrice;
        private TextView txtSamples;
        private TextView txtBarbers;
        private TextView txtDateCreated;


        private AppCompatImageView imgAddBarber;
        private AppCompatImageView imgEdit;
        private AppCompatImageView imgAddSample;
        private AppCompatImageView imgDelete;

        private AppCompatImageView imgService;

        public ViewHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.txtName);
            txtTime = v.findViewById(R.id.txtTime);
            txtPrice = v.findViewById(R.id.txtPrice);
            txtSamples = v.findViewById(R.id.txtSamples);
            txtBarbers = v.findViewById(R.id.txtBarbers);
            txtDateCreated = v.findViewById(R.id.txtDateCreated);

            imgAddBarber = v.findViewById(R.id.imgAddBarber);
            imgEdit = v.findViewById(R.id.imgEdit);
            imgAddSample = v.findViewById(R.id.imgAddSample);
            imgDelete = v.findViewById(R.id.imgDelete);
            imgService = v.findViewById(R.id.imgService);
        }

        public TextView getTxtName() {
            return txtName;
        }

        public AppCompatImageView getImgAddBarber() {
            return imgAddBarber;
        }

        public AppCompatImageView getImgAddSample() {
            return imgAddSample;
        }

        public TextView getTxtBarbers() {
            return txtBarbers;
        }

        public TextView getTxtPrice() {
            return txtPrice;
        }

        public TextView getTxtSamples() {
            return txtSamples;
        }

        public TextView getTxtTime() {
            return txtTime;
        }

        public TextView getTxtDateCreated() {
            return txtDateCreated;
        }

        public AppCompatImageView getImgDelete() {
            return imgDelete;
        }

        public AppCompatImageView getImgEdit() {
            return imgEdit;
        }

        public AppCompatImageView getImgService() {
            return imgService;
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

        viewHolder.getTxtName().setText(service.getName());
        viewHolder.getTxtTime().setText(String.valueOf(service.getTime()));
        viewHolder.getTxtPrice().setText(String.valueOf(service.getPrice()));
        viewHolder.getTxtSamples().setText(String.valueOf(service.getImages().size()));
        viewHolder.getTxtBarbers().setText(String.valueOf(service.getBarbers().size()));
        viewHolder.getTxtDateCreated().setText(service.getCreatedAt());


        viewHolder.getImgAddSample().setOnClickListener(view -> showImages(service));
        viewHolder.getImgDelete().setOnClickListener(view -> delete(service));


        API.getPicasso(mContext)
                .load(service.getBanner())
                .fit()
                .centerCrop()
                .error(R.drawable.ic_account_circle_white_24dp)
                .into(viewHolder.getImgService());

        applyTextFont(viewHolder.getTxtName(),
                viewHolder.getTxtTime(),
                viewHolder.getTxtPrice(),
                viewHolder.getTxtSamples(),
                viewHolder.getTxtBarbers(),
                viewHolder.getTxtDateCreated());
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
