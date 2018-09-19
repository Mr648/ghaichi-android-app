package com.sorinaidea.arayeshgah.webservice;

import com.sorinaidea.arayeshgah.webservice.model.requests.LoginRequest;
import com.sorinaidea.arayeshgah.webservice.model.responses.LoginResponse;
import com.sorinaidea.arayeshgah.webservice.model.requests.VerificationRequest;
import com.sorinaidea.arayeshgah.webservice.model.responses.VerificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by mr-code on 6/17/2018.
 */

public interface LoginService {



    @POST("api/system/sms/send")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/system/sms/verify")
    Call<VerificationResponse> verify(@Body VerificationRequest request);

}
