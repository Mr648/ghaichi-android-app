package com.sorinaidea.arayeshgah.model;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.CompactDecimalFormat;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.ui.PriceUpdater;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;


/**
 * Created by mr-code on 6/28/2018.
 */

public class ServiceViewHolder extends ChildViewHolder {

    private TextView txtName;
    private TextView txtPrice;
    private TextView txtPercent;
    private AppCompatImageView imgIcon;
    private SwitchCompat swSelect;
    private RelativeLayout relativeDiscount;
    private Typeface fontIransans;
    private boolean isSelected = false;
    private final RotateAnimation rotateAnimation;
    private final PriceUpdater updater;

    public ServiceViewHolder(View itemView, PriceUpdater updater) {
        super(itemView);
        this.updater = updater;
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        rotateAnimation.setRepeatCount(RotateAnimation.INFINITE);
        rotateAnimation.setDuration(2500);
        txtName = (TextView) itemView.findViewById(R.id.txtName);
        txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
        txtPercent = (TextView) itemView.findViewById(R.id.txtPercent);
        imgIcon = (AppCompatImageView) itemView.findViewById(R.id.imgIcon);
        swSelect = (SwitchCompat) itemView.findViewById(R.id.swSelect);
        relativeDiscount = (RelativeLayout) itemView.findViewById(R.id.relativeDiscount);
    }


    public void onBind(final Service service) {
        txtName.setText(service.getTitle());

        if (service.hasDiscount()) {
            relativeDiscount.setVisibility(View.VISIBLE);
            imgIcon.startAnimation(rotateAnimation);
            txtPercent.setText(String.format("%.1f%%", service.getDiscountPercent() * 100.0f));
        } else {
            relativeDiscount.setVisibility(View.GONE);
        }


        txtPrice.setText(String.format("%.2f تومان", service.getPrice()));
        swSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    updater.update(service.getPrice());
                    service.setSelected(true);
                } else {
                    if (service.isSelected()) {
                        updater.update(-1.0f * service.getPrice());
                    }

                    service.setSelected(false);
                }
            }
        });
        swSelect.setChecked(service.isSelected());
    }

}
