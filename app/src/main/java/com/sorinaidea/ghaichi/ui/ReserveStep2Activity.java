package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.ReservationTurnSelectionAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.ServiceTurn;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mr-code on 6/17/2018.
 */

public class ReserveStep2Activity extends ToolbarActivity {


    RecyclerView recServices;

    int barbershopId;
    String selectedDate = null;
    String selectedStartTime = null;
    String selectedEndTime = null;
    String selectedServices = null;
    String servicesPrice = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_service_step_2);
        initToolbar("رزرو خدمات", true, false);
        recServices = findViewById(R.id.recServices);
        recServices.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            barbershopId = Integer.parseInt(extras.getString("BARBERSHOP_ID"));
            selectedStartTime = extras.getString("SELECTED_START_TIME");
            selectedEndTime = extras.getString("SELECTED_END_TIME");
            selectedDate = extras.getString("SELECTED_DATE");
            selectedServices = extras.getString("SELECTED_SERVICES");
            servicesPrice = extras.getString("SERVICES_PRICE");
            getTurns();
        } else {
            finish();
        }

    }

    private void getTurns() {
        showProgress();
        API.getRetrofit(this).create(BarbershopServices.class).turns(
                barbershopId,
                selectedStartTime,
                selectedEndTime,
                selectedDate,
                selectedServices
        ).enqueue(new Callback<List<ServiceTurn>>() {
            @Override
            public void onResponse(Call<List<ServiceTurn>> call, Response<List<ServiceTurn>> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    try {
                        recServices.setAdapter(new ReservationTurnSelectionAdapter(Objects.requireNonNull(response.body()), ReserveStep2Activity.this));
                        toast(response.body().get(0).toString());
                    } catch (NullPointerException ignored) {
                        toast("خطا در محاسبه نوبت‌ها");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ServiceTurn>> call, Throwable t) {
                hideProgress();
                if (t instanceof IOException) {
                    toast(R.string.err_unable_to_connect_to_server);
                }
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_reserve) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_reserve, menu);
        return true;
    }
}