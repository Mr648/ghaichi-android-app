package com.sorinaidea.ghaichi.webservice;

import com.sorinaidea.ghaichi.models.BarbershopProfile;
import com.sorinaidea.ghaichi.models.Comment;
import com.sorinaidea.ghaichi.models.CreateCommentPermission;
import com.sorinaidea.ghaichi.models.HomeData;
import com.sorinaidea.ghaichi.models.Location;
import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.models.ServiceCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BarbershopServices {

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/app/barbershops/locations")
    Call<List<Location>> locations(@Header("Authorization") String authToken);

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/barbershops/{id}/location")
    Call<Location> location(@Header("Authorization") String authToken, @Path("id") int id);

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/app/barbershops/{id}")
    Call<BarbershopProfile> barbershop(@Header("Authorization") String authToken, @Path("id") int id);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/app/home?lat=35.312417&lng=46.9955655")
    Call<HomeData> barbershopsCards(@Header("Authorization") String authToken);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/app/barbershops/{id}/comments")
    Call<List<Comment>> comments(@Header("Authorization") String authToken, @Path("id") int id);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @FormUrlEncoded
    @POST("api/app/barbershops/{id}/comments")
    Call<Response> comment(@Header("Authorization") String authToken, @Path("id") int id, @Field("comment") String comment, @Field("rating") String rating);

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/app/barbershops/{id}/comments/check")
    Call<CreateCommentPermission> checkAccessForComment(@Header("Authorization") String authToken, @Path("id") int id);

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/app/barbershops/{id}/services/categories")
    Call<List<ServiceCategory>> services(@Header("Authorization") String authToken, @Path("id") int id);

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/app/barbershops/{barbershopId}/services/{serviceId}/photos")
    Call<List<String>> serviceImages(@Header("Authorization") String authToken, @Path("barbershopId") int barbershopId, @Path("serviceId") int serviceId);
}
