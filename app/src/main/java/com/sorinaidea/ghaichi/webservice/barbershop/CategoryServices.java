package com.sorinaidea.ghaichi.webservice.barbershop;


import com.sorinaidea.ghaichi.models.Category;
import com.sorinaidea.ghaichi.models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface  CategoryServices {


    @GET("api/barbershop/categories")
    Call<List<Category>> categories();


    @GET("api/barbershop/categories/{id}")
    Call<Category> category(@Path("id") int id);

    @POST("api/barbershop/categories")
    Call<Response> create(@Body Category category);


    @DELETE("api/barbershop/categories/{id}")
    Call<Response> delete(@Path("id") int id);


    @PUT("api/barbershop/categories/{id}")
    Call<Response> update(@Path("id") int id, @Body Category category);

}
