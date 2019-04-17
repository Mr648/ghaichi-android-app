package com.sorinaidea.ghaichi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.sorinaidea.ghaichi.util.GhaichiPreferenceManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.InternetConnectionListener;
import com.sorinaidea.ghaichi.webservice.NetworkConnectionInterceptor;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by mr-code on 2/12/2018.
 */

public class App extends MultiDexApplication {


    public static final Locale LOCALE = new Locale("fa");
    public static final Locale LOCALE_EN = new Locale("en");

    public AppCompatActivity currentActivity;
    public static LayoutInflater inflater;
    public static Handler handler;

    private InternetConnectionListener mInternetConnectionListener;
    public static final int DISK_CACHE_SIZE = 15 * 1024 * 1024; // 15 MB

    @Override
    public void onCreate() {
        super.onCreate();
        inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        handler = new Handler();


        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            try {
                ProviderInstaller.installIfNeeded(getApplicationContext());
                SSLContext sslContext;
                sslContext = SSLContext.getInstance("TLSv1.2");
                sslContext.init(null, null, null);
                sslContext.createSSLEngine();
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                    | NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }
        }

    }


    public void setInternetConnectionListener(InternetConnectionListener listener) {
        mInternetConnectionListener = listener;
    }

    public void removeInternetConnectionListener() {
        mInternetConnectionListener = null;
    }


    public Cache getCache() {
        File cacheDir = new File(getCacheDir(), "cache");
        return new Cache(cacheDir, DISK_CACHE_SIZE);
    }

    private boolean isInternetAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        try {
            return Objects.requireNonNull(activeNetworkInfo).isConnected();
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public static boolean hasAccessKey(Context context) {
        String isLoggedIn = GhaichiPreferenceManager.getDecryptedString(context, Util.KEYS.USER_ACCESS_KEY, null);
        return isLoggedIn != null && !isLoggedIn.isEmpty();
    }


    private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);

        okhttpClientBuilder.addInterceptor(new NetworkConnectionInterceptor() {
            @Override
            public boolean isInternetAvailable() {
                return App.this.isInternetAvailable();
            }

            @Override
            public void onInternetUnavailable() {
                if (mInternetConnectionListener != null) {
                    mInternetConnectionListener.onInternetUnavailable();
                }
            }

            @Override
            public void onCacheUnavailable() {
                if (mInternetConnectionListener != null) {
                    mInternetConnectionListener.onCacheUnavailable();
                }
            }
        });

        return okhttpClientBuilder.build();
    }

}
