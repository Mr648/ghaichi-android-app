package com.sorinaidea.ghaichi.webservice;


import com.sorinaidea.ghaichi.models.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AdvertisesService {


    @GET("api/user/related/ads")
    Call<List<Image>> advertises();
}
