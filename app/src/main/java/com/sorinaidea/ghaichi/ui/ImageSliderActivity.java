package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.PhotoSliderAdapter;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mr-code on 6/17/2018.
 */

public class ImageSliderActivity extends ToolbarActivity {

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

        initToolbar("", true, false);

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            serviceId = Integer.parseInt(extras.getString(Util.COMMUNICATION_KEYS.SERVICE_ID));
            barbershopId = Integer.parseInt(extras.getString(Util.COMMUNICATION_KEYS.BARBERSHOP_ID));
            getServiceImages(serviceId, barbershopId);
        } else {
            finish();
        }


    }

    public void getServiceImages(int serviceId, int barbershopId) {
        API.getRetrofit(this).create(BarbershopServices.class)
                .serviceImages( barbershopId, serviceId)
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if (response.isSuccessful()) {
                            imageList.clear();
                            imageList.addAll(response.body());
                            initializeImageSlider(imageList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {

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

    private void initializeImageSlider(List<String> photos) {

        PhotoSliderAdapter adapter = new PhotoSliderAdapter(getApplicationContext(), photos);
        toolbarTitle.setText(String.format(App.LOCALE, "%d از %d", 1, imageList.size()));
        mPager.setAdapter(adapter);
        adapter.setImageOnCLickListener(view -> {
            if (!show) {
                runOnUiThread(this::showToolbar);
            } else {
                runOnUiThread(this::hideToolbar);
            }
            show = !show;
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                toolbarTitle.setText(String.format(App.LOCALE, "%d از %d", (position + 1), imageList.size()));
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
