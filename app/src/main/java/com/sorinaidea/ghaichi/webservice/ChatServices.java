package com.sorinaidea.ghaichi.webservice;


import com.sorinaidea.ghaichi.models.Message;
import com.sorinaidea.ghaichi.models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChatServices {

    @GET("api/user/chat/messages")
    Call<List<Message>> index();


    @FormUrlEncoded
    @POST("api/user/chat/messages")
    Call<Response> send( @Field("message") String message);
}
