package com.sorinaidea.ghaichi.webservice.barbershop;


import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.models.UploadImageResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BannerServices {

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @GET("api/barbershop/banners")
    Call<List<Image>> index(@Header("Authorization") String authToken);


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @DELETE("api/barbershop/banners/{banner}")
    Call<Response> delete(@Header("Authorization") String authToken, @Path("banner") int banner);

    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @Multipart
    @POST("api/barbershop/banners")
    Call<UploadImageResponse> create(@Header("Authorization") String authToken, @Part MultipartBody.Part banner);
}
