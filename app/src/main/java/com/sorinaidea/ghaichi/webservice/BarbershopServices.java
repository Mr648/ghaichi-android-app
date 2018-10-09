package com.sorinaidea.ghaichi.webservice;

import com.sorinaidea.ghaichi.fast.Barbershop;
import com.sorinaidea.ghaichi.fast.BarbershopCard;
import com.sorinaidea.ghaichi.fast.Category;
import com.sorinaidea.ghaichi.fast.Comment;
import com.sorinaidea.ghaichi.fast.Location;
import com.sorinaidea.ghaichi.fast.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BarbershopServices {


    @GET("api/barbershops/all/locations")
    Call<List<Location>> locations(@Query("accessKey") String accessKey);

    @GET("api/barbershops/{id}/location")
    Call<Location> location(@Path("id") int id, @Query("accessKey") String accessKey);

    @GET("api/barbershops/{id}")
    Call<Barbershop> barbershop(@Path("id") int id, @Query("accessKey") String accessKey);

    @GET("api/barbershops/all/short")
    Call<List<BarbershopCard>> barbershopsCards(@Query("accessKey") String accessKey);


    @GET("api/barbershops/{id}/comments")
    Call<List<Comment>> comments(@Path("id") int id, @Query("accessKey") String accessKey);

    @GET("api/barbershops/{id}/services/categories")
    Call<List<Category>> services(@Path("id") int id, @Query("accessKey") String accessKey);

    @GET("api/barbershops/{barbershopId}/services/{serviceId}/photos")
    Call<List<Photo>> serviceImages(@Path("barbershopId") int barbershopId, @Path("serviceId") int serviceId, @Query("accessKey") String accessKey);
}
