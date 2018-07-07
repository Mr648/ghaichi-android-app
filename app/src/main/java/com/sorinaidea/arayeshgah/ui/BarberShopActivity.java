package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.BarberShopProfileServiceAdapter;
import com.sorinaidea.arayeshgah.adapter.ImageSliderAdapter;
import com.sorinaidea.arayeshgah.adapter.ItemOffsetDecoration;
import com.sorinaidea.arayeshgah.layout.UserReservationFragment;
import com.sorinaidea.arayeshgah.model.Service;
import com.sorinaidea.arayeshgah.ui.dialog.CommentDialog;
import com.sorinaidea.arayeshgah.ui.dialog.MessageDialog;
import com.sorinaidea.arayeshgah.ui.dialog.TransactionDialog;
import com.sorinaidea.arayeshgah.util.FontManager;

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
//    private AppCompatImageView imgComment;
    private TextView txtAddress;
    private TextView txtRating;
    private TextView txtName;
    private TextView txtDescription;
    private RelativeLayout relativeLayout;
    private RatingBar ratingBar;
    private RecyclerView recServices;
    private Typeface fontIranSans;
    private static int currentPage = 0;
    private static final int NUM_COLUMNS = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbershop);

        scrViewRoot = (ScrollView) findViewById(R.id.scrViewRoot);
        recServices = (RecyclerView) findViewById(R.id.recServices);

        recServices.setFocusable(false);

        scrViewRoot.fullScroll(ScrollView.FOCUS_UP);
        scrViewRoot.smoothScrollTo(0, 0);


        fontIranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        imgLogo = (AppCompatImageView) findViewById(R.id.imgLogo);
//        imgComment = (AppCompatImageView) findViewById(R.id.imgComment);

        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtRating = (TextView) findViewById(R.id.txtRating);
        txtName = (TextView) findViewById(R.id.txtName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);


//        recServices.setLayoutManager(new GridLayoutManager(BarberShopActivity.this, 2, GridLayoutManager.VERTICAL, true));
        recServices.setLayoutManager(new GridLayoutManager(getApplicationContext(), NUM_COLUMNS, GridLayoutManager.VERTICAL, true));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen._4dp);
        recServices.addItemDecoration(itemDecoration);
        recServices.setAdapter(new BarberShopProfileServiceAdapter(initServices(), BarberShopActivity.this));
        recServices.setNestedScrollingEnabled(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initializeImageSlider();
        ratingBar.setRating(3.5f);
        ratingBar.setIsIndicator(true);

        txtRating.setText(String.format("%.1f", 3.5f));
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommentsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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


        FontManager.setFont(txtAddress, fontIranSans);
        FontManager.setFont(txtRating, fontIranSans);
        FontManager.setFont(txtName, fontIranSans);
        FontManager.setFont(txtDescription, fontIranSans);
        FontManager.setFont(ratingBar, fontIranSans);


    }

    private ArrayList<Service> initServices() {
        ArrayList<Service> services = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            services.add(new Service("خدمت #" + i));
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
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "آرایشگاه تست";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "به اشتراک گذاری با"));
        } else if (id == R.id.action_bookmark) {

        } else if (id == R.id.action_reserve) {
            Intent intent = new Intent(BarberShopActivity.this, ReserveActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_route) {
            Intent intent = new Intent(BarberShopActivity.this, ShowDirectionActivity.class);
            startActivity(intent);
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
