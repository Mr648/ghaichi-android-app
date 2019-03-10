package com.sorinaidea.ghaichi.webservice.barbershop;


import com.sorinaidea.ghaichi.models.Data;
import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.models.UploadImageResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProfileServices {

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @FormUrlEncoded
    @POST("api/barbershop/profile/update")
    Call<Response> update(@Header("Authorization") String authToken, @Field("key") String key, @Field("value") String value);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/barbershop/profile/info")
    Call<List<Data>> profile(@Header("Authorization") String authToken);

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @Multipart
    @POST("api/barbershop/profile/avatar")
    Call<UploadImageResponse> changeAvatar(@Header("Authorization") String authToken, @Part MultipartBody.Part image);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @POST("api/barbershop/profile/logo")
    Call<UploadImageResponse> logo(@Header("Authorization") String authToken, @Part MultipartBody.Part image);
}
