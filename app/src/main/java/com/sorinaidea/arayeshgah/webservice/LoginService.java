package com.sorinaidea.arayeshgah.webservice;

import com.sorinaidea.arayeshgah.webservice.model.LoginRequest;
import com.sorinaidea.arayeshgah.webservice.model.LoginResponse;
import com.sorinaidea.arayeshgah.webservice.model.VerificationRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by mr-code on 6/17/2018.
 */

public interface LoginService {
    @POST("sendSms")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("verifyCode")
    Call<LoginResponse> verify(@Body VerificationRequest request);

}
