package com.sorinaidea.arayeshgah.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.ui.BarberShopActivity;
import com.sorinaidea.arayeshgah.util.SorinaApplication;
import com.sorinaidea.arayeshgah.util.picasso.CircleTransformation;
import com.sorinaidea.arayeshgah.util.picasso.RoundedTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by mr-code on 2/12/2018.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity context;

    public CustomInfoWindowAdapter(Activity context) {
        this.context = context;
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

        txtTitle.setText("ناز عروس");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BarberShopActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
//        String url = "https://cdn2.iconfinder.com/data/icons/flat-jewels-icon-set/512/0002_Tree.png";
        String url = "https://cdn2.iconfinder.com/data/icons/flat-jewels-icon-set/512/0002_Tree.png";


        // TODO: use glide to get logo of marker


        Picasso.with(context)
                .load(url)
                .resize(48, 48)
                .centerCrop()
                .transform(new CircleTransformation()).into(imgLogo);


//        Glide.with(context).load(url).into(imgLogo);


        return view;
    }
}
