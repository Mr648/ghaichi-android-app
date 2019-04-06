package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BaseAdapter;
import com.sorinaidea.ghaichi.models.Barber;
import com.sorinaidea.ghaichi.webservice.API;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mr-code on 3/10/2018.
 */

public class BarberInfoAdapter extends BaseAdapter<BarberInfoAdapter.ViewHolder, Barber> {

    public interface BarberClickListener {
        void update(Barber barber);

        void delete(Barber barber);
    }

    private BarberClickListener clickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtMobile;
        private CircleImageView imgLogo;
        private AppCompatImageView imgDelete;
        private AppCompatImageView imgEdit;

        public ViewHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.txtName);
            txtMobile = v.findViewById(R.id.txtMobile);
            imgLogo = v.findViewById(R.id.imgLogo);
            imgDelete = v.findViewById(R.id.imgDelete);
            imgEdit = v.findViewById(R.id.imgEdit);
        }

        public TextView getTxtName() {
            return txtName;
        }

        public TextView getTxtMobile() {
            return txtMobile;
        }

        public CircleImageView getImgLogo() {
            return imgLogo;
        }

        public AppCompatImageView getImgEdit() {
            return imgEdit;
        }

        public AppCompatImageView getImgDelete() {
            return imgDelete;
        }
    }


    public BarberInfoAdapter(ArrayList<Barber> dataset, Context context, BarberClickListener clickListener) {
        super(dataset, context);
        this.clickListener = clickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_barber_info, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        applyTextFont(viewHolder.getTxtName(), viewHolder.getTxtMobile());

        Barber barber = mDataSet.get(position);

        API.getPicasso(mContext)
                .load(barber.getAvatar())
                .fit()
                .centerInside()
                .error(R.drawable.ic_account_circle_white_24dp)
                .into(viewHolder.getImgLogo());

        viewHolder.getTxtName().setText(String.format(App.LOCALE, "%s %s", barber.getName(), barber.getFamily()));
        viewHolder.getTxtMobile().setText(barber.getMobile());

        viewHolder.getImgDelete().setOnClickListener(view -> clickListener.delete(barber));
        viewHolder.getImgEdit().setOnClickListener(view -> clickListener.update(barber));
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
