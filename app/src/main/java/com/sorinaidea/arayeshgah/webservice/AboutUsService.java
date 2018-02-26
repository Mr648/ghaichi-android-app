package com.sorinaidea.arayeshgah.webservice;

import com.google.gson.Gson;
import com.sorinaidea.arayeshgah.model.AboutUs;
import com.sorinaidea.arayeshgah.model.MapMarker;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mr-code on 2/26/2018.
 */

public interface AboutUsService {
    @GET("/?get=aboutus")
    Call<AboutUs> getAboutUs();
}
