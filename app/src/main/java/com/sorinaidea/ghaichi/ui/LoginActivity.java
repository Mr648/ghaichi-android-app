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
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.AesEncryptDecrypt;
import com.sorinaidea.ghaichi.util.AesEncryptionData;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.RSA;
import com.sorinaidea.ghaichi.util.SorinaApplication;
import com.sorinaidea.ghaichi.util.Util;

import java.util.regex.Pattern;

/**
 * Created by mr-code on 6/17/2018.
 */

public class LoginActivity extends ToolbarActivity {


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

    //kjsdflksdlfsldfksdf;jksf;l

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.MATERIAL_ICONS);
        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);


        alphaAnimation.setDuration(1000);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide(); // TODO delete toolbar

        ViewCompat.setElevation(toolbar, Util.dp(5, LoginActivity.this));

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("ورود به حساب کاربری");

        btnSendCode = findViewById(R.id.btnSendCode);
        txtIconCall = findViewById(R.id.txtIconCall);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        inputLayoutPhoneNumber = findViewById(R.id.inputLayoutPhoneNumber);


        btnSendCode.setOnClickListener(view -> {
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
            String phone;
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


}


/*

//codes about rsa encryption



            try {

                String fucked = new String(Base64.encode(RSA.encrypt(RSA.getPublic(), "adad"), Base64.DEFAULT));
                String fuckedAgain = new String(RSA.decrypt(RSA.getPrivate(),
                        Base64.decode(RSA.encrypt(RSA.getPublic(), fucked), Base64.DEFAULT)));

                Log.d("ENCRYPTED", fucked);
                Log.d("DECRYPTED", fuckedAgain);
//Util.md5();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("EXCEPTION-RSA", e.getMessage());
            }
            view.startAnimation(alphaAnimation);
//            submitForm();
            String str = "eyJpdiI6InVEY1hUTFZUQjI3bFBDN0RvXC9ETVR3PT0iLCJ2YWx1ZSI6IlZrbE5UeEhxeGlJOFNpMUVESzE4c1E9PSIsIm1hYyI6ImE1ZmMwOTNiZWRjYzJjYTJlYmY0MGEwZWJjZWEzMGFmODkyMTBiMGJjZTQwNDM4NGJlYTIxYzA4NDJkZmExZTAifQ";
            AesEncryptionData data = new Gson().fromJson(
                    new String(
                            Base64.decode(
                                    str
                                    , Base64.DEFAULT
                            )
                    ),
                    AesEncryptionData.class
            );
//
            try {
//                            String result = AesEncryptDecrypt.decrypt( new String(Base64.decode(Base64.decode(response.body().getExpiration(), Base64.DEFAULT), Base64.DEFAULT)).getBytes("UTF-8"), data, str);
                String encr = AesEncryptDecrypt.encrypt("XtTIUs54bthRm2Pw5sbWWxL6zZeeBgAiNxOcCGj+g0g=".getBytes("UTF-8"), "user");
                AesEncryptionData data2 = new Gson().fromJson(
                        new String(
                                Base64.decode(
                                        encr
                                        , Base64.DEFAULT
                                )
                        ),
                        AesEncryptionData.class
                );
                String result = AesEncryptDecrypt.decrypt("XtTIUs54bthRm2Pw5sbWWxL6zZeeBgAiNxOcCGj+g0g=".getBytes("UTF-8"), data2);

                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                Log.d("TAG", result);
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.d("EXCEPTION-CRYPT", ex.getMessage());
            }
* */