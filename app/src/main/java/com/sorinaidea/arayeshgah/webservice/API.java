package com.sorinaidea.arayeshgah.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sorinaidea.arayeshgah.model.BarberShop;
import com.sorinaidea.arayeshgah.model.ServerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by mr-code on 4/25/2018.
 */

public class API {


    public static final String BASE_URL = "http://192.168.1.10/";


    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {

        return (retrofit == null) ? initRetrofit() : retrofit;
    }

    private static Retrofit initRetrofit() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }


}
