package com.sorinaidea.arayeshgah.model;

import android.graphics.drawable.Icon;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by mr-code on 2/7/2018.
 */


public class ClusterMarker implements ClusterItem {

    private BitmapDescriptor icon;
    private String title;
    private String snippet;
    private LatLng position;


    @Override
    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BitmapDescriptor getIcon() {
        return icon;
    }

    public void setIcon(BitmapDescriptor icon) {
        this.icon = icon;
    }
}