package com.sorinaidea.arayeshgah.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.ImageSliderAdapter;
import com.sorinaidea.arayeshgah.layout.UserReservationFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by mr-code on 6/17/2018.
 */

public class BarberShopActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mPager;
    private CircleIndicator indicator;
    private AppCompatImageView imgLogo;
    private AppCompatImageView imgComment;
    private TextView txtAddress;
    private TextView txtRating;
    private TextView txtName;
    private TextView txtDescription;
    private RatingBar ratingBar;
    private RecyclerView recServices;

    private static int currentPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbershop);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        imgLogo = (AppCompatImageView) findViewById(R.id.imgLogo);
        imgComment = (AppCompatImageView) findViewById(R.id.imgComment);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtRating = (TextView) findViewById(R.id.txtRating);
        txtName = (TextView) findViewById(R.id.txtName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        recServices = (RecyclerView) findViewById(R.id.recServices);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initializeImageSlider();
 ratingBar.setIsIndicator(true);
 ratingBar.setRating(2.5f);

        imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Comment Here
            }
        });
    }

    private static final List<String> images = Arrays.asList(
            "asdasd",
            "asdasd",
            "asdasd",
            "asdasd"
    );

    private ArrayList<String> imageList = new ArrayList<>();

    private void initializeImageSlider() {

        imageList.addAll(images);
        mPager.setAdapter(new ImageSliderAdapter(getApplicationContext(), imageList));
        indicator.setViewPager(mPager);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.size()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_share) {

        } else if (id == R.id.action_bookmark) {

        } else if (id == R.id.action_reserve) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_barbershop, menu);
        return true;
    }
}
