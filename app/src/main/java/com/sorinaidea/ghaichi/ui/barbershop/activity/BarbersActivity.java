package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.EmptyAdabper;
import com.sorinaidea.ghaichi.adapter.barbershop.BarberInfoAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Barber;
import com.sorinaidea.ghaichi.ui.ToolbarActivity;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.HttpCodes;
import com.sorinaidea.ghaichi.webservice.barbershop.BarberServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarbersActivity extends ToolbarActivity {

    private RecyclerView recBarbers;
    private FloatingActionButton fabAddBarber;
    ArrayList<Barber> barbers;
    private BarberInfoAdapter adapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbers);
        barbers = new ArrayList<>();
        initToolbar(R.string.toolbar_manage_barbers, true, false);

        recBarbers = findViewById(R.id.recBanners);

        fabAddBarber = findViewById(R.id.fabAddBarber);
        fabAddBarber.setOnClickListener((view) -> {
            Intent intent = new Intent(BarbersActivity.this, AddBarberActivity.class);
            startActivityForResult(intent, AddBarberActivity.ADD_BARBER_REQUEST);
        });

        if (savedInstanceState == null || !savedInstanceState.containsKey("barbers")) {
            initBarbers();
        } else {
            barbers = savedInstanceState.getParcelableArrayList("barbers");
        }

        recBarbers.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    BarberInfoAdapter.BarberClickListener clickListener = new BarberInfoAdapter.BarberClickListener() {
        @Override
        public void update(Barber barber) {
            Intent intent = new Intent(BarbersActivity.this, AddBarberActivity.class);
            intent.putExtra("BARBER", barber);
            startActivityForResult(intent, AddBarberActivity.UPDATE_BARBER_REQUEST);
        }

        @Override
        public void delete(Barber barber) {
            deleteBarber(barber);
        }
    };


    public void deleteBarber(Barber barber) {
        confirmAlert("هشدار", String.format(new Locale("fa"), "%s %s %s?", "آیا آرایشگر", String.format(new Locale("fa"), "%s %s", barber.getName(), barber.getFamily()), "حذف شود"), R.drawable.ic_delete_white_24dp, R.color.colorRedAccent200, view -> {
            showProgressDialog("حذف آرایشگر", "در حال حذف آرایشگر", false);
            BarberServices service = API.getRetrofit().create(BarberServices.class);
            service.delete(Auth.getAccessKey(this), barber.getId()).enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
                @Override
                public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, Response<com.sorinaidea.ghaichi.models.Response> response) {
                    hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {
                            com.sorinaidea.ghaichi.models.Response res = response.body();
                            Objects.requireNonNull(res);
                            actionAlert
                                    ("عملیات موفق", res.getMessage(), R.drawable.ic_done_white_24dp, R.color.colorGreenAccent200, view -> initBarbers());
                        } catch (NullPointerException ex) {
                            toast("پاسخی از سمت سرور دریافت نشد.");
                        }
                    } else {
                        toast("خطا در حذف آرایشگر");
                    }
                }

                @Override
                public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                    hideProgressDialog();
                    if (t instanceof IOException) {
                        toast("خطا در اتصال به سرور");
                        return;
                    }
                    toast("خطا در حذف آرایشگر");
                }
            });
        });
    }

    public ArrayList<Barber> initBarbers() {


        String authToken = Auth.getAccessKey(BarbersActivity.this);


        if (authToken != null) {

            showProgressDialog(null, "در حال دریافت لیست آرایشگران", false);

            BarberServices categoryServices = API.getRetrofit().create(BarberServices.class);

            categoryServices.barbers(authToken).enqueue(new Callback<List<Barber>>() {
                @Override
                public void onResponse(Call<List<Barber>> call, Response<List<Barber>> response) {
                    if (response.isSuccessful()) {
                        barbers.clear();
                        barbers.addAll(response.body());
                        if (!barbers.isEmpty()) {
                            adapter = new BarberInfoAdapter(barbers, BarbersActivity.this, clickListener);
                            recBarbers.setAdapter(adapter);
                        } else {
                            recBarbers.setAdapter(new EmptyAdabper(BarbersActivity.this));
                        }
                    } else if (response.code() == HttpCodes.HTTP_NOT_FOUND) {
                        recBarbers.setAdapter(new EmptyAdabper(BarbersActivity.this));
                    }
                    hideProgressDialog();
                }

                @Override
                public void onFailure(Call<List<Barber>> call, Throwable t) {
                    if (t instanceof IOException) {
                        toast(R.string.err_unable_to_connect_to_server);
                    }
                    logVerbose(t.getMessage(), t);
                    hideProgressDialog();
                }
            });

        } else {
            Toast.makeText(BarbersActivity.this, "آرایشگر یافت نشد.", Toast.LENGTH_SHORT).show();
        }

        return barbers;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("barbers", barbers);
        super.onSaveInstanceState(outState);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddBarberActivity.ADD_BARBER_REQUEST
                || requestCode == AddBarberActivity.UPDATE_BARBER_REQUEST) {
            if (resultCode == RESULT_OK) {
                initBarbers();
            }
        }
    }
}
