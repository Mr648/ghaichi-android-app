package com.sorinaidea.arayeshgah.model;

import android.graphics.Typeface;
import android.icu.text.CompactDecimalFormat;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;


/**
 * Created by mr-code on 6/28/2018.
 */

public class ServiceViewHolder extends ChildViewHolder {

    private TextView txtName;
    private TextView txtPrice;
    private SwitchCompat swSelect;
    private Typeface fontIransans;

    public ServiceViewHolder(View itemView) {
        super(itemView);
        txtName = (TextView) itemView.findViewById(R.id.txtName);
        txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
        swSelect = (SwitchCompat) itemView.findViewById(R.id.swSelect);
    }


    public void onBind(Service service) {
        txtName.setText(service.getTitle());
        swSelect.setChecked(service.isSelected());
    }

    public void setServiceName(String name) {
        txtName.setText(name);
    }

    public void setServiceIsSelected(boolean isSelected) {
        swSelect.setChecked(isSelected);
    }

    public void setServicePrice() {

        txtPrice.setText(Integer.toString((int) (Math.random() * 15000)));

    }
}
