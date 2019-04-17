package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.Reserve;
import com.sorinaidea.ghaichi.webservice.API;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mr-code on 3/10/2018.
 */

public class ReservationAdapter extends BaseAdapter<ReservationAdapter.ViewHolder, Reserve> {

    public ReservationAdapter(List<Reserve> reserves, Context context) {
        super(reserves, context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgAvatar;
        TextView txtName;
        TextView txtPrice;
        TextView txtDate;
        TextView txtTime;

        RecyclerView recServices;

        AppCompatImageButton btnCancel;
        AppCompatImageButton btnAccept;
        AppCompatImageButton btnMessage;
        AppCompatImageButton btnAlarm;
        AppCompatImageButton btnCall;

        public ViewHolder(View v) {
            super(v);
            imgAvatar = v.findViewById(R.id.imgAvatar);
            txtName = v.findViewById(R.id.txtName);
            txtPrice = v.findViewById(R.id.txtPrice);
            txtDate = v.findViewById(R.id.txtDate);
            txtTime = v.findViewById(R.id.txtTime);

            btnCancel = v.findViewById(R.id.btnCancel);
            btnAccept = v.findViewById(R.id.btnAccept);
            btnMessage = v.findViewById(R.id.btnMessage);
            btnAlarm = v.findViewById(R.id.btnAlarm);
            btnCall = v.findViewById(R.id.btnCall);

            recServices = v.findViewById(R.id.recServices);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reservation_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Reserve reserve = mDataSet.get(position);
        viewHolder.txtName.setText(reserve.getUser().getFullName());
        viewHolder.txtPrice.setText(String.format(App.LOCALE, "هزینه خدمات %s", reserve.getPrice()));
        viewHolder.txtDate.setText(reserve.getDate());
        viewHolder.txtTime.setText(reserve.getTime());

        API.getPicasso(mContext)
                .load(reserve.getUser().getAvatar())
                .centerCrop()
                .fit()
                .placeholder(R.drawable.preview_small)
                .error(R.drawable.preview_small)
                .into(viewHolder.imgAvatar);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        viewHolder.recServices.setLayoutManager(layoutManager);
        viewHolder.recServices.addItemDecoration(new ItemOffsetDecoration(4));
        viewHolder.recServices.setAdapter(new ReserveServiceAdapter(reserve.getServices(), mContext));

        applyTextBoldFont(viewHolder.txtPrice);

        applyTextFont(
                viewHolder.txtName,
                viewHolder.txtDate,
                viewHolder.txtTime
        );

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
