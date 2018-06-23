package com.sorinaidea.arayeshgah.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.AboutUs;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.Util;
import com.sorinaidea.arayeshgah.webservice.AboutUsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AboutUsActivity extends AppCompatActivity {

    private TextView txtAboutUs;
    private Toolbar toolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("درباره ما");

        txtAboutUs = (TextView) findViewById(R.id.txtAboutUs);

        // getting fonts
        Typeface fontIransans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
        // setting fonts for about us text
        FontManager.setFont(txtAboutUs, fontIransans);


        // build retrofit
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(Util.CONSTANTS.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        /**
         *  get about us text from SERVER using {@link AboutUsService}
         * */
        AboutUsService service = retrofit.create(AboutUsService.class);
        Call<AboutUs> repos = service.getAboutUs();
        repos.enqueue(new Callback<AboutUs>() {
            @Override
            public void onFailure(Call<AboutUs> call, Throwable t) {
            }

            @Override
            public void onResponse(Call<AboutUs> call, Response<AboutUs> response) {
                txtAboutUs.setText(response.body().getAboutUsText() + response.body().getAboutUsText());
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
