package com.sorinaidea.ghaichi.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mr-code on 4/25/2018.
 */

public class API {


    public static final String BASE_URL = "http://192.168.43.85/";


    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {

        return (retrofit == null) ? initRetrofit() : retrofit;
    }

    private static Retrofit initRetrofit() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client=new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }


}
