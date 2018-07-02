package com.sorinaidea.arayeshgah.model;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;


/**
 * Created by mr-code on 6/28/2018.
 */

public class ServiceListViewHolder extends GroupViewHolder {

    private TextView serviceListTitle;
    private AppCompatImageView imgArrow;

    public ServiceListViewHolder(View itemView) {
        super(itemView);
        serviceListTitle = (TextView) itemView.findViewById(R.id.txtName);
        imgArrow = (AppCompatImageView) itemView.findViewById(R.id.imgArrow);
    }

    public void setServiceListTitle(ExpandableGroup group) {
        serviceListTitle.setText(group.getTitle());
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        imgArrow.startAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        imgArrow.startAnimation(rotate);
    }
}
