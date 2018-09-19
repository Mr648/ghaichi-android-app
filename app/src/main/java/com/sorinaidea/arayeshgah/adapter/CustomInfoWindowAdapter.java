package com.sorinaidea.arayeshgah.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.ui.BarberShopActivity;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.SorinaApplication;
import com.sorinaidea.arayeshgah.util.picasso.CircleTransformation;
import com.sorinaidea.arayeshgah.util.picasso.RoundedTransformation;
import com.sorinaidea.arayeshgah.webservice.API;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
        String markerBarbershopId = marker.getTitle().split("@")[1];
        txtTitle.setText(markerTitle);
        marker.setTag(markerBarbershopId);
        String icon = marker.getSnippet();

        FontManager.setFont(txtTitle, fontIransans);


        // TODO: use glide to get logo of marker

        try {
            Picasso.with(context)
                    .load(API.BASE_URL
                            + URLDecoder.decode(icon, "UTF-8"))
                    .resize(48, 48)
                    .centerCrop()
                    .transform(new CircleTransformation()).into(imgLogo);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }
}
