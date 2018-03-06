package com.sorinaidea.arayeshgah.util;

import android.app.Application;
import android.content.Context;
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
}
