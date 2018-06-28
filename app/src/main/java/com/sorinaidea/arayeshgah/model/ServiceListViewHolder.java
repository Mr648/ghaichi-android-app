package com.sorinaidea.arayeshgah.model;

import android.view.View;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;


/**
 * Created by mr-code on 6/28/2018.
 */

public class ServiceListViewHolder extends GroupViewHolder {

    private TextView serviceListTitle;

    public ServiceListViewHolder(View itemView) {
        super(itemView);
        serviceListTitle = (TextView) itemView.findViewById(R.id.txtTitle);
    }

    public void setServiceListTitle(ExpandableGroup group) {
        serviceListTitle.setText(group.getTitle());
    }
}
