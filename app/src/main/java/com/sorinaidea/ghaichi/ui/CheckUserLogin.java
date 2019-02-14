package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import org.apache.commons.codec.binary.Hex;
import com.sorinaidea.ghaichi.util.GhaichiPrefrenceManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.UserProfileService;
import com.sorinaidea.ghaichi.webservice.model.responses.Response;

import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;

public class CheckUserLogin extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String accessKey = GhaichiPrefrenceManager
                .getDecryptedString(getApplicationContext(), Util.PREFRENCES_KEYS.USER_ACCESS_KEY, null);

        if (accessKey == null || accessKey.isEmpty()) {
            startActivity(new Intent(CheckUserLogin.this, LoginActivity.class));
            finish();
        } else {


            Log.d(Util.PREFRENCES_KEYS.USER_ACCESS_KEY, "asad");
//
//            Call<Response> info =
//                    API.getRetrofit()
//                            .create(UserProfileService.class)
//                            .validateToken(accessKey);
//
//
//            info.enqueue(new Callback<Response>() {
//                @Override
//                public void onResponse(Call<com.sorinaidea.ghaichi.webservice.model.responses.Response> call, retrofit2.Response<Response> response) {
////                    if (response.isSuccessful()) {
////                        if (response.body().hasError()) {
////                            GhaichiPrefrenceManager.removeKey(getApplicationContext(), Util.PREFRENCES_KEYS.USER_ACCESS_KEY);
////                            GhaichiPrefrenceManager.removeKey(getApplicationContext(), Util.md5(Util.PREFRENCES_KEYS.USER_ROLE));
////
////                            // Go To Login Activity
////                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
////                            finish();
////
////                        } else {
////                            String userType = GhaichiPrefrenceManager.getString(getApplicationContext(),
////                                    Util.md5(Util.PREFRENCES_KEYS.USER_ROLE)
////                                    , null);
////
////                            userType = Util.base64decode(userType, Util.PREFRENCES_KEYS.BASE_64_ENCODE_DECODE_COUNT);
////
////
////                            if (userType.equals(Util.CONSTANTS.ROLE_BARBERSHOP)) {
////                                startActivity(new Intent(SplashActivity.this, NewMainActivity.class));
////                                finish();
////                            } else if (userType.equals(Util.CONSTANTS.ROLE_NORMAL_USER)) {
////                                startActivity(new Intent(SplashActivity.this, NewMainActivity.class));
////                                finish();
////                            } else {
////                                finish();
////                            }
////                        }
////                    } else {
////                    }
//                }
//
//                @Override
//                public void onFailure(Call<com.sorinaidea.ghaichi.webservice.model.responses.Response> call, Throwable t) {
//
//                }
//            });

        }

    }


}
