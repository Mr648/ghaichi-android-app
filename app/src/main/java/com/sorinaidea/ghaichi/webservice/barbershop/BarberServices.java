package com.sorinaidea.ghaichi.webservice.barbershop;


import android.support.annotation.Nullable;

import com.sorinaidea.ghaichi.models.Barber;
import com.sorinaidea.ghaichi.models.Category;
import com.sorinaidea.ghaichi.models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BarberServices {

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/barbershop/barbers")
    Call<List<Barber>> barbers(@Header("Authorization") String authToken);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/barbershop/barbers/{id}")
    Call<Barber> barber(@Header("Authorization") String authToken, @Path("id") int id);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/barbershop/barbers/create")
    Call<Response> create(@Header("Authorization") String authToken, @Body Barber category);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @DELETE("api/barbershop/barbers/{id}")
    Call<Response> delete(@Header("Authorization") String authToken, @Path("id") int id);



    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @PUT("api/barbershop/barbers/{id}")
    Call<Response> update(@Header("Authorization") String authToken, @Path("id") int id);
}
