package com.sorinaidea.ghaichi.webservice.barbershop;


import com.sorinaidea.ghaichi.models.Barber;
import com.sorinaidea.ghaichi.models.Response;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BarberServices {


    @GET("api/barbershop/barbers")
    Call<List<Barber>> barbers();


    @GET("api/barbershop/barbers/{id}")
    Call<Barber> barber(@Path("id") int id);


    @Multipart
    @POST("api/barbershop/barbers")
    Call<Response> create(@Part("barber") Barber barber, @Part MultipartBody.Part image);


    @DELETE("api/barbershop/barbers/{id}")
    Call<Response> delete(@Path("id") int id);


    @Multipart
    @POST("api/barbershop/barbers/{id}/avatar")
    Call<Response> avatar(@Path("id") int id, @Part MultipartBody.Part avatar);


    @FormUrlEncoded
    @PUT("api/barbershop/barbers/{id}")
    Call<Response> update(@Path("id") int id,
                          @Field("name") String name,
                          @Field("family") String family,
                          @Field("mobile") String mobile
    );
}
