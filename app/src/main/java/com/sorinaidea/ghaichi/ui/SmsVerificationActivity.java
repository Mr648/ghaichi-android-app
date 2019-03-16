package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.util.SmsReceiver;
import com.sorinaidea.ghaichi.util.SorinaApplication;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.LoginService;
import com.sorinaidea.ghaichi.webservice.model.requests.LoginRequest;
import com.sorinaidea.ghaichi.webservice.model.requests.VerificationRequest;
import com.sorinaidea.ghaichi.webservice.model.responses.LoginResponse;
import com.sorinaidea.ghaichi.webservice.model.responses.VerificationResponse;

import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by mr-code on 6/17/2018.
 */

public class SmsVerificationActivity extends ToolbarActivity {


    private String phone;

    private Button btnVerify;

    private TextInputLayout inputLayoutVerificationCode;
    private TextInputEditText edtVerificationCode;
    private AlphaAnimation alphaAnimation = new AlphaAnimation(1F, 0.8F);

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

        initToolbar("دریافت کد فعالسازی", true, false);

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


        btnVerify = findViewById(R.id.btnVerify);

        inputLayoutVerificationCode = findViewById(R.id.inputLayoutVerificationCode);
        edtVerificationCode = findViewById(R.id.edtVerificationCode);

        btnVerify.setOnClickListener(view -> {
            action();
        });


        applyTextFont(btnVerify,
                inputLayoutVerificationCode,
                edtVerificationCode
        );


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
        showProgressDialog("بررسی", "در حال اعتبار سنجی کد تایید", false);

        callVerification.enqueue(new Callback<VerificationResponse>() {
            @Override
            public void onResponse(Call<VerificationResponse> call, Response<VerificationResponse> response) {
                hideProgressDialog();
                if (response.body() != null) {

                    if (!response.body().hasError()) {


                        Auth.loggedIn(SmsVerificationActivity.this, response.body());

                        // TODO Goto Other Part Of Program.
//                        if (userType.equals(Util.CONSTANTS.ROLE_BARBERSHOP)) {
                        startActivity(new Intent(SmsVerificationActivity.this, BarberMainActivity.class));
                        finish();
//                        } else if (userType.equals(Util.CONSTANTS.ROLE_NORMAL_USER)) {
//                            startActivity(new Intent(SmsVerificationActivity.this, NewMainActivity.class));
//                            finish();
//                        }
                    } else {
                        toast("پاسخی از سرور دریافت نشد.");
                        finish();
                    }
                } else {
                    toast("خطا در اعتبار سنجی");
                }
            }

            @Override
            public void onFailure(Call<VerificationResponse> call, Throwable t) {
                hideProgressDialog();
                if (t instanceof IOException)
                    toast("خطا در ارتباط با سرور");

                actionAlert("تایید ناموفق", "میخواهید مجددا امتحان کنید؟", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorAmberAccent900, view -> sendVerificationCode(phone, verificationCode));

            }
        });
    }

    private void sendSms(final String phone) {

        showProgressDialog("ارسال کد تایید", "در حال ارسال کد تایید", false);

        Retrofit retrofit = API.getRetrofit();
        LoginService webService = retrofit.create(LoginService.class);

        callWebservice =
                webService.login(new LoginRequest(phone));

        callWebservice.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    if (!response.body().hasError()) {
                        toast(String.format(new Locale("fa"), "%s %s %s.", "کد فعالسازی به شماره  " , phone , " ارسال شد"));
                    } else {
                        toast("خطا در ارسال اطلاعات");
                    }
                } else {
                    toast("پاسخی از سرور دریافت نشد.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideProgressDialog();
                if (t instanceof IOException)
                    toast("خطا در ارتباط با سرور");
                actionAlert("ارسال ناموفق", "کد جدید برای شما ارسال شود؟", R.drawable.ic_info, R.color.colorAmberAccent900, view -> sendSms(phone));
            }
        });
    }

    private boolean validateVerificationCode() {
        if (edtVerificationCode.getText().toString().trim().isEmpty()) {
            inputLayoutVerificationCode.setError(getString(R.string.err__empty__verfcode));
            requestFocus(edtVerificationCode);
            return false;
        } else if (!Pattern.matches(Util.CONSTANTS.REGEX_VERIFICATION_CODE, edtVerificationCode.getText().toString())) {
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

/*
*   String str = response.body().getUserRole();
                        Log.d("TAGGGGGGG", response.body().getUserRole());
*
*
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

*
* */