package com.sorinaidea.ghaichi.webservice;

import android.content.Context;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sorinaidea.ghaichi.webservice.util.TLSSocketFactory;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mr-code on 4/25/2018.
 */

public class API {


    public static final String BASE_URL = "http://192.168.1.10/";
//    public static final String BASE_URL = "http://192.168.1.19/";
//    public static final String BASE_URL = "http://127.0.0.1/";
//    public static final String BASE_URL = "https://ghaichi.com/";

//    public static final String BASE_URL = "http://192.168.43.85/";

    private static Retrofit retrofit;
    private static Picasso picasso;

    public static Retrofit getRetrofit() {

        return (retrofit == null) ? initRetrofit() : retrofit;
    }


    public static Picasso getPicasso(Context context) {
        return (picasso == null) ? initPicasso(context) : picasso;
    }

    private static Picasso initPicasso(Context context) {


        Picasso.Builder  builder = new Picasso.Builder(context);
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
        OkHttpDownloader okHttpDownloader = new OkHttpDownloader(client);
        builder.downloader(okHttpDownloader);
        picasso = builder.build();

        return picasso;
    }

    private static Retrofit initRetrofit() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = null;

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {

            try {
                client = new OkHttpClient.Builder()
                        .sslSocketFactory(new TLSSocketFactory())
                        .build();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        } else {
            client = new OkHttpClient.Builder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build();
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return retrofit;
    }
}
