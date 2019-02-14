package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.AesEncryptDecrypt;
import com.sorinaidea.ghaichi.util.AesEncryptionData;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.GhaichiPrefrenceManager;
import com.sorinaidea.ghaichi.util.SmsReceiver;
import com.sorinaidea.ghaichi.util.SorinaApplication;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.LoginService;
import com.sorinaidea.ghaichi.webservice.model.requests.LoginRequest;
import com.sorinaidea.ghaichi.webservice.model.requests.VerificationRequest;
import com.sorinaidea.ghaichi.webservice.model.responses.LoginResponse;
import com.sorinaidea.ghaichi.webservice.model.responses.VerificationResponse;

import org.apache.commons.codec.DecoderException;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by mr-code on 6/17/2018.
 */

public class SmsVerificationActivity extends AppCompatActivity {

    private String TAG = "SmsVerificationActivity";

    private String phone;
    private Toolbar toolbar;

    private Button btnVerify;
    private ProgressBar prgTimer;
    private RelativeLayout relProgress;
    private TextView txtProgress;

    private TextInputLayout inputLayoutVerificationCode;
    private TextInputEditText edtVerificationCode;
    private AlphaAnimation alphaAnimation = new AlphaAnimation(1F, 0.8F);

    private Handler handler;
    private Call<LoginResponse> callWebservice;


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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        alphaAnimation.setDuration(1000);

        handler = new Handler();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        prgTimer = (ProgressBar) findViewById(R.id.prgTimer);
        relProgress = (RelativeLayout) findViewById(R.id.relProgress);
        txtProgress = (TextView) findViewById(R.id.txtProgress);

        Drawable progressDrawable = prgTimer.getIndeterminateDrawable().mutate();
        progressDrawable.setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        prgTimer.setProgressDrawable(progressDrawable);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ViewCompat.setElevation(toolbar, Util.dp(5, SmsVerificationActivity.this));

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("دریافت کد فعالسازی");

        Bundle extras = getIntent().getExtras();

        if (extras != null && !extras.isEmpty()) {
            phone = extras.getString("phone");
            if (callWebservice != null) {
                if (!callWebservice.isExecuted() | callWebservice.isCanceled()) {
                    sendSms(phone);
                }
            } else {
                sendSms(phone);
            }
        } else {
            finish();
        }

        relProgress.setVisibility(View.GONE);

        btnVerify = (Button) findViewById(R.id.btnVerify);

        inputLayoutVerificationCode = (TextInputLayout) findViewById(R.id.inputLayoutVerificationCode);
        edtVerificationCode = (TextInputEditText) findViewById(R.id.edtVerificationCode);

        btnVerify.setOnClickListener(view -> {
            action();
        });

        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        FontManager.setFont(btnVerify, iranSans);
        FontManager.setFont(inputLayoutVerificationCode, iranSans);
        FontManager.setFont(edtVerificationCode, iranSans);
        FontManager.setFont(mTitle, iranSans);

//        if (Permi)
        SmsReceiver.bindListener(messageText -> {
            edtVerificationCode.setText(messageText);
            action();
        });
    }

    public void action() {
        submitForm(phone);
    }

    private void sendVerificationCode(final String phone, final String verificationCode) {


        Retrofit retrofit = API.getRetrofit();

        LoginService webService = retrofit.create(LoginService.class);

        Call<VerificationResponse> callVerification =
                webService.verify(new VerificationRequest(phone, verificationCode));

        callVerification.enqueue(new Callback<VerificationResponse>() {
            @Override
            public void onResponse(Call<VerificationResponse> call, Response<VerificationResponse> response) {
                if (response.body() != null) {

                    if (!response.body().hasError()) {


                        GhaichiPrefrenceManager.putEncryptedString(getApplicationContext(),
                                Util.PREFRENCES_KEYS.USER_ACCESS_KEY,
                                response.body().getAccessKey()
                        );

                        GhaichiPrefrenceManager.putEncryptedString(getApplicationContext(),
                                Util.PREFRENCES_KEYS.USER_ROLE,
                                response.body().getUserRole()
                        );

                        //9ed6e4ec89d8e923bd154329d3f395b0ffa8b69d2c69e081b934c75da24ffbb9
                        //9ed6e4ec89d8e923bd154329d3f395b0ffa8b69d2c69e081b934c75da24ffbb9
                        GhaichiPrefrenceManager.putEncryptedString(getApplicationContext(),
                                Util.PREFRENCES_KEYS.KEY_EXPIRATION,
                                response.body().getExpiration()
                        );

                        String str = response.body().getUserRole();
                        Log.d("TAGGGGGGG", response.body().getUserRole());


                        AesEncryptionData data = new Gson().fromJson(new String(Base64.decode(str, Base64.URL_SAFE)), AesEncryptionData.class);
//
                        try {
//                            String result = AesEncryptDecrypt.decrypt( new String(Base64.decode(Base64.decode(response.body().getExpiration(), Base64.DEFAULT), Base64.DEFAULT)).getBytes("UTF-8"), data, str);
                            String result = AesEncryptDecrypt.decrypt(new String(Base64.decode("rOSGWR1lsu8+yX1JkAaFlDa1/1YH+p4L0NkJ8dpfMGY=", Base64.DEFAULT)).getBytes("UTF-8"), data);
                            Toast.makeText(SmsVerificationActivity.this, result, Toast.LENGTH_LONG).show();
                            Log.d("TAG", result);
                        } catch (NoSuchPaddingException | NoSuchAlgorithmException | DecoderException | UnsupportedEncodingException | InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException ex) {
                            ex.printStackTrace();
                            Log.d("EXCEPTION", ex.getMessage());
                        }


                        // TODO Goto Other Part Of Program.
//                        if (userType.equals(Util.CONSTANTS.ROLE_BARBERSHOP)) {
                            startActivity(new Intent(SmsVerificationActivity.this, BarberMainActivity.class));
                            finish();
//                        } else if (userType.equals(Util.CONSTANTS.ROLE_NORMAL_USER)) {
//                            startActivity(new Intent(SmsVerificationActivity.this, NewMainActivity.class));
//                            finish();
//                        }
                    } else {
                        Log.i(TAG, "onResponse.FAILURE: " + response.body().getMessage());
                        finish();
                    }
                } else {
                    Log.i(TAG, "onResponse.NULL: " + null);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<VerificationResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                finish();
            }
        });
    }

    private void sendSms(final String phone) {

        relProgress.setVisibility(View.VISIBLE);

        Retrofit retrofit = API.getRetrofit();
        LoginService webService = retrofit.create(LoginService.class);

        callWebservice =
                webService.login(new LoginRequest(phone));

        callWebservice.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    if (!response.body().hasError()) {
                        Toast.makeText(SmsVerificationActivity.this, "Sent to " + phone, Toast.LENGTH_SHORT).show();
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
                Logger.getLogger("").info("");
            }
        });
    }

    private boolean validateVerificationCode() {
        if (edtVerificationCode.getText().toString().trim().isEmpty()) {
            inputLayoutVerificationCode.setError(getString(R.string.err__empty__verfcode));
            requestFocus(edtVerificationCode);
            return false;
        } else if (!Pattern.matches(Util.CONSTANTS.REGEX_VERIFICATIONCODE, edtVerificationCode.getText().toString())) {
            inputLayoutVerificationCode.setError(getString((R.string.err__invalid__verfcode)));
            requestFocus(edtVerificationCode);
            return false;
        } else {
            inputLayoutVerificationCode.setErrorEnabled(false);
        }
        return true;
    }

    private void submitForm(final String phone) {
        if (!validateVerificationCode()) {
            return;
        }
        sendVerificationCode(phone, edtVerificationCode.getText().toString());
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SmsVerificationActivity.this, LoginActivity.class);
        intent.putExtra("phone", phone);
        startActivity(intent);
        finish();
    }
}