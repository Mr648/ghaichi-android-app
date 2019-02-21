package com.sorinaidea.ghaichi.webservice.barbershop;


import android.support.annotation.Nullable;

import com.sorinaidea.ghaichi.models.Category;
import com.sorinaidea.ghaichi.models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryServices {

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/barbershop/categories")
    Call<List<Category>> categories(@Header("Authorization") String authToken);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/barbershop/categories/{id}")
    Call<Category> category(@Header("Authorization") String authToken, @Path("id") int id);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @POST("api/barbershop/categories")
    Call<Response> create(@Header("Authorization") String authToken,  @Body Category category);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @DELETE("api/barbershop/categories/{id}")
    Call<Response> delete(@Header("Authorization") String authToken, @Path("id") int id);



    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @PUT("api/barbershop/categories/{id}")
    Call<Response> update(@Header("Authorization") String authToken, @Path("id") int id, @Body Category category);


    /*@Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
            "Content-Type: application/json"
    })
    @PUT("api/barbershop/categories/{id}")
    Call<Response> update(@Header("Authorization") String authToken, @Path("id") int id, @Body String json);*/
}
