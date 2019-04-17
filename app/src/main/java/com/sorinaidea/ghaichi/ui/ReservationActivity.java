package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.EmptyAdabper;
import com.sorinaidea.ghaichi.adapter.ReservationAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Reserve;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.UserReserveServices;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReservationActivity extends ToolbarActivity {

    private RecyclerView recReservations;
    private ReservationAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        if (!Auth.isUser(this)) {
            finish();
        }
        initToolbar("رزروها", true, false);

        recReservations = findViewById(R.id.recReservations);
        recReservations.setNestedScrollingEnabled(false);
        recReservations.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        getReservations();
    }


    private void getReservations() {
        showProgress();
        API.getRetrofit(this).create(UserReserveServices.class)
                .reserves()
                .enqueue(new Callback<List<Reserve>>() {
                    @Override
                    public void onResponse(Call<List<Reserve>> call, Response<List<Reserve>> response) {
                        hideProgress();
                        if (response.isSuccessful()) {
                            try {
                                adapter = new ReservationAdapter(Objects.requireNonNull(response.body()), ReservationActivity.this);
                                recReservations.setAdapter(adapter);
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
}
