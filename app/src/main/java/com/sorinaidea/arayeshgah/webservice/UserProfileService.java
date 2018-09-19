package com.sorinaidea.arayeshgah.webservice;

import com.sorinaidea.arayeshgah.webservice.model.requests.EditProfileRequest;
import com.sorinaidea.arayeshgah.webservice.model.requests.LoginRequest;
import com.sorinaidea.arayeshgah.webservice.model.requests.VerificationRequest;
import com.sorinaidea.arayeshgah.webservice.model.responses.LoginResponse;
import com.sorinaidea.arayeshgah.webservice.model.responses.Response;
import com.sorinaidea.arayeshgah.webservice.model.responses.VerificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by mr-code on 6/17/2018.
 */

public interface UserProfileService {



    @POST("api/user/profile/update")
    Call<Response> update(@Body EditProfileRequest request);


}
