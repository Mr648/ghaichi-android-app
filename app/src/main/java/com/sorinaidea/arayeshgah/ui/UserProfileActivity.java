package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import com.sorinaidea.arayeshgah.ui.dialog.MessageDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by mr-code on 6/17/2018.
 */

public class UserProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatImageView imgUserProfile;
    private TextView txtName;
    private TextView txtFamily;
    private TextView txtGender;
    private LinearLayout lnrEditProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("حساب کاربری");

        imgUserProfile = (AppCompatImageView) findViewById(R.id.imgUserProfile);
        txtName = (TextView) findViewById(R.id.txtName);
        txtFamily = (TextView) findViewById(R.id.txtFamily);
        txtGender = (TextView) findViewById(R.id.txtGender);
        lnrEditProfile = (LinearLayout) findViewById(R.id.lnrEditProfile);


        imgUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO open gallery to select profile photo!
            }
        });

        lnrEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

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
