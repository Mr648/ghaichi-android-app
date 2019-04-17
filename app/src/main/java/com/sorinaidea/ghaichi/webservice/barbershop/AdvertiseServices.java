package com.sorinaidea.ghaichi.webservice.barbershop;


import com.sorinaidea.ghaichi.models.BaseAdvertise;
import com.sorinaidea.ghaichi.models.Data;
import com.sorinaidea.ghaichi.models.Pricing;
import com.sorinaidea.ghaichi.models.Response;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AdvertiseServices {


    @GET("api/barbershop/advertises/list/pricing")
    Call<List<Pricing>> fetchPricingList();

    @FormUrlEncoded
    @POST("api/barbershop/advertises/update")
    Call<Response> updateAdvertise(@Field("key") String key, @Field("value") String value);


    @GET("api/barbershop/advertises")
    Call<List<BaseAdvertise>> advertises();

    @GET("api/barbershop/advertises/{advertise}")
    Call<List<Data>> advertise(@Path("advertise") int advertiseId);


    @Multipart
    @POST("api/barbershop/advertises/banner")
    Call<Response> requestBannerAdvertise(@Part MultipartBody.Part image, @Part("description") String description, @Part("pricing") int pricing);


    @Multipart
    @POST("api/barbershop/advertises/special")
    Call<Response> requestSpecialAdvertise(@Part("description") String description, @Part("pricing") int pricing);


}
