package com.sorinaidea.arayeshgah.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.BarberShopProfileServiceAdapter;
import com.sorinaidea.arayeshgah.adapter.ImageSliderAdapter;
import com.sorinaidea.arayeshgah.layout.UserReservationFragment;
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

public class BarberShopActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mPager;
    private ScrollView scrViewRoot;
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
        scrViewRoot = (ScrollView) findViewById(R.id.scrViewRoot);


        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtRating = (TextView) findViewById(R.id.txtRating);
        txtName = (TextView) findViewById(R.id.txtName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        recServices = (RecyclerView) findViewById(R.id.recServices);
        recServices.setLayoutManager(new GridLayoutManager(BarberShopActivity.this, 2, GridLayoutManager.VERTICAL, true));
        recServices.setAdapter(new BarberShopProfileServiceAdapter(initServices(), BarberShopActivity.this));
        recServices.setNestedScrollingEnabled(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initializeImageSlider();
        ratingBar.setRating(3.5f);
        ratingBar.setIsIndicator(true);
        txtRating.setText(String.format("%.1f", 3.5f));
        scrViewRoot.scrollTo(0, 0);
        imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentDialog commentDialog = new CommentDialog(BarberShopActivity.this);
                commentDialog.show();
            }
        });

        txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog commentDialog = new MessageDialog(BarberShopActivity.this);
                commentDialog.show();
            }
        });


        scrViewRoot.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrViewRoot.getScrollY(); // For ScrollView
                int scrollX = scrViewRoot.getScrollX(); // For HorizontalScrollView

                if (scrollY <= 0) {
                    showToolbar();
                }
                if (scrollY > 100) {
                    hideToolbar();
                }
                // DO SOMETHING WITH THE SCROLL COORDINATES
            }
        });
    }

    private ArrayList<Service> initServices() {
        ArrayList<Service> services = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            services.add(new Service("Service #" + i));
        }
        return services;
    }

    private static final List<String> images = Arrays.asList(
            "asdasd",
            "asdasd",
            "asdasd",
            "asdasd"
    );

    private ArrayList<String> imageList = new ArrayList<>();

    private void hideToolbar() {
        toolbar.animate().translationY(-256).setInterpolator(new AccelerateInterpolator()).start();
//        if (getSupportActionBar().isShowing()){
//            getSupportActionBar().hide();
//        }
    }

    private void showToolbar() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
    }

    private void initializeImageSlider() {

        imageList.addAll(images);
        ImageSliderAdapter adapter = new ImageSliderAdapter(getApplicationContext(), imageList);
        mPager.setAdapter(adapter);
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


        adapter.setImageOnCLickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!show) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToolbar();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideToolbar();
                        }
                    });
                }
                show = !show;
            }
        });

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

    private boolean show = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_share) {

        } else if (id == R.id.action_bookmark) {

        } else if (id == R.id.action_reserve) {

        } else if (id == R.id.action_route) {

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
