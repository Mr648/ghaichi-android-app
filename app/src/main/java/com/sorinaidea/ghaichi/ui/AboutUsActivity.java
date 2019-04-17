package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.About;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.SystemServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends AppCompatActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("درباره ما");

        TextView txtAboutUs = findViewById(R.id.txtAboutUs);
        TextView txtRules = findViewById(R.id.txtRules);
        TextView txtVersion = findViewById(R.id.txtVersion);

        AppCompatImageView imgResaneh = findViewById(R.id.imgResaneh);
        AppCompatImageView imgEnamad = findViewById(R.id.imgEnamad);
        AppCompatImageView imgBehparpdakht = findViewById(R.id.imgBehparpdakht);

        // getting fonts
        Typeface fontIransans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
        // setting fonts for about us text
        FontManager.setFont(txtAboutUs, fontIransans);
        FontManager.setFont(txtVersion, fontIransans);
        FontManager.setFont(txtRules, fontIransans);
        FontManager.setFont(mTitle, fontIransans);

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
                    txtAboutUs.setText(response.body().getAbout());
                    txtVersion.setText(response.body().getVersion());
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
