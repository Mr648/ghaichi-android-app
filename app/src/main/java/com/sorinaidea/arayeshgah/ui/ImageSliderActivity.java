package com.sorinaidea.arayeshgah.ui;

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
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.BarberShopProfileServiceAdapter;
import com.sorinaidea.arayeshgah.adapter.ImageSliderAdapter;
import com.sorinaidea.arayeshgah.adapter.PhotoSliderAdapter;
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

public class ImageSliderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mPager;

    private static int currentPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mPager = (ViewPager) findViewById(R.id.pager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        initializeImageSlider();
    }


    private static final List<String> images = Arrays.asList(
            "1",
            "2",
            "3",
            "4",
            "5"
    );

    private ArrayList<String> imageList = new ArrayList<>();

    private void hideToolbar() {
        toolbar.animate().translationY(-256).setInterpolator(new AccelerateInterpolator()).start();
    }

    private void showToolbar() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
    }

    private boolean show = false;

    private void initializeImageSlider() {

        imageList.addAll(images);
        PhotoSliderAdapter adapter = new PhotoSliderAdapter(getApplicationContext(), imageList);
        getSupportActionBar().setTitle(1 + " از " + images.size());
        mPager.setAdapter(adapter);
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
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setTitle((position + 1) + " از " + images.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
