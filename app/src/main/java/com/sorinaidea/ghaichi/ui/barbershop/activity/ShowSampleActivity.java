package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.adapter.barbershop.SCDCategoryAdapter;
import com.sorinaidea.ghaichi.adapter.barbershop.SCDServiceAdapter;
import com.sorinaidea.ghaichi.adapter.barbershop.SamplesAdapter;
import com.sorinaidea.ghaichi.adapter.barbershop.SingleChoiceDialogAdapter;
import com.sorinaidea.ghaichi.models.Category;
import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.models.Service;
import com.sorinaidea.ghaichi.ui.ToolbarActivity;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.CategoryServices;
import com.sorinaidea.ghaichi.webservice.barbershop.SampleServices;
import com.sorinaidea.ghaichi.webservice.barbershop.ServiceServices;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mr-code on 5/15/2018.
 */

public class ShowSampleActivity extends ToolbarActivity {

    private RecyclerView recSamples;

    private FloatingActionButton fab;
    private final static int NUM_COLUMNS = 3;


    private ArrayList<Category> categories;
    private ArrayList<Service> services;
    private ArrayList<Image> images;

    private SamplesAdapter samplesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sample_works);
        initToolbar("مدیریت نمونه کارها", true, false);

        services = new ArrayList<>();
        categories = new ArrayList<>();
        images = new ArrayList<>();

        recSamples = findViewById(R.id.recSamples);
        recSamples.setLayoutManager(new GridLayoutManager(getApplicationContext(), NUM_COLUMNS, LinearLayoutManager.VERTICAL, false));
        recSamples.addItemDecoration(new ItemOffsetDecoration(getApplicationContext(), R.dimen._4dp));
        recSamples.setNestedScrollingEnabled(false);

        samplesAdapter = new SamplesAdapter(images, this) {

            @Override
            protected void onDeleteClicked(Image image) {
                deleteItem(image.getId());
            }
        };
        recSamples.setAdapter(samplesAdapter);


        fab = findViewById(R.id.fab);
        fetchServices();
        fetchCategories();
        fetchSamples();

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.filter_all) {
            fetchSamples();
        } else if (id == R.id.filter_service) {
            showServicesDialog();
        } else if (id == R.id.filter_category) {
            showCategoriesDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sample, menu);
        return true;
    }


    private void deleteItem(int image) {
        confirmAlert("هشدار", "آیا تصویر حذف شود؟", R.drawable.ic_info, R.color.colorRedAccent200, view -> {
            showProgressDialog("حذف تصویر", "", false);

            API.getRetrofit(this)
                    .create(SampleServices.class)
                    .delete(image)
                    .enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
                        @Override
                        public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, Response<com.sorinaidea.ghaichi.models.Response> response) {
                            hideProgressDialog();
                            if (response.isSuccessful()) {
                                try {
                                    Objects.requireNonNull(response.body());
                                    toast(response.body().getMessage());
                                    fetchSamples();
                                } catch (NullPointerException ex) {
                                    actionAlert("خطا", "خطایی رخ داده است.", R.drawable.ic_info, R.color.colorAmberAccent900, view -> fetchServices());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                            hideProgressDialog();
                            if (t instanceof IOException) {
                                actionAlert("قطع اتصال", "ارتباط شما با سرور قطع است", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark, view -> fetchServices());
                            }
                        }
                    });
        });
    }

    private void fetchServices() {
        showProgressDialog(null, "در حال دریافت داده‌ها", false);
        API.getRetrofit(this)
                .create(ServiceServices.class)
                .srevices()
                .enqueue(new Callback<List<Service>>() {
                    @Override
                    public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                        hideProgressDialog();
                        if (response.isSuccessful()) {
                            try {
                                Objects.requireNonNull(response.body());
                                services.clear();
                                services.addAll(response.body());
                            } catch (NullPointerException ex) {
                                actionAlert("خطا", "خطایی رخ داده است.", R.drawable.ic_info, R.color.colorAmberAccent900, view -> fetchServices());
                            }
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Service>> call, Throwable t) {
                        hideProgressDialog();
                        if (t instanceof IOException) {
                            actionAlert("قطع اتصال", "ارتباط شما با سرور قطع است", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark, view -> fetchServices());
                        }
                    }
                });
    }

    private void fetchCategories() {
        showProgressDialog(null, "در حال دریافت داده‌ها", false);

        API.getRetrofit(this)
                .create(CategoryServices.class)
                .categories()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        hideProgressDialog();
                        if (response.isSuccessful()) {
                            try {
                                Objects.requireNonNull(response.body());
                                categories.clear();
                                categories.addAll(response.body());
                            } catch (NullPointerException ex) {
                                actionAlert("خطا", "خطایی رخ داده است.", R.drawable.ic_info, R.color.colorAmberAccent900, view -> fetchServices());
                            }
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        hideProgressDialog();
                        if (t instanceof IOException) {
                            actionAlert("قطع اتصال", "ارتباط شما با سرور قطع است", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark, view -> fetchCategories());
                        }
                    }
                });
    }


    private Callback<List<Image>> callback =
            new Callback<List<Image>>() {
                @Override
                public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                    toast(call.request().url().toString());
                    hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {
                            Objects.requireNonNull(response.body());
                            images.clear();
                            images.addAll(response.body());
                            samplesAdapter.notifyDataSetChanged();
                        } catch (NullPointerException ex) {
                            actionAlert("خطا", "خطایی رخ داده است.", R.drawable.ic_info, R.color.colorAmberAccent900, view -> fetchServices());
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<List<Image>> call, Throwable t) {
                    hideProgressDialog();
                    if (t instanceof IOException) {
                        actionAlert("قطع اتصال", "ارتباط شما با سرور قطع است", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark, view -> fetchServices());
                    }
                }
            };

    private void fetchSamples() {
        showProgressDialog(null, "در حال دریافت داده‌ها", false);
        API.getRetrofit(this).create(SampleServices.class).index().enqueue(callback);
    }

    private void fetchSamples(String type, int id) {
        showProgressDialog(null, "در حال دریافت داده‌ها", false);
        API.getRetrofit(this).create(SampleServices.class)
                .index(type, id).enqueue(callback);

    }

    private void showServicesDialog() {

        SCDServiceAdapter adapter = new SCDServiceAdapter(services, this) {
            @Override
            protected void checked(Service service) {
                fetchSamples("service", service.getId());
            }
        };

        showDialog(adapter, "انتخاب خدمت", "یک خدمت انتخاب کنید.");
    }

    private void showCategoriesDialog() {
        SCDCategoryAdapter adapter = new SCDCategoryAdapter(categories, this) {
            @Override
            protected void checked(Category category) {
                fetchSamples("category", category.getId());

            }
        };
        showDialog(adapter, "انتخاب دسته‌بندی", "یک دسته‌بندی انتخاب کنید.");
    }

    private void showDialog(SingleChoiceDialogAdapter adapter, String title, String msg) {
        RecyclerView recItems;
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_single_choice, null);
        recItems = dialogView.findViewById(R.id.recItems);
        recItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        LovelyCustomDialog dialog = new LovelyCustomDialog(this)
                .setView(dialogView)
                .setTopColorRes(R.color.colorGold)
                .setTitle(title)
                .setIcon(R.drawable.ic_attach_money)
                .setMessage(msg)
                .configureMessageView(this::applyTextFont)
                .configureTitleView(this::applyTextFont);

        recItems.setAdapter(adapter);

        dialog.show();
    }
}



