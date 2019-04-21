package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.About;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.SystemServices;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends ToolbarActivity {

    AppCompatImageView imgResaneh;
    AppCompatImageView imgEnamad;
    AppCompatImageView imgBehparpdakht;

    TextView txtAboutUs;
    TextView txtRules;
    TextView txtVersion;
    TextView txt;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        initToolbar("درباره ما", true, false);

        txtAboutUs = findViewById(R.id.txtAboutUs);
        txtRules = findViewById(R.id.txtRules);
        txt = findViewById(R.id.txt);
        txtVersion = findViewById(R.id.txtVersion);

        imgResaneh = findViewById(R.id.imgResaneh);
        imgEnamad = findViewById(R.id.imgEnamad);
        imgBehparpdakht = findViewById(R.id.imgBehparpdakht);

        txtRules.setOnClickListener((view) -> {
            String url = "http://ghaichi.com/rules";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        Call<About> about = API.getRetrofit(this).create(SystemServices.class).about();
        about.enqueue(new Callback<About>() {
            @Override
            public void onResponse(Call<About> call, Response<About> response) {
                if (response.body() != null) {
                    try {
                        About about = Objects.requireNonNull(response.body());
                        txtAboutUs.setText(about.getAbout());
                        txtVersion.setText(String.format(App.LOCALE, "v%s", about.getVersion()));
                    } catch (NullPointerException ignored) {
                        toast(R.string.err_unable_to_connect_to_server);
                    }
                }
            }

            @Override
            public void onFailure(Call<About> call, Throwable t) {

            }
        });

        View.OnClickListener onClickListener = (view) -> {
            String url = "https://ghaichi.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        };

        imgEnamad.setOnClickListener(onClickListener);
        imgResaneh.setOnClickListener(onClickListener);
        imgBehparpdakht.setOnClickListener(onClickListener);

        applyTextFont(
                txtAboutUs,
                txtVersion,
                txtRules,
                txt
        );
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
