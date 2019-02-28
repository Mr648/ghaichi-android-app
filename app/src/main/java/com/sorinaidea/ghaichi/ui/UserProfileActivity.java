package com.sorinaidea.ghaichi.ui;

import android.graphics.Color;
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

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.DataAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Data;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.ProfileServices;
import com.sorinaidea.ghaichi.webservice.image.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        setContentView(R.layout.activity_userprofile);
        bindActivity();
        List<Data> list = new ArrayList<>();

        recData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adapter = new DataAdapter(list, this);
        recData.setAdapter(adapter);
        update();
    }

    public void update() {
        showProgressDialog(null, "در حال دریافت اطلاعات", false);

        ProfileServices service = API.getRetrofit().create(ProfileServices.class);

        Call<Map<String, String>> info = service.profile(Auth.getAccessKey(getApplicationContext()));

        info.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    try {
//                        Objects.requireNonNull(response.body());
                        updateView(response.body());
                    } catch (NullPointerException ex) {
                        alert("خطا", "مشکل در دریافت اطلاعات", R.drawable.ic_close, R.color.colorRedAccent900);
                    }
                }
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                if (t instanceof IOException)
                    alert("خطا", "مشکل در ارتباط با سرور", R.drawable.ic_close, R.color.colorRedAccent900);

                alert("خطا", "مشکل در دریافت اطلاعات", R.drawable.ic_close, R.color.colorRedAccent900);

            }
        });
    }


    public void updateView(Map<String, String> data) {
        List<Data> list = new ArrayList<>();
        for (String key : data.keySet()) {
            logInfo(key , data.get(key));
            list.add(new Data(key, data.get(key)));
        }
        recData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recData.setAdapter(new DataAdapter(list, this));
    }


    private void bindActivity() {
        initToolbar(R.string.toolbar_user_profile, false, true);
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
        getMenuInflater().inflate(R.menu.activity_user_profile, menu);
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
            try {
                ImagePicker.create(this).single().returnMode(ReturnMode.ALL)
                        .folderMode(true) // folder mode (false by default)
                        .toolbarFolderTitle("پوشه") // folder selection title
                        .toolbarImageTitle("برای انتخاب لمس کنید") // image selection title
                        .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("دوربین") // directory name for captured image  ("Camera" folder by default)
                        .enableLog(false) // disabling log
                        .start(); // start image picker activity with request code
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected UploadTask generateTask(File... files) throws NullPointerException {
        return null;
    }

}
