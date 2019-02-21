package com.sorinaidea.ghaichi.webservice.image;

import java.io.File;

public interface ImageUploader {
    boolean isDone();
    boolean upload();
}

    /*

    MultipartBody.Part image = MultipartBody.Part.createFormData("user_profile_image", imageFiles[0].getName(),
            RequestBody.create(MediaType.parse("image/*"), imageFiles[0]));

    MultipartBody.Part accessKey = MultipartBody.Part.createFormData("accessKey", Auth.getAccessKey(activity));


    Call<Response> uploadRecognitionImages = API.getRetrofit().create(UserProfileService.class).uploadUserImage(accessKey, image);


        uploadRecognitionImages.enqueue(new Callback<Response>() {
@Override
public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
        if (response.body() != null) {
        if (!response.body().hasError()) {
        Log.d("ERROR_IMAGE", response.body().getMessage());
        }
        }
        Log.d("ERROR_IMAGE", "RESPONSE NULL");
        }

@Override
public void onFailure(Call<Response> call, Throwable t) {
        Log.d("ERROR_IMAGE", "RESPONSE FAILED");
        }
        });

        */