package com.sorinaidea.arayeshgah.webservice;

import com.sorinaidea.arayeshgah.webservice.model.LoginRequest;
import com.sorinaidea.arayeshgah.webservice.model.LoginResponse;
import com.sorinaidea.arayeshgah.webservice.model.VerificationRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mr-code on 6/17/2018.
 */

public interface GetBarberShopsService {
    @POST("barbershops/{filter}")
    Call<LoginResponse> get(@Path("filter") String filter);

    @POST("barbershops/{id}")
    Call<LoginResponse> getOne(@Path("id") int id);
}
