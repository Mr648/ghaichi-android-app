package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.DataAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Data;
import com.sorinaidea.ghaichi.models.UploadImageResponse;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.ProfileServices;
import com.sorinaidea.ghaichi.webservice.image.SingleImageUploader;
import com.sorinaidea.ghaichi.webservice.image.UploadTask;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

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

public class UserProfileActivity extends ImageUploaderActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private AppBarLayout mAppBarLayout;
    private TextView txtHeaderName;
    private TextView txtHeaderNumber;
    private RecyclerView recData;

    private de.hdodenhof.circleimageview.CircleImageView imgUserImage;

    DataAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        bindActivity();
        update();
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
                                } catch (NullPointerException ex) {
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
        } catch (NullPointerException ex) {
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
        // TODO how to use Picasso
        API.getPicasso(this)
                .load(image.getValue())
                .fit()
                .centerInside()
                .into(imgUserImage);
    }

    public void updateView(List<Data> userData) {
        Data data = null;
        for (Data d : userData) {
            if (d.getKeyEn().equals("avatar")) {
                data = d;
                userData.remove(d);
                break;
            }
        }

//        alert("اطلاعات", data != null ? data.getValue() : "default", R.drawable.ic_close, R.color.colorBlue);
        updateProfileImage(data);
        recData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recData.setAdapter(new DataAdapter(userData, this, edit -> {
            new LovelyTextInputDialog(this)
                    .setTopColorRes(R.color.colorAmberAccent900)
                    .setIcon(R.drawable.ic_edit_white_24dp)
                    .setTitle("ویرایش")
                    .setMessage(edit.getKeyFa() + " خود را ویرایش کنید.")
                    .setInitialInput(edit.getValue())
                    .setConfirmButton("تایید", text -> {
                        if (!text.isEmpty() && !text.equals(edit.getValue())) {
                            updateField(edit.getKeyEn(), text);
                        }
                    })
                    .configureMessageView(this::applyTextFont)
                    .configureTitleView(this::applyTextFont)
                    .show();
        }));
    }

    private ProfileServices getProfileServices() {
        return API.getRetrofit(this).create(ProfileServices.class);
    }

    private void updateField(String key, String value) {
        try {
            Objects.requireNonNull(getProfileServices())
                    .updateUser(  key, value)
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
        } catch (NullPointerException ex) {
            confirmAlert("خطا", "خطا در احراز هویت کاربر", R.drawable.ic_account_circle_white_24dp, R.color.colorRedAccent900, view -> Auth.logout(this));
        }
    }

    private void bindActivity() {
        initToolbar(R.string.toolbar_user_profile, false, false);
        mTitleContainer = findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = findViewById(R.id.main_appbar);
        mAppBarLayout.addOnOffsetChangedListener(this);
        startAlphaAnimation(toolbarTitle, 0, View.INVISIBLE);
        txtHeaderName = findViewById(R.id.txtHeaderName);
        txtHeaderNumber = findViewById(R.id.txtHeaderNumber);
        imgUserImage = findViewById(R.id.imgUserImage);
        recData = findViewById(R.id.recData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profiles, menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(toolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(toolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
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
                            .changeAvatar( image)
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
                } catch (NullPointerException ex) {
                    confirmAlert("خطا", "خطا در احراز هویت کاربر", R.drawable.ic_account_circle_white_24dp, R.color.colorRedAccent900, view -> Auth.logout(UserProfileActivity.this));
                }
                return uploadResult;
            }
        });

        return task;
    }

}
