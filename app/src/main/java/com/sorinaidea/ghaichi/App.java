package com.sorinaidea.ghaichi;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.sorinaidea.ghaichi.util.GhaichiPrefrenceManager;
import com.sorinaidea.ghaichi.util.Util;

import java.util.Locale;

/**
 * Created by mr-code on 2/12/2018.
 */

public class App extends Application {


    public static final Locale LOCALE = new Locale("fa");
    public static final Locale LOCALE_EN = new Locale("en");

    public AppCompatActivity currentActivity;

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

    public static boolean hasAccessKey(Context context) {
        String isLoggedIn = GhaichiPrefrenceManager.getDecryptedString(context, Util.KEYS.USER_ACCESS_KEY, null);
        return isLoggedIn != null && !isLoggedIn.isEmpty();
    }


}
