package com.sorinaidea.ghaichi.webservice.barbershop;


import android.support.annotation.Nullable;

import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.models.Reserve;
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
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReserveServices {


    @GET("api/barbershop/reservations")
    Call<List<Reserve>> reserves( @Nullable @Query("date") String date, @Nullable @Query("status") String status);




    @GET("api/barbershop/services/{id}")
    Call<Service> srevice(@Header("Authorization") String authToken, @Path("id") int id);


    @FormUrlEncoded
    @POST("api/barbershop/services")
    Call<Response> create(@Header("Authorization") String authToken, @FieldMap Map<String, String> map);


    @DELETE("api/barbershop/services/{id}")
    Call<Response> delete(@Header("Authorization") String authToken, @Path("id") int id);


    @PUT("api/barbershop/services/{id}")
    Call<Response> update(@Header("Authorization") String authToken, @Path("id") int id);



    @Multipart
    @POST("api/barbershop/services/{id}/images")
    Call<Response> upload(@Header("Authorization") String authToken, @Path("id") int id, @Part MultipartBody.Part... images);


    @Multipart
    @POST("api/barbershop/services/{id}/discount")
    Call<Response> discount(@Header("Authorization") String authToken, @Path("id") int id, @FieldMap Map<String, String> params);


    @GET("api/barbershop/services/{id}/images")
    Call<List<Image>> samples(@Header("Authorization") String authToken, @Path("id") int id);

}
