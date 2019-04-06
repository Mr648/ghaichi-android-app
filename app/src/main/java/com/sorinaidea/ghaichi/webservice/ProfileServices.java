package com.sorinaidea.ghaichi.webservice;

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
    @POST("api/user/profile/update")
    Call<Response> updateUser(@Header("Authorization") String authToken, @Field("key") String key, @Field("value") String value);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/user/profile/info")
    Call<List<Data>> profile(@Header("Authorization") String authToken);

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/user/profile/home")
    Call<List<Data>> home(@Header("Authorization") String authToken);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @Multipart
    @POST("api/user/profile/avatar")
    Call<UploadImageResponse> changeAvatar(@Header("Authorization") String authToken, @Part MultipartBody.Part image);
}
