package com.sorinaidea.ghaichi.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BarberShopCategoryAdapter;
import com.sorinaidea.ghaichi.adapter.ImageSliderAdapter;
import com.sorinaidea.ghaichi.adapter.TopBarberShopUserAdabper;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.fast.Advertise;
import com.sorinaidea.ghaichi.model.FAQ;
import com.sorinaidea.ghaichi.ui.MapsActivity;
import com.sorinaidea.ghaichi.util.GhaichiPrefrenceManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.AdvertisesService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Query;


public class HomePageFragment extends Fragment {

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        imageList = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fargment_home, container, false);
    }


    private RecyclerView recCategories;
    private ViewPager mPager;
    private FloatingActionButton fabMap;
    private CircleIndicator indicator;
    private static int currentPage = 0;

    private ArrayList<String> imageList = new ArrayList<>();

    private void initializeImageSlider() {
/*

        Retrofit retrofit = API.getRetrofit();
        AdvertisesService advertises = retrofit.create(AdvertisesService.class);

        String accessKey = Auth.getAccessKey(getContext());

        ImageSliderAdapter adapter = new ImageSliderAdapter(getContext(), imageList);
        mPager.setAdapter(adapter);
        indicator.setViewPager(mPager);

        advertises.advertises(accessKey).enqueue(new Callback<List<Advertise>>() {
            @Override
            public void onResponse(Call<List<Advertise>> call, Response<List<Advertise>> response) {
                if (response.body() != null) {
                    for (Advertise ad : response.body()) {
                        imageList.add(ad.photo.getPath());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Advertise>> call, Throwable t) {

            }
        });


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == imageList.size()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 1500);*/
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        mPager = view.findViewById(R.id.pager);
        indicator = view.findViewById(R.id.indicator);
        recCategories = view.findViewById(R.id.recCategories);
        fabMap = view.findViewById(R.id.fabMap);


        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                getActivity().startActivity(intent);
            }
        });
        recCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        BarberShopCategoryAdapter adapter = new BarberShopCategoryAdapter(getContext(), initProductItems());
        recCategories.setAdapter(adapter);
        recCategories.setNestedScrollingEnabled(false);


        initializeImageSlider();
    }

    private ArrayList<String> initProductItems() {
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(
                "پیشنهادات ویژه",
                "نزدیک‌ترین‌ها",
                "تخفیفات",
                "بیشترین خدمات",
                "جدید"
        ));
        return list;
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
