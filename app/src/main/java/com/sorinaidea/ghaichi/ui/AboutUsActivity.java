package com.sorinaidea.ghaichi.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.About;
import com.sorinaidea.ghaichi.model.AboutUs;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.AboutUsService;
import com.sorinaidea.ghaichi.webservice.SystemServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AboutUsActivity extends AppCompatActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("درباره ما");

        TextView txtAboutUs = (TextView) findViewById(R.id.txtAboutUs);
        TextView txtRules = (TextView) findViewById(R.id.txtRules);
        TextView txtVersion = (TextView) findViewById(R.id.txtVersion);

        // getting fonts
        Typeface fontIransans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
        // setting fonts for about us text
        FontManager.setFont(txtAboutUs, fontIransans);
        FontManager.setFont(txtVersion, fontIransans);
        FontManager.setFont(txtRules, fontIransans);
        FontManager.setFont(mTitle, fontIransans);


        Call<About> about = API.getRetrofit().create(SystemServices.class).about(Util.getAccessKey(getApplicationContext()));
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
