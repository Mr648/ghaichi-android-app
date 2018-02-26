package com.sorinaidea.arayeshgah.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by mr-code on 2/12/2018.
 */

public class SorinaApplication extends Application {

    public static final String TAG = "SORINA_IDEA_ARAYESHGAH";
    public static Context context;
    public static AppCompatActivity currentActivity;
    public static LayoutInflater inflater;
    public static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        handler = new Handler();
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public static BitmapDescriptor getBitmapDescriptor(int id) {

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


}
