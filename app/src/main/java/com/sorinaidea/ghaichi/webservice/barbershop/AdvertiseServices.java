package com.sorinaidea.ghaichi.webservice.barbershop;


import com.sorinaidea.ghaichi.models.Pricing;
import com.sorinaidea.ghaichi.models.UploadImageResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AdvertiseServices {


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/barbershop/advertises/list/pricing")
    Call<List<Pricing>> fetchPricingList(@Header("Authorization") String authToken);

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @Multipart
    @POST("api/barbershop/profile/avatar")
    Call<UploadImageResponse> requestBannerAdvertise(@Header("Authorization") String authToken, @Part MultipartBody.Part image);

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @Multipart
    @POST("api/barbershop/profile/avatar")
    Call<UploadImageResponse> requestSpecialAdvertise(@Header("Authorization") String authToken, @Part MultipartBody.Part image);


}
