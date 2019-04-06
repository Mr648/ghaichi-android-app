package com.sorinaidea.ghaichi.webservice;

import com.sorinaidea.ghaichi.fast.About;
import com.sorinaidea.ghaichi.fast.FAQ;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface SystemServices {

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/system/faq")
    Call<List<FAQ>> faqs(@Header("Authorization") String authToken);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/system/about")
    Call<About> about(@Header("Authorization") String authToken);
}
