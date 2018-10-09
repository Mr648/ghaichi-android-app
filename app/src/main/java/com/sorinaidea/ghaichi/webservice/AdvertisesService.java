package com.sorinaidea.ghaichi.webservice;


import com.sorinaidea.ghaichi.fast.Advertise;
import com.sorinaidea.ghaichi.fast.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AdvertisesService {

    @GET("api/user/related/ads")
    Call<List<Advertise>> advertises(@Query("accessKey") String accessKey);
}
