package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.DataAdapter;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Data;
import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.AdvertiseServices;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class AdvertisementInfoActivity extends ToolbarActivity {


    private RecyclerView recInfo;
    private AppCompatImageView imgBanner;

    private int advertiseId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_advertisment);

        initToolbar("جزییات تبلیغ", true, false);
        recInfo = findViewById(R.id.recInfo);
        imgBanner = findViewById(R.id.imgBanner);

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            advertiseId = Integer.parseInt(extras.getString(Util.COMMUNICATION_KEYS.ADVERTISE_ID));

            getAdvertiseInfo();
        } else {
            finish();
        }
    }


    private void getAdvertiseInfo() {
        recInfo.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen._4dp);
        recInfo.addItemDecoration(itemDecoration);

        showProgress();

        API.getRetrofit(this).create(AdvertiseServices.class)
                .advertise(advertiseId)
                .enqueue(new Callback<List<Data>>() {
                    @Override
                    public void onResponse(Call<List<Data>> call, retrofit2.Response<List<Data>> response) {
                        hideProgress();
                        if (response.isSuccessful()) {
                            try {
                                updateAdvertiseInfo(Objects.requireNonNull(response.body()));
                            } catch (NullPointerException ex) {
                                toast("مشکل در دریافت اطلاعات");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Data>> call, Throwable t) {
                        hideProgress();
                        if (t instanceof IOException) {
                            toast(R.string.err_unable_to_connect_to_server);
                            return;
                        }
                        toast("مشکل در دریافت اطلاعات");
                    }
                });
    }

    private void updateBanner(Data data) {
        if (data == null) return;
        if (data.getValue().equals("default")) return;
        imgBanner.setVisibility(View.VISIBLE);
        API.getPicasso(this)
                .load(data.getValue())
                .centerCrop()
                .fit()
                .into(imgBanner);

    }

    private void updateAdvertiseInfo(List<Data> advertiseInfo) {
        Data data = null;
        for (Data d : advertiseInfo) {
            if (d.getKeyEn().equals("banner")) {
                data = d;
                advertiseInfo.remove(d);
                break;
            }
        }

        updateBanner(data);
        recInfo.setAdapter(new DataAdapter(advertiseInfo, this, edit -> {
            new LovelyTextInputDialog(this)
                    .setTopColorRes(R.color.colorAmberAccent900)
                    .setIcon(R.drawable.ic_edit_white_24dp)
                    .setTitle("ویرایش")
                    .setMessage(edit.getKeyFa() + " تبلیغ را ویرایش کنید.")
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

    private void updateField(String key, String value) {


        API.getRetrofit(this).create(AdvertiseServices.class)
                .updateAdvertise( key, value)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            toast(response.body().getMessage());
                            getAdvertiseInfo();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                        toast("خطا در بروزرسانی داده‌");
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

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


}
