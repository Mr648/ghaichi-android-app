package com.sorinaidea.arayeshgah.webservice;

import com.sorinaidea.arayeshgah.model.MapMarker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mr-code on 2/7/2018.
 */

public interface MapBuilderWebService {
    @GET("/?get=markers")
    Call<List<MapMarker>> listMarkers();
}
