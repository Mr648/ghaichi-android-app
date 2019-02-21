package com.sorinaidea.ghaichi.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.AuthService;
import com.sorinaidea.ghaichi.webservice.HttpCodes;
import com.sorinaidea.ghaichi.webservice.model.responses.Response;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginCheckActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String authToken = Auth.getAccessKey(LoginCheckActivity.this);

        if (authToken == null) {
            Intent intent = new Intent(LoginCheckActivity.this, SplashActivity.class);
            intent.putExtra("IS_LOGGED_IN", false);
            startActivity(intent);
            finish();
            return;
        }


        AuthService auth = API.getRetrofit().create(AuthService.class);
        auth.isLoggedIn(authToken).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if (response.isSuccessful()) {
                    Intent intent = new Intent(LoginCheckActivity.this, SplashActivity.class);
                    intent.putExtra("IS_LOGGED_IN", true);
                    startActivity(intent);
                    finish();
                } else if (response.code() == HttpCodes.HTTP_UNAUTHORIZED || response.code() == HttpCodes.HTTP_BAD_REQUEST) {
                    Intent intent = new Intent(LoginCheckActivity.this, SplashActivity.class);
                    intent.putExtra("IS_LOGGED_IN", false);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                if (t instanceof IOException) {
                    call.clone();
                }
            }
        });


    }
}
