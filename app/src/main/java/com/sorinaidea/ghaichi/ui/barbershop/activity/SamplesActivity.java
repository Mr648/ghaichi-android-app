package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.barbershop.SamplesAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.models.Service;
import com.sorinaidea.ghaichi.ui.ImageUploaderActivity;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.SampleServices;
import com.sorinaidea.ghaichi.webservice.barbershop.ServiceServices;
import com.sorinaidea.ghaichi.webservice.image.MultipleImageUploader;
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

public class SamplesActivity extends ImageUploaderActivity {

    RecyclerView recImages;
    FloatingActionButton fab;
    private Service service;
    private ArrayList<Image> listImages;
    private SamplesAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samples);
        initToolbar(R.string.toolbar_samples, true, false);
        recImages = findViewById(R.id.recImages);
        recImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            service = extras.getParcelable("SERVICE");
            if (service == null) finish();
            listImages = new ArrayList<>();
            listImages.addAll(service.getImages());
            if (listImages == null) {
                fetchServiceImages();
            } else {
                adapter = new SamplesAdapter(listImages, getApplicationContext()) {
                    @Override
                    protected void onDeleteClicked(Image image) {
                        deleteItem(image.getId());
                    }
                };
                recImages.setAdapter(adapter);
            }
        }

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> pickMultipleImages());


    }

    private void fetchServiceImages() {
        ServiceServices serviceServices = API.getRetrofit().create(ServiceServices.class);
        serviceServices.samples(Auth.getAccessKey(this), service.getId()).enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, retrofit2.Response<List<Image>> response) {
                if (response.isSuccessful()) {
                    try {
                        Objects.requireNonNull(response.body());
                        listImages.addAll(response.body());
                        if (adapter == null) {
                            adapter = new SamplesAdapter(listImages, getApplicationContext()) {
                                @Override
                                protected void onDeleteClicked(Image image) {
                                    deleteItem(image.getId());
                                }
                            };
                            recImages.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    } catch (NullPointerException ex) {
                    }
                } else {

                    toast("خطا در پاسخ سرور");
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                toast("خطای کلی");
            }
        });
    }

    private void deleteItem(int image) {
        confirmAlert("هشدار", "آیا تصویر حذف شود؟", R.drawable.ic_info, R.color.colorRedAccent200, view -> {
            showProgressDialog("حذف تصویر", "", false);
            SampleServices sampleServices = API.getRetrofit().create(SampleServices.class);
            sampleServices.delete(Auth.getAccessKey(this), image).enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
                @Override
                public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, retrofit2.Response<Response> response) {
                    hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {
                            Objects.requireNonNull(response.body());
                            toast(response.body().getMessage());
                            fetchServiceImages();
                        } catch (NullPointerException ex) {
                            actionAlert("خطا", "خطایی رخ داده است.", R.drawable.ic_info, R.color.colorAmberAccent900, view -> fetchServiceImages());
                        }
                    }
                }

                @Override
                public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                    hideProgressDialog();
                    if (t instanceof IOException) {
                        actionAlert("قطع اتصال", "ارتباط شما با سرور قطع است", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark, view -> fetchServiceImages());
                    }
                }
            });
        });
    }

    @Override
    protected UploadTask generateTask(File... files) {
        if (null == files) throw new NullPointerException("هیچ فایلی انتخاب نشده است.");

        UploadTask task;

        if (files.length > 1) {
            MultipartBody.Part images[] = new MultipartBody.Part[files.length];
            for (int i = 0; i < files.length; i++) {
                images[i] = MultipartBody.Part.createFormData("images[" + i + "]", files[i].getName(),
                        RequestBody.create(MediaType.parse("image/*"), files[i]));
            }

            task = new UploadTask(new MultipleImageUploader(images) {

                boolean uploadResult = false;

                @Override
                public boolean isDone() {
                    return uploadResult;
                }

                @Override
                public boolean upload(MultipartBody.Part[] image) {

                    showProgressDialog(R.string.upload_title, R.string.upload_progress, R.mipmap.ic_file_upload_white_24dp, R.color.colorAmberAccent900, false);

                    ServiceServices serviceServices = API.getRetrofit().create(ServiceServices.class);
                    serviceServices.upload(Auth.getAccessKey(SamplesActivity.this), service.getId(), image).enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            hideProgressDialog();
                            if (response.isSuccessful()) {
                                alert("آپلود موفق", "تصاویر با موفقیت افزوده شدند.", R.mipmap.ic_file_upload_white_24dp, R.color.colorTransaction);
                                uploadResult = true;
                            } else {
                                alert("آپلود ناموفق", "خطا در آپلود تصاویر.", R.drawable.ic_info, R.color.colorAmberAccent900);
                                uploadResult = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            hideProgressDialog();
                            uploadResult = false;
                        }
                    });
                    return uploadResult;
                }
            });

        } else {
            MultipartBody.Part image = MultipartBody.Part.createFormData("images[0]", files[0].getName(),
                    RequestBody.create(MediaType.parse("image/*"), files[0]));

            task = new UploadTask(new SingleImageUploader(image) {

                boolean uploadResult = false;

                @Override
                public boolean isDone() {
                    return uploadResult;
                }

                @Override
                public boolean upload(MultipartBody.Part image) {
                    showProgressDialog(R.string.upload_title, R.string.upload_progress, R.mipmap.ic_file_upload_white_24dp, R.color.colorAmberAccent900, false);

                    ServiceServices serviceServices = API.getRetrofit().create(ServiceServices.class);
                    serviceServices.upload(Auth.getAccessKey(SamplesActivity.this), service.getId(), image).enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            hideProgressDialog();
                            if (response.isSuccessful()) {
                                alert("آپلود موفق", "تصاویر با موفقیت افزوده شدند.", R.mipmap.ic_file_upload_white_24dp, R.color.colorTransaction);
                                uploadResult = true;
                            } else {
                                alert("آپلود ناموفق", "خطا در آپلود تصاویر.", R.drawable.ic_info, R.color.colorAmberAccent900);
                                uploadResult = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            hideProgressDialog();
                            uploadResult = false;
                            if (t instanceof IOException) {
                                alert("قطع ارتباط", "خطا در اتصال به سرور", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark);
                                return;
                            }
                        }
                    });
                    return uploadResult;
                }
            });
        }

        return task;
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
