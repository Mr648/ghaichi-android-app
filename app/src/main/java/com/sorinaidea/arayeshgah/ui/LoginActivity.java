package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.GhaichiPrefrenceManager;
import com.sorinaidea.arayeshgah.util.SorinaApplication;
import com.sorinaidea.arayeshgah.util.Util;
import com.sorinaidea.arayeshgah.webservice.API;
import com.sorinaidea.arayeshgah.webservice.LoginService;
import com.sorinaidea.arayeshgah.webservice.model.requests.LoginRequest;
import com.sorinaidea.arayeshgah.webservice.model.responses.LoginResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by mr-code on 6/17/2018.
 */

public class LoginActivity extends AppCompatActivity {


    private Button btnSendCode;
    private TextInputEditText edtPhoneNumber;
    private TextView txtIconCall;
    private TextInputLayout inputLayoutPhoneNumber;

    private String TAG = "LoginActivity";
    private Toolbar toolbar;

    @Override
    protected void onRestart() {
        super.onRestart();
        if (SorinaApplication.hasAccessKey(getApplicationContext())) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SorinaApplication.hasAccessKey(getApplicationContext())) {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (SorinaApplication.hasAccessKey(getApplicationContext())) {
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide(); // TODO delete toolbar

        ViewCompat.setElevation(toolbar, Util.dp(5, LoginActivity.this));

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("ورود به حساب کاربری");

        btnSendCode = (Button) findViewById(R.id.btnSendCode);
        txtIconCall = (TextView) findViewById(R.id.txtIconCall);
        edtPhoneNumber = (TextInputEditText) findViewById(R.id.edtPhoneNumber);
        inputLayoutPhoneNumber = (TextInputLayout) findViewById(R.id.inputLayoutPhoneNumber);


        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });


        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.MATERIAL_ICONS);
        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        FontManager.setFont(txtIconCall, iconFont);
        FontManager.setFont(mTitle, iranSans);
        FontManager.setFont(btnSendCode, iranSans);
        FontManager.setFont(edtPhoneNumber, iranSans);
        FontManager.setFont(inputLayoutPhoneNumber, iranSans);

        edtPhoneNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    submitForm();
                }
                return false;
            }
        });

    }

    private void submitForm() {

        if (!validateUserPhone()) {
            return;
        }


        sendSms(edtPhoneNumber.getText().toString());
    }

    private void sendSms(final String phone) {

        Retrofit retrofit = API.getRetrofit();

        LoginService webService = retrofit.create(LoginService.class);


        Call<LoginResponse> callWebservice =
                webService.login(new LoginRequest(phone));

        callWebservice.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {

                    if (!response.body().hasError()) {

                        GhaichiPrefrenceManager.putString(getApplicationContext(),
                                Util.md5(Util.PREFRENCES_KEYS.USER_ROLE),
                                Util.base64encode(response.body().getUserRole(), Util.PREFRENCES_KEYS.BASE_64_ENCODE_DECODE_COUNT)
                        );

                        Intent intent = new Intent(LoginActivity.this, SmsVerificationActivity.class);
                        intent.putExtra("phone", phone);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.i(TAG, "onResponse.FAILURE: " + response.body().getMessage());
                    }
                } else {
                    Log.i(TAG, "onResponse.NULL: " + null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    private boolean validateUserPhone() {
        if (edtPhoneNumber.getText().toString().trim().isEmpty()) {
            inputLayoutPhoneNumber.setError(getString(R.string.err__empty__phone));
            requestFocus(edtPhoneNumber);
            return false;

        } else if (!Pattern.matches(Util.CONSTANTS.REGEX_PHONE, edtPhoneNumber.getText().toString())) {
            inputLayoutPhoneNumber.setError(getString((R.string.err__invalid__phone)));
            requestFocus(edtPhoneNumber);
            return false;

        } else {
            inputLayoutPhoneNumber.setErrorEnabled(false);

        }
        return true;
    }

    private void requestFocus(View view) {

        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

    }
}
