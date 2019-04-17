package com.sorinaidea.ghaichi.webservice;

import com.sorinaidea.ghaichi.fast.About;
import com.sorinaidea.ghaichi.fast.FAQ;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SystemServices {


    @GET("api/system/faq")
    Call<List<FAQ>> faqs();

    @GET("api/system/about")
    Call<About> about();
}
