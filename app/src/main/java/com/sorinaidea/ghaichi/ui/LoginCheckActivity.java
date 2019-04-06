package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.AuthService;
import com.sorinaidea.ghaichi.webservice.HttpCodes;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginCheckActivity extends ToolbarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_login);
        initToolbar("ورود به حساب کاربری", false, false);
        checkLogin();
    }

    private void checkLogin() {
        showProgressDialog("ورود", "در حال ورود به حساب کاربری", false);
        String accessKey = Auth.getAccessKey(this);
        if (accessKey != null && !accessKey.isEmpty()) {
            AuthService auth = API.getRetrofit().create(AuthService.class);
            auth.isLoggedIn(accessKey).enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    hideProgressDialog();
                    if (response.isSuccessful()) {
                        if (Auth.isBarbershop(LoginCheckActivity.this)) {
                            startActivity(new Intent(LoginCheckActivity.this, BarberMainActivity.class));
                            finish();
                        } else if (Auth.isUser(LoginCheckActivity.this)) {
                            startActivity(new Intent(LoginCheckActivity.this, NewMainActivity.class));
                            finish();
                        } else {
                            Auth.logout(LoginCheckActivity.this);
                            finish();
                        }
                    } else {
                        try {
                            if (Objects.requireNonNull(response.body()).hasError()) {
                                if (response.code() == HttpCodes.HTTP_UNAUTHORIZED) {
                                    confirmAlert("دسترسی غیر مجاز", Objects.requireNonNull(response.body()).getMessage(), R.drawable.ic_account_circle_white_24dp, R.color.colorRedAccent900, v -> Auth.getAccessKey(LoginCheckActivity.this));
                                } else if (response.code() == HttpCodes.HTTP_UNAUTHORIZED) {
                                    confirmAlert("ورود مجدد", Objects.requireNonNull(response.body()).getMessage(), R.drawable.ic_account_circle_white_24dp, R.color.colorAmberAccent900, v -> Auth.getAccessKey(LoginCheckActivity.this));
                                }
                            }
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                            actionAlert("ورود مجدد", "توکن دسترسی شما به اپلیکیشن منقضی شده است، لطفا بار دیگر وارد برنامه شوید.", R.drawable.ic_account_circle_white_24dp, R.color.colorAmberAccent900, v -> Auth.logout(LoginCheckActivity.this));
                        }

                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    hideProgressDialog();
                    if (t instanceof IOException) {
                        confirmAlert("قطع ارتباط", "خطا در اتصال به سرور", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark, "تلاش مجدد", v -> checkLogin(), "خروج", v -> finish());
                    }
                    toast("are are are");
                }
            });

        }

    }

}

