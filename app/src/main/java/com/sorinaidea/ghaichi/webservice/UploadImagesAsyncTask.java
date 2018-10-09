package com.sorinaidea.ghaichi.webservice;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.model.responses.Response;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by mr-code on 4/28/2018.
 */

public class UploadImagesAsyncTask extends AsyncTask<File, Void, Boolean> {


    private UploadImagesAsyncTaskCallback callback;

    private ProgressDialog dialog;
    private AppCompatActivity activity;

    public UploadImagesAsyncTask(UploadImagesAsyncTaskCallback callback, AppCompatActivity activity) {

        this.callback = callback;
        dialog = new ProgressDialog(activity);
        this.activity = activity;
    }

    boolean result;

    public interface UploadImagesAsyncTaskCallback {
        void onRequestComplete();
    }


    @Override
    protected void onPreExecute() {
        dialog.setMessage("در حال بارگزاری تصویر، لطفا صبر کنید!");
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(File... imageFiles) {

        if (imageFiles == null) {
            return false;
        }


        MultipartBody.Part image = MultipartBody.Part.createFormData("user_profile_image", imageFiles[0].getName(),
                RequestBody.create(MediaType.parse("image/*"), imageFiles[0]));

        MultipartBody.Part accessKey = MultipartBody.Part.createFormData("accessKey", Util.getAccessKey(activity));


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

        try {
            Thread.sleep(2500);
        } catch (InterruptedException ex) {

        }
        return result;
    }



    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        try {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {

        }

        callback.onRequestComplete();
    }
}
