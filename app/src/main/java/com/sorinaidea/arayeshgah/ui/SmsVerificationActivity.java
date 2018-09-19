package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.GhaichiPrefrenceManager;
import com.sorinaidea.arayeshgah.util.SmsListener;
import com.sorinaidea.arayeshgah.util.SmsReciver;
import com.sorinaidea.arayeshgah.util.SorinaApplication;
import com.sorinaidea.arayeshgah.util.Util;
import com.sorinaidea.arayeshgah.webservice.API;
import com.sorinaidea.arayeshgah.webservice.LoginService;
import com.sorinaidea.arayeshgah.webservice.model.requests.VerificationRequest;
import com.sorinaidea.arayeshgah.webservice.model.responses.VerificationResponse;

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

    private final static int TIME_TO_TRY_AGAIN_SECONDS = 10;

    private String phone;
    private Toolbar toolbar;

    private Button btnVerify;
    private TextInputLayout inputLayoutVerificationCode;
    private TextInputEditText edtVerificationCode;

    private Handler handler;


    View.OnClickListener verfiyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            submitForm(phone);
        }
    };

    View.OnClickListener changeNumberCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ViewCompat.setElevation(toolbar, Util.dp(5, SmsVerificationActivity.this));

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("دریافت کد فعالسازی");

        Bundle extras = getIntent().getExtras();

        if (extras != null && !extras.isEmpty()) {
            phone = extras.getString("phone");
        } else {
            finish();
        }


        btnVerify = (Button) findViewById(R.id.btnVerify);
        inputLayoutVerificationCode = (TextInputLayout) findViewById(R.id.inputLayoutVerificationCode);
        edtVerificationCode = (TextInputEditText) findViewById(R.id.edtVerificationCode);

        edtVerificationCode.setOnKeyListener((view, i, keyEvent) -> {
            if (btnVerify.isEnabled() && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                submitForm(phone);
            }
            return false;
        });

        btnVerify.setOnClickListener(verfiyClickListener);
//        btnVerify.setEnabled(false);


        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);


        FontManager.setFont(btnVerify, iranSans);
        FontManager.setFont(inputLayoutVerificationCode, iranSans);
        FontManager.setFont(edtVerificationCode, iranSans);
        FontManager.setFont(mTitle, iranSans);

        SmsReciver.bindListener(new SmsListener() {
            @Override
            public void onMessageReceived(String messageText) {
                Log.e("Text", messageText);
                edtVerificationCode.setText(messageText);
                btnVerify.setEnabled(true);
                btnVerify.animate().scaleXBy(1.1f);
            }
        });

    }

    private void sendVerificationCode(final String phone, final String verificationCode) {


        Retrofit retrofit = API.getRetrofit();

        LoginService webService = retrofit.create(LoginService.class);


        final String userType = Util.base64decode(
                GhaichiPrefrenceManager.getString(getApplicationContext(),
                        Util.md5(Util.PREFRENCES_KEYS.USER_ROLE)
                        , null
                ),
                Util.PREFRENCES_KEYS.BASE_64_ENCODE_DECODE_COUNT
        );

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
                            if (userType.equals(Util.CONSTANTS.ROLE_BARBERSHOP)) {
                                startActivity(new Intent(SmsVerificationActivity.this, BarberMainActivity.class));
                                finish();
                            } else if (userType.equals(Util.CONSTANTS.ROLE_NORMAL_USER)) {
                                startActivity(new Intent(SmsVerificationActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    } else {
                        Log.i(TAG, "onResponse.FAILURE: " + response.body().getMessage());
                    }

                } else {
                    Log.i(TAG, "onResponse.NULL: " + null);
                }
            }

            @Override
            public void onFailure(Call<VerificationResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
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
//        btnVerify.setEnabled(false);
        if (!validateVerificationCode()) {
            btnVerify.setEnabled(true);
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