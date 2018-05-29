package com.sorinaidea.arayeshgah.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.FAQAdabper;
import com.sorinaidea.arayeshgah.model.AboutUs;
import com.sorinaidea.arayeshgah.model.FAQ;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.Util;
import com.sorinaidea.arayeshgah.webservice.AboutUsService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AboutUsFragment extends Fragment {

    public AboutUsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aboutus, container, false);
    }

    private TextView txtAboutUs;
    private TextView txtInstagram;
    private TextView txtLinkedIn;
    private TextView txtTwitter;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        txtAboutUs = (TextView) view.findViewById(R.id.txtAboutUs);
        txtInstagram = (TextView) view.findViewById(R.id.txtInstagram);
        txtLinkedIn = (TextView) view.findViewById(R.id.txtLinkedIn);
        txtTwitter = (TextView) view.findViewById(R.id.txtTwitter);

        // getting fonts
        Typeface fontSocialIcons = FontManager.getTypeface(getContext(), FontManager.SOCIAL_ICONS);
        Typeface fontIransans = FontManager.getTypeface(getContext(), FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
        FontManager.setFont(txtInstagram, fontSocialIcons);
        FontManager.setFont(txtTwitter, fontSocialIcons);
        FontManager.setFont(txtLinkedIn, fontSocialIcons);

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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
