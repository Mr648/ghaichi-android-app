package com.sorinaidea.ghaichi.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.picasso.CircleTransformation;
import com.sorinaidea.ghaichi.webservice.API;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by mr-code on 2/12/2018.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity context;
    private Typeface fontIransans;


    public CustomInfoWindowAdapter(Activity context) {
        this.context = context;
        fontIransans = FontManager.getTypeface(context, FontManager.IRANSANS_TEXTS);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }


    @Override
    public View getInfoContents(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.marker_info_window, null);


        TextView txtTitle = (TextView) view.findViewById(R.id.marker_title);
        ImageView imgLogo = (ImageView) view.findViewById(R.id.marker_logo);

        String markerTitle = marker.getTitle().split("@")[0];

        txtTitle.setText(markerTitle);
        String icon = marker.getSnippet();
        FontManager.setFont(txtTitle, fontIransans);


        // TODO: use glide to get logo of marker

        if (icon == null || (icon!=null && icon.isEmpty())) {
            imgLogo.setImageResource(R.drawable.ic_cluster);
        } else {
            try {
                API.getPicasso(context)
                        .load(API.BASE_URL
                                + URLDecoder.decode(icon, "UTF-8"))
                        .resize(128,128)
                        .transform(new CircleTransformation()).into(imgLogo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}
