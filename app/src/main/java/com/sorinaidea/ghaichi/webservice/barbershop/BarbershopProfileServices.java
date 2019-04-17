package com.sorinaidea.ghaichi.webservice.barbershop;


import com.google.gson.JsonObject;
import com.sorinaidea.ghaichi.models.BusinessTime;
import com.sorinaidea.ghaichi.models.Data;
import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.models.UploadImageResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface BarbershopProfileServices {


    @FormUrlEncoded
    @POST("api/barbershop/profile/barbershop/update")
    Call<Response> updateBarbershop(@Field("key") String key, @Field("value") String value);


    @GET("api/barbershop/profile/u/info")
    Call<List<Data>> profile();


    @GET("api/barbershop/business")
    Call<List<BusinessTime>> businessTimes();


    @GET("api/barbershop/profile/b/info")
    Call<List<Data>> barbershop();


    @Multipart
    @POST("api/barbershop/profile/avatar")
    Call<UploadImageResponse> changeAvatar(@Part MultipartBody.Part image);


    @Multipart
    @POST("api/barbershop/profile/logo")
    Call<UploadImageResponse> changeLogo(@Part MultipartBody.Part image);


    @FormUrlEncoded
    @PUT("api/barbershop/business/update")
    Call<Response> updateBusinessTimes(@FieldMap Map<String, JsonObject> map);

}
