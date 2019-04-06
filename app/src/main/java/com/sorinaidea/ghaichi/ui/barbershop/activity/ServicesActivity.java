package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.EmptyAdabper;
import com.sorinaidea.ghaichi.adapter.barbershop.ServiceAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Service;
import com.sorinaidea.ghaichi.ui.ToolbarActivity;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.ServiceServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesActivity extends ToolbarActivity {

    private RecyclerView recServices;
    private FloatingActionButton fabAddService;
    private ArrayList<Service> listServices;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        listServices = new ArrayList<>();

        initToolbar(R.string.toolbar_manage_services, true, false);
        recServices = findViewById(R.id.recBanners);

        fabAddService = findViewById(R.id.fabAddService);
        fabAddService.setOnClickListener((view) -> {
            Intent intent = new Intent(ServicesActivity.this, AddServiceActivity.class);
            startActivity(intent);
        });

        recServices.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recServices.setNestedScrollingEnabled(false);
        fetchServices();
    }

    private void fetchServices() {
        ServiceServices serviceServices = API.getRetrofit().create(ServiceServices.class);
        showProgressDialog("دریافت لیست خدمات", "در حال خواندن داده‌ها", false);
        serviceServices.srevices(Auth.getAccessKey(this)).enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    try {
                        Objects.requireNonNull(response.body());
                        listServices.clear();
                        listServices.addAll(response.body());
                        if (listServices.isEmpty()) {
                            recServices.setAdapter(new EmptyAdabper(getApplicationContext()));
                        } else {
                            recServices.setAdapter(new ServiceAdapter(listServices, getApplicationContext()) {
                                @Override
                                public void showImages(Service service) {
                                    Intent intent = new Intent(getApplicationContext(), SamplesActivity.class);
                                    intent.putExtra("SERVICE", service);
                                    startActivity(intent);
                                }

                                @Override
                                public void delete(Service service) {
                                    confirmAlert(
                                            "حذف خدمت"
                                            , String.format(
                                                    App.LOCALE
                                                    , "%s %s %s؟"
                                                    , "آیا از حذف خدمت"
                                                    , service.getName()
                                                    , "مطمئنید"
                                            )
                                            , R.drawable.ic_delete
                                            , R.color.colorRedAccent200
                                            , "بله"
                                            , view -> deleteService(service)
                                            , "خیر"
                                            , null
                                    );
                                }

                                @Override
                                public void addBarber(Service service) {

                                }

                            });
                        }
                    } catch (NullPointerException ex) {
                        toast("خطا در دریافت خدمات");
                    }
                } else {
                    toast("خطایی رخ داده است!");
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                hideProgressDialog();
                if (t instanceof IOException) {
                    toast("خطا در ارتباط با سرور");
                } else {
                    toast("خطایی رخ داده است!");
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

    void deleteService(Service service) {

        ServiceServices services = API.getRetrofit().create(ServiceServices.class);
        services.delete(Auth.getAccessKey(this), service.getId()).enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
            @Override
            public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, Response<com.sorinaidea.ghaichi.models.Response> response) {
                if (response.isSuccessful()) {
                    toast("خدمت با موفقیت حذف شد.");
                    fetchServices();
                } else {
                    toast("خطایی رخ داده است.");
                }
            }

            @Override
            public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                if (t instanceof IOException) {
                    toast(R.string.err_unable_to_connect_to_server);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
