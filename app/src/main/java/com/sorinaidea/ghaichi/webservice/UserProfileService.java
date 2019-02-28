package com.sorinaidea.ghaichi.webservice;

import com.sorinaidea.ghaichi.fast.BarbershopCard;
import com.sorinaidea.ghaichi.fast.ReagentCode;
import com.sorinaidea.ghaichi.fast.UserInfo;
import com.sorinaidea.ghaichi.fast.UserShortInfo;
import com.sorinaidea.ghaichi.webservice.model.requests.AddCommentRequest;
import com.sorinaidea.ghaichi.webservice.model.requests.EditProfileRequest;
import com.sorinaidea.ghaichi.webservice.model.responses.IsBookmarked;
import com.sorinaidea.ghaichi.webservice.model.responses.Response;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by mr-code on 6/17/2018.
 */

public interface UserProfileService {






























    @POST("api/user/profile/update")
    Call<Response> update(@Body EditProfileRequest request);

    @GET("api/user/profile/info")
    Call<UserInfo> info(@Query("accessKey") String accessKey);

    @FormUrlEncoded
    @POST("api/user/profile/logout")
    Call<Response> logout(@Field("accessKey") String accessKey);


    @GET("api/user/profile/info/short")
    Call<UserShortInfo> shortInfo(@Query("accessKey") String accessKey);

    @GET("api/user/profile/codes")
    Call<ReagentCode> codes(@Query("accessKey") String accessKey);


    @GET("api/user/profile/bookmarks")
    Call<List<BarbershopCard>> bookmarks(@Query("accessKey") String accessKey);

    @Multipart
    @POST("api/user/profile/upload/image")
    Call<Response> uploadUserImage(@Part MultipartBody.Part accessKey, @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("api/user/profile/validate/token")
    Call<Response> validateToken(@Field("accessKey") String accessKey);

    @POST("api/user/comment/create")
    Call<Response> addComment(@Body AddCommentRequest addCommentRequest);

    @FormUrlEncoded
    @POST("api/user/bookmark/createOrRemove")
    Call<Response> createOrRemove(@Query("accessKey") String accessKey, @Field("barbershop_id") String barbershopId);


    @GET("api/user/bookmark/exists")
    Call<IsBookmarked> bookmarkExists(@Query("accessKey") String accessKey, @Query("barbershop_id") String barbershopId);
}
