package com.sorinaidea.ghaichi.webservice;


import com.sorinaidea.ghaichi.models.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface AdvertisesService {

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/user/related/ads")
    Call<List<Image>> advertises(@Header("Authorization") String authToken);
}
