package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.ui.dialog.MessageDialog;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.GhaichiPrefrenceManager;
import com.sorinaidea.arayeshgah.util.Util;

import org.w3c.dom.Text;

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

        if (!Util.isOnline(SplashActivity.this)) {
            MessageDialog commentDialog = new MessageDialog(SplashActivity.this, Util.CONSTANTS.NO_INTERNET_CONNECTION);
            commentDialog.show();
            new CountDownTimer(FINISH_DELAY, 100) {
                @Override
                public void onTick(final long l) {
                    if (Util.isOnline(SplashActivity.this)) {
                        this.cancel();
                        if (commentDialog.isShowing()) commentDialog.dismiss();
                    }
                }

                @Override
                public void onFinish() {
                    SplashActivity.this.finish();

                }

            }.start();
        }


        txtTitle = (TextView) findViewById(R.id.txtTitle);

        new CountDownTimer(SPLASH_TIME, 100) {
            @Override
            public void onTick(final long l) {

            }

            @Override
            public void onFinish() {
                String isLoggedIn = GhaichiPrefrenceManager.getString(getApplicationContext(),
                        Util.md5(Util.PREFRENCES_KEYS.USER_ACCESS_KEY), null);

                if (isLoggedIn == null || isLoggedIn.isEmpty()) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {

                    String userType = GhaichiPrefrenceManager.getString(getApplicationContext(),
                            Util.md5(Util.PREFRENCES_KEYS.USER_ROLE)
                            , null);

                    userType = Util.base64decode(userType, Util.PREFRENCES_KEYS.BASE_64_ENCODE_DECODE_COUNT);


                    if (userType.equals(Util.CONSTANTS.ROLE_BARBERSHOP)) {
                        startActivity(new Intent(SplashActivity.this, BarberMainActivity.class));
                        finish();
                    } else if (userType.equals(Util.CONSTANTS.ROLE_NORMAL_USER)) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {
                        finish();
                    }

                }
            }
        }.start();
        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);


        FontManager.setFont(txtTitle, iranSans);
    }
}
