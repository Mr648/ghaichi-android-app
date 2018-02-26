package com.sorinaidea.arayeshgah.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.AboutUs;
import com.sorinaidea.arayeshgah.model.ClusterMarker;
import com.sorinaidea.arayeshgah.model.MapMarker;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.SorinaApplication;
import com.sorinaidea.arayeshgah.util.Util;
import com.sorinaidea.arayeshgah.webservice.AboutUsService;
import com.sorinaidea.arayeshgah.webservice.MapBuilderWebService;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mr-code on 2/26/2018.
 */

public class AboutUsActivity extends SorinaActivity {

    private TextView txtAboutUs;
    private TextView txtInstagram;
    private TextView txtLinkedIn;
    private TextView txtTwitter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);


        txtAboutUs = (TextView) findViewById(R.id.txtAboutUs);
        txtInstagram = (TextView) findViewById(R.id.txtInstagram);
        txtLinkedIn = (TextView) findViewById(R.id.txtLinkedIn);
        txtTwitter = (TextView) findViewById(R.id.txtTwitter);

        // getting fonts
        Typeface fontSocialIcons = FontManager.getTypeface(getApplicationContext(), FontManager.SOCIAL_ICONS);
        Typeface fontIransans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
        FontManager.markAsIconContainer(txtInstagram, fontSocialIcons);
        FontManager.markAsIconContainer(txtTwitter, fontSocialIcons);
        FontManager.markAsIconContainer(txtLinkedIn, fontSocialIcons);

        // setting fonts for about us text
        FontManager.markAsIconContainer(txtAboutUs, fontIransans);


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
}
