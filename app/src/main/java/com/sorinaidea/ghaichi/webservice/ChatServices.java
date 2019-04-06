package com.sorinaidea.ghaichi.webservice;


import com.sorinaidea.ghaichi.models.Message;
import com.sorinaidea.ghaichi.models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChatServices {

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/user/chat/messages")
    Call<List<Message>> index(@Header("Authorization") String authToken);

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @FormUrlEncoded
    @POST("api/user/chat/messages")
    Call<Response> send(@Header("Authorization") String authToken, @Field("message") String message);
}
