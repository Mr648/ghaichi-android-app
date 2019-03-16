package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.esafirm.imagepicker.features.ImagePicker;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Service;
import com.sorinaidea.ghaichi.models.UploadImageResponse;
import com.sorinaidea.ghaichi.ui.ImageUploaderActivity;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.ServiceServices;
import com.sorinaidea.ghaichi.webservice.image.MultipleImageUploader;
import com.sorinaidea.ghaichi.webservice.image.SingleImageUploader;
import com.sorinaidea.ghaichi.webservice.image.UploadTask;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SamplesActivity extends ImageUploaderActivity {

    RecyclerView recImages;
    FloatingActionButton fab;

    private Service service;


    boolean creatingNewService = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samples);
        initToolbar(R.string.toolbar_samples, true, false);


        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            service = extras.getParcelable("SERVICE");
            if (service == null) finish();
            creatingNewService = service.getId() == -1;
        }

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            ImagePicker.create(this)
                    .folderMode(true) // folder mode (false by default)
                    .toolbarFolderTitle("پوشه") // folder selection title
                    .toolbarImageTitle("برای انتخاب لمس کنید") // image selection title
                    .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                    .multi() // multi mode (default mode)
                    .limit(10) // max images can be selected (99 by default)
                    .showCamera(true) // show camera or not (true by default)
                    .imageDirectory("دوربین") // directory name for captured image  ("Camera" folder by default)
                    .enableLog(false) // disabling log
                    .start(); // start image picker activity with request code
        });

        recImages = findViewById(R.id.recImages);
        recImages.setLayoutManager(new GridLayoutManager(this, 2));
        recImages.addItemDecoration(new ItemOffsetDecoration(8));
//        recImages.setAdapter(new SamplesAdapter(this));
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
                    serviceServices.upload(Auth.getAccessKey(SamplesActivity.this), image).enqueue(new Callback<UploadImageResponse>() {
                        @Override
                        public void onResponse(Call<UploadImageResponse> call, retrofit2.Response<UploadImageResponse> response) {
                            hideProgressDialog();
                            if (response.isSuccessful()) {
                                service.setImages(response.body().getImages());

                                if (response.body().getImages().size() == 0) {

                                    alert("آپلود ناموفق", "خطا در آپلود تصاویر.", R.drawable.ic_info, R.color.colorAmberAccent900);
                                } else {
                                    alert("آپلود موفق", "تصاویر با موفقیت افزوده شدند.", R.mipmap.ic_file_upload_white_24dp, R.color.colorTransaction);
                                }

                                uploadResult = true;

                            } else {
                                alert("آپلود ناموفق", "خطا در آپلود تصاویر.", R.drawable.ic_info, R.color.colorAmberAccent900);
                                uploadResult = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                            hideProgressDialog();
                            uploadResult = false;
                            if (t instanceof IOException) {
//                                alert("قطع ارتباط", "خطا در اتصال به سرور", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark);
                                return;
                            }
//                            alert("آپلود ناموفق", "خطا در آپلود تصاویر.", R.drawable.ic_close, R.color.colorRedAccent900);
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
//                    showProgressDialog(R.string.upload_title, R.string.upload_progress  ,R.mipmap.ic_file_upload_white_24dp,R.color.colorAmberAccent900,false);

                    ServiceServices serviceServices = API.getRetrofit().create(ServiceServices.class);
                    serviceServices.upload(Auth.getAccessKey(SamplesActivity.this), image).enqueue(new Callback<UploadImageResponse>() {
                        @Override
                        public void onResponse(Call<UploadImageResponse> call, retrofit2.Response<UploadImageResponse> response) {
//                            hideProgressDialog();
                            if (response.isSuccessful()) {
                                service.setImages(response.body().getImages());
                                alert("آپلود موفق", "تصاویر با موفقیت افزوده شدند.", R.mipmap.ic_file_upload_white_24dp, R.color.colorTransaction);
                                uploadResult = true;
                            } else {
                                alert("آپلود ناموفق", "خطا در آپلود تصاویر.", R.drawable.ic_info, R.color.colorAmberAccent900);
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
//                            alert("آپلود ناموفق", "خطا در آپلود تصاویر.", R.drawable.ic_close, R.color.colorRedAccent900);
                        }
                    });
                    return uploadResult;
                }
            });
        }

        return task;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sample, menu);
        return true;
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

        if (creatingNewService) {
//            confirmAlert("آپلود تصاویر متوقف شود؟");
        }

        super.onBackPressed();

    }
}
