package com.sorinaidea.ghaichi.webservice;

import com.sorinaidea.ghaichi.models.Response;

import retrofit2.Call;
import retrofit2.http.POST;

public interface AuthService {

    @POST("api/system/auth/check")
    Call<Response> isLoggedIn();
}
