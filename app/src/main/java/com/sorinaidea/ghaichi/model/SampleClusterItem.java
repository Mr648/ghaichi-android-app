package com.sorinaidea.ghaichi.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import net.sharewire.googlemapsclustering.ClusterItem;

public class SampleClusterItem implements ClusterItem {

    private final Integer id;
    private final LatLng location;
    private final String title;
    private final String snippet;

    public SampleClusterItem(@NonNull Integer id,@NonNull LatLng location, @NonNull String title, @NonNull String snippet) {
        this.id = id;
        this.location = location;
        this.title = title;
        this.snippet = snippet;
    }

    public LatLng getLocation() {
        return location;
    }

    @Override
    public double getLatitude() {
        return location.latitude;
    }

    @Override
    public double getLongitude() {
        return location.longitude;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return snippet;
    }

    public Integer getId() {
        return id;
    }
}