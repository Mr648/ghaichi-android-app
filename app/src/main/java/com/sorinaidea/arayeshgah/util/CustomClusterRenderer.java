package com.sorinaidea.arayeshgah.util;

import android.content.Context;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.ClusterMarker;

/**
 * Created by mr-code on 2/10/2018.
 */

public class CustomClusterRenderer extends DefaultClusterRenderer<ClusterMarker> {
    private final Context mContext;
    private final IconGenerator mClusterIconGenerator;
    private final IconGenerator mMarkerIconGenerator;


    private TextView txtClusterSize;


    public CustomClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<ClusterMarker> clusterManager) {
        super(context, map, clusterManager);
        mContext = context;

        mClusterIconGenerator = new IconGenerator(mContext);
        mMarkerIconGenerator = new IconGenerator(mContext);

//        FragmentActivity fa = (FragmentActivity) context;

//        View clusterView = fa.getLayoutInflater().inflate(R.layout.cluster, null);
//        View markerView = fa.getLayoutInflater().inflate(R.layout.marker, null);
//
//        txtClusterSize = (TextView) clusterView.findViewById(R.id.txtClusterSize);
//
//        mClusterIconGenerator.setContentView(clusterView);
//        mMarkerIconGenerator.setBackground(markerView.getBackground());

    }

    @Override
    protected void onBeforeClusterRendered(Cluster<ClusterMarker> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
//
//        txtClusterSize.setText(String.valueOf(cluster.getSize()));
//
//        final Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
//        BitmapDescriptor icon = SorinaApplication.getBitmapDescriptor(R.drawable.googleg_standard_color_18);
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
//        markerOptions.icon(icon);
    }

    @Override
    protected void onBeforeClusterItemRendered(ClusterMarker item,
                                               MarkerOptions markerOptions) {

        BitmapDescriptor icon = Util.getBitmapDescriptor(R.drawable.ic_placeholder, mContext);
        markerOptions.icon(icon);
    }

}
