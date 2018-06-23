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
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.Util;

import java.util.regex.Pattern;

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

        FontManager.setFont(txtIconCall, iconFont);

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


        Intent intent = new Intent(LoginActivity.this, SmsVerificationActivity.class);
        intent.putExtra("phone", phone);

        startActivity(intent);
        finish();
        return;
        /*
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LoginService webService = retrofit.create(LoginService.class);


        Call<LoginResponse> callWebservice =
                webService.login(new LoginRequest(phone));

        callWebservice.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {

                    if (!response.body().hasError()) {

                        // Login Successfull
                        // TODO go to verification activity
                        Log.i(TAG, "onResponse.SUCCESS: " + response.body().getMessage());

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
        });*/
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
