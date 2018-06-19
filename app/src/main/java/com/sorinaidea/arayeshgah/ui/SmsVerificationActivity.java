package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.util.Util;

import java.util.regex.Pattern;

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

        edtVerificationCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    submitForm(phone);
                }
                return false;
            }
        });

        btnVerify.setOnClickListener(verfiyClickListener);


    }

    private void sendVerificationCode(final String phone, final String verificationCode) {


        Intent intent = new Intent(SmsVerificationActivity.this, PersonalInfoActivity.class);
        startActivity(intent);

        finish();

        /*
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LoginService webService = retrofit.create(LoginService.class);


        Call<LoginResponse> callWebservice =
                webService.verify(new VerificationRequest(phone, verificationCode));

        callWebservice.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {

                    if (!response.body().hasError()) {

                        // TODO Goto Other Part Of Program.
                        getApplicationContext().getSharedPreferences("userConfiguration", MODE_PRIVATE).edit().putString(
                                "authKey",
                                response.body().getAuthKey()
                        );

                        Log.i(TAG, "onResponse.SUCCESS: " + response.body().getMessage());
                        Intent intent = new Intent(SmsVerificationActivity.this, PersonalInfoActivity.class);
                        startActivity(intent);

                        finish();
                    } else {
                        timer.cancel();
                        Log.i(TAG, "onResponse.FAILURE: " + response.body().getMessage());
                    }

                } else {
                    timer.cancel();
                    Log.i(TAG, "onResponse.NULL: " + null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
                timer.cancel();
            }
        });*/
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
        btnVerify.setEnabled(false);
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