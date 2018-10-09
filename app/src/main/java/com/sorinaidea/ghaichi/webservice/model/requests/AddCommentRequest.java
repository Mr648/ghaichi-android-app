package com.sorinaidea.ghaichi.webservice.model.requests;

import com.google.gson.annotations.SerializedName;

public class AddCommentRequest extends UserRequest {


    @SerializedName("barbershop_id")
    private int barbershopId;

    @SerializedName("comment_text")
    private String userComment;

    @SerializedName("comment_rating")
    private String userRating;


    public AddCommentRequest(String accessKey, int barbershopId, String userComment, String userRating){
        super(accessKey);
        this.barbershopId = barbershopId;
        this.userComment = userComment;
        this.userRating = userRating;
    }


}
