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
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProfileServices {

    @FormUrlEncoded
    @POST("api/user/profile/update")
    Call<Response> updateUser(  @Field("key") String key, @Field("value") String value);


    @GET("api/user/profile/info")
    Call<List<Data>> profile();


    @GET("api/user/profile/home")
    Call<List<Data>> home();



    @Multipart
    @POST("api/user/profile/avatar")
    Call<UploadImageResponse> changeAvatar( @Part MultipartBody.Part image);
}
