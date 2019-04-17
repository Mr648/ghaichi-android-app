package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.EmptyAdabper;
import com.sorinaidea.ghaichi.adapter.ReservationAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Reserve;
import com.sorinaidea.ghaichi.ui.ToolbarActivity;
import com.sorinaidea.ghaichi.util.JalaliDate;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.ReserveServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReservationActivity extends ToolbarActivity {

    private RecyclerView recReservations;
    private ReservationAdapter adapter;
    private List<Reserve> reserves;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        if (!Auth.isBarbershop(this)) {
            finish();
        }
        reserves = new ArrayList<>();
        initToolbar("رزروها", true, false);
        recReservations = findViewById(R.id.recReservations);
        recReservations.setNestedScrollingEnabled(false);
        recReservations.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new ReservationAdapter(reserves, ReservationActivity.this);
        getReservations();
    }


    private void getReservations() {

        Callback<List<Reserve>> callback = new Callback<List<Reserve>>() {
            @Override
            public void onResponse(Call<List<Reserve>> call, Response<List<Reserve>> response) {
                if (response.isSuccessful()) {
                    try {
                        reserves.clear();
                        reserves.addAll(Objects.requireNonNull(response.body()));
                        if (reserves.isEmpty()) {
                            recReservations.setAdapter(new EmptyAdabper(ReservationActivity.this));
                        } else {
                            recReservations.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    } catch (NullPointerException ex) {
                        recReservations.setAdapter(new EmptyAdabper(ReservationActivity.this));
                        toast("خطا در دریافت اطلاعات");
                    }
                } else {
                    toast("خطا در پاسخ سرور");
                }
            }

            @Override
            public void onFailure(Call<List<Reserve>> call, Throwable t) {
                if (t instanceof IOException) {
                    toast(R.string.err_unable_to_connect_to_server);
                }
            }
        };

        try {
            String date = Objects.requireNonNull(selectedDate).toString().replaceAll("/", "-");
            API.getRetrofit(this)
                    .create(ReserveServices.class)
                    .reserves(date, "APPROVED")
                    .enqueue(callback);
        } catch (NullPointerException ex) {
            API.getRetrofit(this)
                    .create(ReserveServices.class)
                    .reserves(null, "APPROVED")
                    .enqueue(callback);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_date, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_date:
                pickDate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    JalaliDate selectedDate;

    private void pickDate() {
        Calendar now = Calendar.getInstance();
        Calendar next2weeks = Calendar.getInstance();
        next2weeks.add(Calendar.DAY_OF_MONTH, 14);

        new DatePicker.Builder()
                .id(20)
                .minDate(now)
                .maxDate(next2weeks)
                .date(Calendar.getInstance())
                .build(
                        (id, calendar, day, month, year) -> {
                            selectedDate = new JalaliDate(year, month, day);
                            getReservations();
                        }
                )
                .show(getSupportFragmentManager(), "");
    }
}
