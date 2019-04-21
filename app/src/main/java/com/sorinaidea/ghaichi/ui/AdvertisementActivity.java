package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.AdvertisementAdapter;
import com.sorinaidea.ghaichi.models.BaseAdvertise;
import com.sorinaidea.ghaichi.ui.barbershop.activity.RequestAdvertisementActivity;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.AdvertiseServices;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertisementActivity extends ToolbarActivity {

    private RecyclerView recAdvertises;

    private FloatingActionButton fabAddAdvertise;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisment);
        initToolbar(R.string.toolbar_advertises, true, false);


        recAdvertises = findViewById(R.id.recAdvertises);
        recAdvertises.setNestedScrollingEnabled(false);

        fabAddAdvertise = findViewById(R.id.fabAddAdvertise);

        recAdvertises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        fabAddAdvertise.setOnClickListener(view -> {
            Intent intent = new Intent(AdvertisementActivity.this, RequestAdvertisementActivity.class);
            startActivity(intent);
        });

        getAdvertises();
    }


    private void getAdvertises() {
        showProgress();
        API.getRetrofit(this).create(AdvertiseServices.class)
                .advertises( ).enqueue(new Callback<List<BaseAdvertise>>() {
            @Override
            public void onResponse(Call<List<BaseAdvertise>> call, Response<List<BaseAdvertise>> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    try {
                        recAdvertises.setAdapter(new AdvertisementAdapter(Objects.requireNonNull(response.body()), AdvertisementActivity.this));
                    } catch (NullPointerException ignored) {
                        toast("خطا در خواندن داده‌ها");
                    }
                } else {
                    toast("خطا در فرمت داده‌ها");
                }
            }

            @Override
            public void onFailure(Call<List<BaseAdvertise>> call, Throwable t) {
                hideProgress();
                if (t instanceof IOException) {
                    toast(R.string.err_unable_to_connect_to_server);
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

}
