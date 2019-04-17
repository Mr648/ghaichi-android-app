package com.sorinaidea.ghaichi.webservice;


import com.sorinaidea.ghaichi.models.Reserve;
import com.sorinaidea.ghaichi.models.Response;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserReserveServices {

    @GET("api/user/reservations")
    Call<List<Reserve>> reserves( );


    @FormUrlEncoded
    @POST("api/app/barbershops/{id}/reserve")
    Call<Response> reserve(  @Path("id") int id, @FieldMap Map<String, String> map);


    @DELETE("api/user/reservations")
    Call<Response> delete( @Path("id") int id);

}
