package com.sorinaidea.ghaichi.webservice;

import com.sorinaidea.ghaichi.models.BarbershopProfile;
import com.sorinaidea.ghaichi.models.Comment;
import com.sorinaidea.ghaichi.models.CreateCommentPermission;
import com.sorinaidea.ghaichi.models.HomeData;
import com.sorinaidea.ghaichi.models.Location;
import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.models.ServiceCategory;
import com.sorinaidea.ghaichi.models.ServiceTurn;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BarbershopServices {


    @GET("api/app/barbershops/locations")
    Call<List<Location>> locations();


    @GET("api/barbershops/{id}/location")
    Call<Location> location(@Path("id") int id);


    @GET("api/app/barbershops/{id}")
    Call<BarbershopProfile> barbershop(@Path("id") int id);


    @GET("api/app/home?lat=35.312417&lng=46.9955655")
    Call<HomeData> barbershopsCards();



    @GET("api/app/barbershops/{id}/comments")
    Call<List<Comment>> comments(@Path("id") int id);


    @FormUrlEncoded
    @POST("api/app/barbershops/{id}/comments")
    Call<Response> comment(@Path("id") int id, @Field("comment") String comment, @Field("rating") String rating);

    @GET("api/app/barbershops/{id}/comments/check")
    Call<CreateCommentPermission> checkAccessForComment(@Path("id") int id);


    @GET("api/app/barbershops/{id}/services/categories")
    Call<List<ServiceCategory>> services(@Path("id") int id);

    @GET("api/app/barbershops/{barbershopId}/services/{serviceId}/photos")
    Call<List<String>> serviceImages(@Path("barbershopId") int barbershopId, @Path("serviceId") int serviceId);


    @FormUrlEncoded
    @POST("api/app/barbershops/{id}/turns")
    Call<List<ServiceTurn>> turns(@Path("id") int id,
                                  @Field("start") String start,
                                  @Field("end") String end,
                                  @Field("date") String date,
                                  @Field("services") String services);
}
