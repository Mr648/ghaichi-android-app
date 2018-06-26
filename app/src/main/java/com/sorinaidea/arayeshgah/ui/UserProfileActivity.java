package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.BarberShopProfileServiceAdapter;
import com.sorinaidea.arayeshgah.adapter.ImageSliderAdapter;
import com.sorinaidea.arayeshgah.model.Service;
import com.sorinaidea.arayeshgah.ui.dialog.CommentDialog;
import com.sorinaidea.arayeshgah.ui.dialog.GenderDialog;
import com.sorinaidea.arayeshgah.ui.dialog.InputDialog;
import com.sorinaidea.arayeshgah.ui.dialog.MessageDialog;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by mr-code on 6/17/2018.
 */

public class UserProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatImageView imgUserProfile;
    private AppCompatButton btnNameAndFamily;
    private AppCompatButton btnGender;
    private AppCompatButton btnPhone;
    private Typeface fontIransSans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        fontIransSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("حساب کاربری");

        imgUserProfile = (AppCompatImageView) findViewById(R.id.imgUserProfile);
        btnNameAndFamily = (AppCompatButton) findViewById(R.id.btnNameAndFamily);
        btnGender = (AppCompatButton) findViewById(R.id.btnGender);
        btnPhone = (AppCompatButton) findViewById(R.id.btnPhone);

        btnNameAndFamily.setText("نام کاربر");
        btnGender.setText("جنسیت کاربر");
        btnPhone.setText("09360835848");

        imgUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO open gallery to select profile photo!
            }
        });


        btnNameAndFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Predicate<String> validator = (x) -> !x.isEmpty();
                InputDialog dialog = new InputDialog(UserProfileActivity.this, btnNameAndFamily, null, R.drawable.ic_account_circle_white_24dp, R.string._hint_name_family, R.string._hint_name_family);
                dialog.show();
            }
        });

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Predicate<String> validator = (x) -> Pattern.matches(Util.CONSTANTS.REGEX_PHONE, x);
                InputDialog dialog = new InputDialog(UserProfileActivity.this, btnPhone, null, R.drawable.ic_dialpad_white_48dp, R.string._hint_phone, R.string._hint_phone);
                dialog.show();
            }
        });

        btnGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenderDialog genderDialog = new GenderDialog(UserProfileActivity.this, btnGender);
                genderDialog.show();
            }
        });

        FontManager.setFont(btnNameAndFamily, fontIransSans);
        FontManager.setFont(btnGender, fontIransSans);
        FontManager.setFont(btnPhone, fontIransSans);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
