package com.sorinaidea.ghaichi.webservice;

import com.sorinaidea.ghaichi.models.Response;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {


    @Headers({
            "User-Agent: GHAICHI-APPLICATION-USER",
    })
    @POST("api/system/auth/check")
    Call<Response> isLoggedIn(@Header("Authorization") String authToken);
}
