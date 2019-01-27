package com.sorinaidea.ghaichi.ui;

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
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.SorinaApplication;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.util.access.Access;

import java.util.regex.Pattern;

/**
 * Created by mr-code on 6/17/2018.
 */

public class LoginActivity extends AppCompatActivity {


    private String TAG = LoginActivity.class.getSimpleName();

    private Button btnSendCode;
    private Toolbar toolbar;
    private TextView txtIconCall;
    private TextInputEditText edtPhoneNumber;
    private TextInputLayout inputLayoutPhoneNumber;

    private AlphaAnimation alphaAnimation = new AlphaAnimation(1F, 0.8F);

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

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.MATERIAL_ICONS);
        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);


        alphaAnimation.setDuration(1000);



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


        btnSendCode.setOnClickListener(view -> {
            view.startAnimation(alphaAnimation);
            submitForm();
        });




        FontManager.setFont(txtIconCall, iconFont);
        FontManager.setFont(mTitle, iranSans);
        FontManager.setFont(btnSendCode, iranSans);
        FontManager.setFont(edtPhoneNumber, iranSans);
        FontManager.setFont(inputLayoutPhoneNumber, iranSans);

        checkExtras();

        edtPhoneNumber.setOnKeyListener((view, i, keyEvent) ->

        {
            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                submitForm();
            }
            return false;
        });

    }

    private void checkExtras() {

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            String phone = null;
            try {
                phone = extras.getString("phone");
            } catch (Exception e) {
                phone = "";
            }
            edtPhoneNumber.setText(phone);
        }
    }

    private void submitForm() {

        if (!validateUserPhone()) {
            return;
        }
        Intent intent = new Intent(LoginActivity.this, SmsVerificationActivity.class);
        intent.putExtra("phone", edtPhoneNumber.getText().toString());
        startActivity(intent);
        finish();
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
