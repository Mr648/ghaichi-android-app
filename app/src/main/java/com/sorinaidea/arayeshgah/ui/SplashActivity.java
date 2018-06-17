package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sorinaidea.arayeshgah.R;

/**
 * Created by mr-code on 6/17/2018.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new CountDownTimer(5000, 100) {
            @Override
            public void onTick(final long l) {

            }

            @Override
            public void onFinish() {
//                if (isAuthenticated) {
//                    startActivity(new Intent(SplashActivity.this, Login.class));
//                    finish();
//                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
//                }
            }
        }.start();

    }
}
