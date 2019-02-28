package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.PhotoSliderAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.fast.Photo;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mr-code on 6/17/2018.
 */

public class ImageSliderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mPager;

    private int serviceId;
    private int barbershopId;
    private ArrayList<String> imageList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        toolbar = findViewById(R.id.toolbar);
        mPager = findViewById(R.id.pager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            serviceId = Integer.parseInt(extras.getString(Util.COMMUNICATION_KEYS.SERVICE_ID));
            barbershopId = Integer.parseInt(extras.getString(Util.COMMUNICATION_KEYS.BARBERSHOP_ID));
            Log.d("PHOTOS", serviceId + " :: " + barbershopId);
            getServiceImages(serviceId, barbershopId);
        } else {
            finish();
        }



    }

    public void  getServiceImages(int serviceId, int barbershopId) {

        final List<Photo> photos = new ArrayList<>();
        Call<List<Photo>> serviceImages =
                API.getRetrofit()
                        .create(BarbershopServices.class)
                        .serviceImages(barbershopId, serviceId, Auth.getAccessKey(getApplicationContext()));

        serviceImages.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.body() != null) {
                    initializeImageSlider(response.body());
                    Log.d("PHOTOS", Arrays.toString(photos.toArray()));
                }else{
                    Log.d("PHOTOS_ERR", serviceId + " :: " + barbershopId);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {

            }
        });

    }

    private void hideToolbar() {
        toolbar.animate().translationY(-256).setInterpolator(new AccelerateInterpolator()).start();
    }

    private void showToolbar() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
    }

    private boolean show = false;

    private void initializeImageSlider(List<Photo> photos) {

        for (Photo photo :
                photos) {
            imageList.add(photo.getPath());
        }

        PhotoSliderAdapter adapter = new PhotoSliderAdapter(getApplicationContext(), imageList);
        getSupportActionBar().setTitle(1 + " از " + imageList.size());
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
                getSupportActionBar().setTitle((position + 1) + " از " + imageList.size());
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
