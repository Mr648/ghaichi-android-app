package com.sorinaidea.ghaichi.webservice.barbershop;


import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.models.UploadImageResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProfileServices {

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @POST("api/barbershop/categories")
    Call<Response> update(@Header("Authorization") String authToken, @Field("key") String key, @Field("value") String value);



    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/barbershop/profile/info")
    Call<Map<String, String>> profile(@Header("Authorization") String authToken);

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @POST("api/barbershop/profile/avatar")
    Call<UploadImageResponse> avatar(@Header("Authorization") String authToken, @Part MultipartBody.Part image);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @POST("api/barbershop/profile/logo")
    Call<UploadImageResponse> logo(@Header("Authorization") String authToken, @Part MultipartBody.Part image);
}
