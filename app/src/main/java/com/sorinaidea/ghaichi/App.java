package com.sorinaidea.ghaichi;

import android.os.Build;
import android.support.multidex.MultiDexApplication;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.net.ssl.SSLContext;

/**
 * Created by mr-code on 2/12/2018.
 * Application class of app.
 *
 */

public class App extends MultiDexApplication {


    /**
     * Default locale of application.
     */
    public static final Locale LOCALE = new Locale("fa");

    /**
     * An alternative for default locale
     */
    public static final Locale LOCALE_EN = new Locale("en");



    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * Check for api < 21 phone, and create ssl engine for it.
         * to access https:// API endpoints.
         */
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

}
