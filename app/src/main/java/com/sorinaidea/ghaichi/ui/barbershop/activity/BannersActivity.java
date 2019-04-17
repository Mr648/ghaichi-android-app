package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.EmptyAdabper;
import com.sorinaidea.ghaichi.adapter.barbershop.BannersAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.models.UploadImageResponse;
import com.sorinaidea.ghaichi.ui.ImageUploaderActivity;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.BannerServices;
import com.sorinaidea.ghaichi.webservice.image.SingleImageUploader;
import com.sorinaidea.ghaichi.webservice.image.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannersActivity extends ImageUploaderActivity {

    private RecyclerView recBanners;
    private FloatingActionButton fabAddBanner;
    private List<Image> banners;
    private BannersAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banners);

        initToolbar(R.string.toolbar_banners, true, false);

        recBanners = findViewById(R.id.recBanners);

        fabAddBanner = findViewById(R.id.fabAddBanner);
        fabAddBanner.setOnClickListener((view) -> {
            pickSingleImage();
        });

        banners = new ArrayList<>();

        recBanners.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        fetchBanners();
    }


    private void fetchBanners() {
        showProgress();
        BannerServices bannersServices = API.getRetrofit(this).create(BannerServices.class);
        bannersServices.index().enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(@NonNull Call<List<Image>> call, @NonNull Response<List<Image>> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    try {
                        banners.clear();
                        banners.addAll(Objects.requireNonNull(response.body()));
                        if (banners.isEmpty()) {
                            recBanners.setAdapter(new EmptyAdabper(BannersActivity.this));
                        } else {
                            adapter = new BannersAdapter(banners, BannersActivity.this) {
                                @Override
                                protected void onDeleteClicked(Image image) {
                                    deleteBanner(image);
                                }
                            };
                            recBanners.setAdapter(adapter);
                        }
                    } catch (NullPointerException ex) {
                        toast("خطایی رخ داده است.");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                hideProgress();
                if (t instanceof IOException) {
                    toast("ارتباط شما با سرور قطع است");
                }
            }
        });
    }


    private void deleteBanner(Image image) {
        confirmAlert("هشدار", "آیا تصویر حذف شود؟", R.drawable.ic_info, R.color.colorRedAccent200, view -> {
            showProgressDialog("حذف تصویر", "", false);
            BannerServices bannerServices = API.getRetrofit(this).create(BannerServices.class);
            bannerServices.delete(  image.getId()).enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
                @Override
                public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, retrofit2.Response<com.sorinaidea.ghaichi.models.Response> response) {
                    hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {
                            toast(Objects.requireNonNull(response.body()).getMessage());
                            fetchBanners();
                        } catch (NullPointerException ex) {
                            actionAlert("خطا", "خطایی رخ داده است.", R.drawable.ic_info, R.color.colorAmberAccent900, view -> fetchBanners());
                        }
                    }
                }

                @Override
                public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                    hideProgressDialog();
                    if (t instanceof IOException) {
                        actionAlert("قطع اتصال", "ارتباط شما با سرور قطع است", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark, view -> fetchBanners());
                    }
                }
            });
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

    @Override
    protected UploadTask generateTask(File... files) throws NullPointerException {

        UploadTask task;

        MultipartBody.Part image = MultipartBody.Part.createFormData("banner", files[0].getName(),
                RequestBody.create(MediaType.parse("image/*"), files[0]));

        task = new UploadTask(new SingleImageUploader(image) {

            boolean uploadResult = false;

            @Override
            public boolean isDone() {
                return uploadResult;
            }

            @Override
            public boolean upload(MultipartBody.Part image) {
                showProgress();
                BannerServices bannerServices = API.getRetrofit(BannersActivity.this).create(BannerServices.class);
                bannerServices.create(image).enqueue(new Callback<UploadImageResponse>() {
                    @Override
                    public void onResponse(Call<UploadImageResponse> call, retrofit2.Response<UploadImageResponse> response) {
                        hideProgressDialog();
                        if (response.isSuccessful()) {
                            UploadImageResponse result = response.body();
                            result.getImages();
                            alert("آپلود موفق", "تصویر با موفقیت افزوده شدند.", R.mipmap.ic_file_upload_white_24dp, R.color.colorTransaction);
                            uploadResult = true;
                        } else {
                            alert("آپلود ناموفق", "خطا در آپلود تصویر.", R.drawable.ic_info, R.color.colorAmberAccent900);
                            uploadResult = false;
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadImageResponse> call, Throwable t) {
//                            hideProgressDialog();
                        uploadResult = false;
                        if (t instanceof IOException) {
                            alert("قطع ارتباط", "خطا در اتصال به سرور", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark);
                            return;
                        }
                        alert("آپلود ناموفق", "خطا در آپلود تصاویر.", R.drawable.ic_close, R.color.colorRedAccent900);
                    }
                });
                return uploadResult;
            }
        });

        return task;
    }
}
