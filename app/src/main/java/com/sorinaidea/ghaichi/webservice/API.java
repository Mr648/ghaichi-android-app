package com.sorinaidea.ghaichi.webservice;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.webservice.util.TLSSocketFactory;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mr-code on 4/25/2018.
 */

public class API {


    //public static final String BASE_URL = "http://192.168.1.17/";
    //public static final String BASE_URL = "http://192.168.43.85/";

    public static final int DISK_CACHE_SIZE = 100 * 1024 * 1024; // 50 MB


//    public static final String BASE_URL = "https://ghaichi.com/";

    public static final String BASE_URL = "http://192.168.1.10/";

    private static final int READ_TIMEOUT_SECONDS = 60;
    private static final int WRITE_TIMEOUT_SECONDS = 60;
    private static final int CONNECTION_TIMEOUT_SECONDS = 60;


    private static Retrofit retrofit;
    private static Picasso picasso;

    public static Retrofit getRetrofit(Context context) {
        return (retrofit == null) ? initRetrofit(context, true) : retrofit;
    }

    public static Retrofit getNonAuthRetrofit(Context context) {
        return (retrofit == null) ? initRetrofit(context, false) : retrofit;
    }


    public static Picasso getPicasso(Context context) {
        return (picasso == null) ? initPicasso(context) : picasso;
    }

    private static Picasso initPicasso(Context context) {

        Picasso.Builder builder = new Picasso.Builder(context);
        com.squareup.okhttp.OkHttpClient client = new com.squareup.okhttp.OkHttpClient();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            try {
                client.setSslSocketFactory(new TLSSocketFactory());
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

// Caching images.
        OkHttpDownloader okHttpDownloader = new OkHttpDownloader(context.getCacheDir(), DISK_CACHE_SIZE); // 100 Megabytes
        builder.downloader(okHttpDownloader);

        picasso = builder.build();
        return picasso;
    }

    private static Interceptor getRetryInterceptor() {
        return chain -> {
            Request.Builder builder = chain.request().newBuilder();

            okhttp3.Response response = chain.proceed(builder.build());

            int tryCount = 0;
            while (!response.isSuccessful() && tryCount < 3) {
                tryCount++;
                response = chain.proceed(builder.build());
            }
            // otherwise just pass the original response on
            return response;
        };
    }

    private static Interceptor getAuthorizationInterceptor(@NonNull final String token) {
        return chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", token)
                    .build();
            return chain.proceed(newRequest);
        };
    }

    private static Interceptor getUserAgentInterceptor() {
        return chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("User-Agent", "GHAICHI-APPLICATION-USER")
                    .build();
            return chain.proceed(newRequest);
        };
    }


    private static Retrofit initRetrofit(Context context, boolean authRequired) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(getRetryInterceptor())
                .addInterceptor(getUserAgentInterceptor())
                .addInterceptor(httpLoggingInterceptor);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            try {
                X509TrustManager x509TrustManager =
                        new X509TrustManager() {
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[0];
                            }

                            public void checkServerTrusted(X509Certificate[] chain, String authType) {
                            }

                            public void checkClientTrusted(X509Certificate[] chain, String authType) {
                            }
                        };
                builder.sslSocketFactory(new TLSSocketFactory(), x509TrustManager);
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }


        if (authRequired) {
            try {
                builder.addInterceptor(getAuthorizationInterceptor(Objects.requireNonNull(Auth.getAccessKey(context))));
            } catch (NullPointerException ignore) {
                Auth.logout(context);
            }
        }

        client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }


}
