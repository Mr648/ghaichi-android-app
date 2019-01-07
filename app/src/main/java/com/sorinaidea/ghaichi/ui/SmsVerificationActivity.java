package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sorinaidea.ghaichi.R;
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

import java.util.logging.Logger;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by mr-code on 6/17/2018.
 */

public class SmsVerificationActivity extends AppCompatActivity {
    private String TAG = "SmsVerificationActivity";

    private final static int TIMEOUT_DURATION_MILLIS = 61000;
    private final static int COUNTDOWN_INTERVAL = 1000;

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

        timer = new CountDownTimer(TIMEOUT_DURATION_MILLIS, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                isTimerRunning = true;
                handler.post(() -> {
                    txtProgress.setText(Long.toString((millisUntilFinished / COUNTDOWN_INTERVAL) - 1));
                });
            }

            @Override
            public void onFinish() {
//                btnVerify.setEnabled(true);
                isTimerRunning = false;
            }
        };

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
            sendSms(phone);
        } else {
            finish();
        }

        relProgress.setVisibility(View.GONE);

        btnVerify = (Button) findViewById(R.id.btnVerify);

        inputLayoutVerificationCode = (TextInputLayout) findViewById(R.id.inputLayoutVerificationCode);
        edtVerificationCode = (TextInputEditText) findViewById(R.id.edtVerificationCode);

        edtVerificationCode.setOnKeyListener((view, i, keyEvent) -> {
            if (btnVerify.isEnabled() && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                action();
            }
            return false;
        });


        btnVerify.setOnClickListener(view -> {
            action();
        });


        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);


        FontManager.setFont(btnVerify, iranSans);
        FontManager.setFont(inputLayoutVerificationCode, iranSans);
        FontManager.setFont(edtVerificationCode, iranSans);
        FontManager.setFont(mTitle, iranSans);

        SmsReceiver.bindListener(messageText -> {
            edtVerificationCode.setText(messageText);
            action();
        });

    }

    private CountDownTimer timer;
    private boolean isTimerRunning = false;

    public void action() {
            submitForm(phone);
//        if (timer == null) {
////            btnVerify.setEnabled(true);
//            btnVerify.startAnimation(alphaAnimation);
//
//        } else if (timer != null) {
//            if (!isTimerRunning) {
//                timer.cancel();
//                timer = null;
//            }
//        }
    }

    private void sendVerificationCode(final String phone, final String verificationCode) {


        Retrofit retrofit = API.getRetrofit();

        LoginService webService = retrofit.create(LoginService.class);


        final String userType = "29c1cab0898b664bee9dd17ed48bbed0";

//                Util.base64decode(
//                GhaichiPrefrenceManager.getString(getApplicationContext(),
//                        Util.md5(Util.PREFRENCES_KEYS.USER_ROLE),
//                        Util.base64encode("29c1cab0898b664bee9dd17ed48bbed0", Util.PREFRENCES_KEYS.BASE_64_ENCODE_DECODE_COUNT)
//                ),
//                Util.PREFRENCES_KEYS.BASE_64_ENCODE_DECODE_COUNT
//        );

        Call<VerificationResponse> callWebservice =
                webService.verify(new VerificationRequest(phone, verificationCode, userType));

        callWebservice.enqueue(new Callback<VerificationResponse>() {
            @Override
            public void onResponse(Call<VerificationResponse> call, Response<VerificationResponse> response) {
                if (response.body() != null) {

                    if (!response.body().hasError()) {

                        // TODO Goto Other Part Of Program.
                        GhaichiPrefrenceManager.putString(getApplicationContext(),
                                Util.md5(Util.PREFRENCES_KEYS.USER_ACCESS_KEY),
                                Util.base64encode(response.body().getAccessKey(), Util.PREFRENCES_KEYS.BASE_64_ENCODE_DECODE_COUNT)
                        );

                        Log.i(TAG, "onResponse.SUCCESS: " + response.body().getMessage());
                        if (!response.body().isProfileCompleted()) {
                            Intent intent = new Intent(SmsVerificationActivity.this, PersonalInfoActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
//                            startActivity(new Intent(SmsVerificationActivity.this, BarberMainActivity.class));
//                            finish();
                            if (userType.equals(Util.CONSTANTS.ROLE_BARBERSHOP)) {
                                startActivity(new Intent(SmsVerificationActivity.this, BarberMainActivity.class));
                                finish();
                            } else if (userType.equals(Util.CONSTANTS.ROLE_NORMAL_USER)) {
                                startActivity(new Intent(SmsVerificationActivity.this, NewMainActivity.class));
                                finish();
                            }
                        }
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
        timer.start();

        Retrofit retrofit = API.getRetrofit();

        LoginService webService = retrofit.create(LoginService.class);


        Call<LoginResponse> callWebservice =
                webService.login(new LoginRequest(phone));

        callWebservice.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    if (!response.body().hasError()) {
                        Toast.makeText(SmsVerificationActivity.this, "Sent to " + phone, Toast.LENGTH_SHORT).show();
                        GhaichiPrefrenceManager.putString(getApplicationContext(),
                                Util.md5(Util.PREFRENCES_KEYS.USER_ROLE),
                                Util.base64encode(response.body().getUserRole(), Util.PREFRENCES_KEYS.BASE_64_ENCODE_DECODE_COUNT)
                        );
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
//            btnVerify.setEnabled(true);
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