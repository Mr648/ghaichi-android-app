package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Data;
import com.sorinaidea.ghaichi.models.UploadImageResponse;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.ProfileServices;
import com.sorinaidea.ghaichi.webservice.image.SingleImageUploader;
import com.sorinaidea.ghaichi.webservice.image.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mr-code on 6/17/2018.
 */

public class UserProfileActivity extends ImageUploaderActivity {


    AppCompatImageView imgImage;

    EditText txtName;
    EditText txtFamily;
    EditText txtNationalCode;
    EditText txtMobile;
    EditText txtGender;
    EditText txtEmail;
    EditText txtBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        bindActivity();
        update();

        applyTextFont(
                txtName,
                txtFamily,
                txtNationalCode,
                txtMobile,
                txtGender,
                txtEmail,
                txtBirthday
        );
    }

    public void update() {
        showProgressDialog(null, "در حال دریافت اطلاعات", false);
        try {
            Objects.requireNonNull(getProfileServices())
                    .profile()
                    .enqueue(new Callback<List<Data>>() {
                        @Override
                        public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                            if (response.isSuccessful()) {
                                try {
                                    Objects.requireNonNull(response.body());
                                    updateView(response.body());
                                } catch (NullPointerException ignored) {
                                    alert(
                                            "خطا",
                                            "مشکل در دریافت اطلاعات",
                                            R.drawable.ic_close,
                                            R.color.colorRedAccent900
                                    );
                                }
                            }
                            hideProgressDialog();
                        }

                        @Override
                        public void onFailure(Call<List<Data>> call, Throwable t) {
                            if (t instanceof IOException)
                                alert(
                                        "خطا",
                                        "مشکل در ارتباط با سرور",
                                        R.drawable.ic_close,
                                        R.color.colorRedAccent900
                                );

                            alert(
                                    "خطا",
                                    "مشکل در دریافت اطلاعات",
                                    R.drawable.ic_close,
                                    R.color.colorRedAccent900
                            );
                        }
                    });
        } catch (NullPointerException ignored) {
            confirmAlert(
                    "خطا",
                    "خطا در احراز هویت کاربر",
                    R.drawable.ic_account_circle_white_24dp,
                    R.color.colorRedAccent900,
                    view -> Auth.logout(this)
            );
        }
    }


    public void updateProfileImage(Data image) {
        if (image == null) return;
        if (image.getValue().equals("default")) return;

        logInfo(image.getValue());

        API.getPicasso(this)
                .load(Util.imageUrl(image.getValue(),imgImage))
                .centerCrop()
                .fit()
                .into(imgImage);
    }

    public void updateView(List<Data> userData) {
        for (Data data : userData) {
            switch (data.getKeyEn()) {
                case "name": {
                    txtName.setText(String.valueOf(data.getValue()));
                }
                break;
                case "family": {
                    txtFamily.setText(String.valueOf(data.getValue()));
                }
                break;
                case "national_code": {
                    txtNationalCode.setText(String.valueOf(data.getValue()));
                }
                break;
                case "mobile": {
                    txtMobile.setText(String.valueOf(data.getValue()));
                }
                break;
                case "email": {
                    txtEmail.setText(String.valueOf(data.getValue()));
                }
                break;
                case "birthday": {
                    txtBirthday.setText(String.valueOf(data.getValue()));
                }
                break;
                case "gender": {
                    txtGender.setText(String.valueOf(data.getValue()));
                }
                break;
                case "avatar": {
                    updateProfileImage(data);
                }
                break;
            }
        }
    }

    private ProfileServices getProfileServices() {
        return API.getRetrofit(this).create(ProfileServices.class);
    }

    private void updateField(String key, String value) {
        try {
            Objects.requireNonNull(getProfileServices())
                    .updateUser(key, value)
                    .enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
                        @Override
                        public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, Response<com.sorinaidea.ghaichi.models.Response> response) {
                            if (response.isSuccessful()) {
                                toast(response.body().getMessage());
                                update();
                            }
                        }
                        @Override
                        public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                            toast("خطا در بروزرسانی داده‌");
                        }
                    });
        } catch (NullPointerException ignored) {
            confirmAlert("خطا", "خطا در احراز هویت کاربر", R.drawable.ic_account_circle_white_24dp, R.color.colorRedAccent900, view -> Auth.logout(this));
        }
    }

    private void bindActivity() {
        initToolbar(R.string.toolbar_user_profile, false, false);
        imgImage = findViewById(R.id.imgImage);
        txtName = findViewById(R.id.txtName);
        txtFamily = findViewById(R.id.txtFamily);
        txtNationalCode = findViewById(R.id.txtNationalCode);
        txtMobile = findViewById(R.id.txtMobile);
        txtGender = findViewById(R.id.txtGender);
        txtEmail = findViewById(R.id.txtEmail);
        txtBirthday = findViewById(R.id.txtBirthday);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profiles, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_pick_photo) {
            pickSingleImage();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected UploadTask generateTask(File... files) throws NullPointerException {
        if (null == files) throw new NullPointerException("هیچ فایلی انتخاب نشده است.");

        UploadTask task;


        MultipartBody.Part image = MultipartBody.Part.createFormData("avatar", files[0].getName(),
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
                try {
                    Objects.requireNonNull(getProfileServices())
                            .changeAvatar(image)
                            .enqueue(new Callback<UploadImageResponse>() {
                                @Override
                                public void onResponse(Call<UploadImageResponse> call, retrofit2.Response<UploadImageResponse> response) {
                                    hideProgress();
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
                                    hideProgress();
                                    uploadResult = false;
                                    if (t instanceof IOException) {
                                        alert("قطع ارتباط", "خطا در اتصال به سرور", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark);
                                        return;
                                    }
                                    alert("آپلود ناموفق", "خطا در آپلود تصاویر.", R.drawable.ic_close, R.color.colorRedAccent900);
                                }
                            });
                } catch (NullPointerException ignored) {
                    confirmAlert("خطا", "خطا در احراز هویت کاربر", R.drawable.ic_account_circle_white_24dp, R.color.colorRedAccent900, view -> Auth.logout(UserProfileActivity.this));
                }
                return uploadResult;
            }
        });
        return task;
    }
}
