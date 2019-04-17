package com.sorinaidea.ghaichi.webservice.barbershop;


import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SampleServices {


    @GET("api/barbershop/samples")
    Call<List<Image>> index(@Query("type") String type, @Query("id") int id);


    @GET("api/barbershop/samples")
    Call<List<Image>> index();


    @DELETE("api/barbershop/samples/delete/{id}")
    Call<Response> delete(@Path("id") int id);
}
