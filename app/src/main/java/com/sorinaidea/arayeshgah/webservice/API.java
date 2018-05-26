package com.sorinaidea.arayeshgah.webservice;

import com.sorinaidea.arayeshgah.model.BarberShop;
import com.sorinaidea.arayeshgah.model.ServerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by mr-code on 4/25/2018.
 */

public interface API {

    //the base URL for our API
    //make sure you are not using localhost
    //find the ip usinc ipconfig command
    String BASE_URL = "http://192.168.2.1/";

    //this is our multipart request
    //we have two parameters on is name and other one is description
//    @POST("check")
//    Call<ServerResponse> uploadImage(@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file, @Part("desc") RequestBody desc);



    @GET("barbershop")
    Call<List<BarberShop>> getAllBarberShops();


    @GET("barbershop/{id}")
    Call<BarberShop> getBarberShop(@Path("id") Integer barberShopId);


    @POST("login")
    Call<ServerResponse> login();


    @POST("logout")
    Call<ServerResponse> logout();
}
