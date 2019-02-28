package com.sorinaidea.ghaichi.model;

import android.graphics.Typeface;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;


/**
 * Created by mr-code on 6/28/2018.
 */

public class ReserveTimeViewHolder extends ChildViewHolder {

    private TextView txtName;
    private SwitchCompat swSelect;
    private Typeface fontIransans;
    private boolean isSelected = false;
    private final RotateAnimation rotateAnimation;

    public ReserveTimeViewHolder(View itemView) {
        super(itemView);
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        rotateAnimation.setRepeatCount(RotateAnimation.INFINITE);
        rotateAnimation.setDuration(2500);
        txtName = itemView.findViewById(R.id.txtTime);
        swSelect = itemView.findViewById(R.id.swSelect);
    }


    public void onBind(final Service service) {
        txtName.setText(service.getTitle());

        swSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            }
        });
        swSelect.setChecked(service.isSelected());
    }

}
