package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.sorinaidea.ghaichi.R;

/**
 * Created by mr-code on 6/17/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        WebView web = findViewById(R.id.webView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            web.setBackgroundColor(getResources().getColor(R.color.colorGraySplash, getTheme()));
        } else {
            web.setBackgroundColor(getResources().getColor(R.color.colorGraySplash));
        }
        web.loadUrl("file:///android_asset/htmls/gif.html");

        new CountDownTimer(SPLASH_TIME, 100) {
            @Override
            public void onTick(final long l) {
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, ActivityIntro.class));
                finish();
            }
        }.start();
    }
}
