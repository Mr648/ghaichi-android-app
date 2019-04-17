package com.sorinaidea.ghaichi.webservice.barbershop;


import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.models.Service;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ServiceServices {


    @GET("api/barbershop/services")
    Call<List<Service>> srevices();


    @GET("api/barbershop/services/{id}")
    Call<Service> srevice(@Path("id") int id);


    @FormUrlEncoded
    @POST("api/barbershop/services")
    Call<Response> create(@FieldMap Map<String, String> map);


    @DELETE("api/barbershop/services/{id}")
    Call<Response> delete(@Path("id") int id);


    @PUT("api/barbershop/services/{id}")
    Call<Response> update(@Path("id") int id);


    @Multipart
    @POST("api/barbershop/services/{id}/images")
    Call<Response> upload(@Path("id") int id, @Part MultipartBody.Part... images);


    @Multipart
    @POST("api/barbershop/services/{id}/discount")
    Call<Response> discount(@Path("id") int id, @FieldMap Map<String, String> params);


    @GET("api/barbershop/services/{id}/images")
    Call<List<Image>> samples(@Path("id") int id);

}
