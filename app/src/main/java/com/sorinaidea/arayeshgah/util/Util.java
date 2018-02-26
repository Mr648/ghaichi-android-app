package com.sorinaidea.arayeshgah.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.VectorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by mr-code on 1/30/2018.
 */

public class Util {


    public static final class CONSTANTS{
        public static final String TAG = "SORINA_IDEA_ARAYESHGAH";
        public static final String BASE_URL = "http://sorinaidea.ir";
        public static final String REGEX_PHONE
            = "^(0|\\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}$";
    }

    public static BitmapDescriptor getBitmapDescriptor(int id, Context context) {

        // TODO amasa
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vectorDrawable = (VectorDrawable) context.getDrawable(id);

            int h = vectorDrawable.getIntrinsicHeight();
            int w = vectorDrawable.getIntrinsicWidth();

            vectorDrawable.setBounds(0, 0, w, h);
            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bm);
            vectorDrawable.draw(canvas);

            return BitmapDescriptorFactory.fromBitmap(bm);
        } else {

            return BitmapDescriptorFactory.fromResource(id);
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
