package com.sorinaidea.arayeshgah.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

/**
 * Created by mr-code on 2/12/2018.
 */

public class SorinaApplication extends Application {


    public static AppCompatActivity currentActivity;
    public static LayoutInflater inflater;
    public static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        handler = new Handler();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
