package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.ui.dialog.MessageDialog;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.GhaichiPrefrenceManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.UserProfileService;
import com.sorinaidea.ghaichi.webservice.model.responses.Response;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by mr-code on 6/17/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 1500;
    private static final int FINISH_DELAY = 3000;
    private TextView txtTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);
        FontManager.setFont(txtTitle, iranSans);

        if (!Util.isOnline(SplashActivity.this)) {
            MessageDialog commentDialog = new MessageDialog(SplashActivity.this, Util.CONSTANTS.NO_INTERNET_CONNECTION,
                    (view) -> {
                        SplashActivity.this.finish();
                    });
            commentDialog.show();
        } else {
            new CountDownTimer(SPLASH_TIME, 100) {
                @Override
                public void onTick(final long l) {
                }

                @Override
                public void onFinish() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }.start();
        }
    }
}
