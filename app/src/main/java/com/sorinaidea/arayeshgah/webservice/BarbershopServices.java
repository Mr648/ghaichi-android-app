package com.sorinaidea.arayeshgah.webservice;

import com.sorinaidea.arayeshgah.fast.Location;

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
}
