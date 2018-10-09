package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BarberShopCategoryAdapter;
import com.sorinaidea.ghaichi.adapter.ImageSliderAdapter;
import com.sorinaidea.ghaichi.fast.Advertise;
import com.sorinaidea.ghaichi.fast.UserShortInfo;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.GhaichiPrefrenceManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.AdvertisesService;
import com.sorinaidea.ghaichi.webservice.UserProfileService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URLDecoder;
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

/**
 * Created by mr-code on 4/8/2018.
 */

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TextView txtUserInfo;
    private AppCompatImageView imgProfileImage;
    private TextView txtCity;
    private Typeface fontIranSans;
    private RecyclerView recCategories;
    private ViewPager mPager;
    private FloatingActionButton fabMap;
    private CircleIndicator indicator;
    private DrawerLayout drawer;

    private static int currentPage = 0;

    private ArrayList<String> imageList = new ArrayList<>();

    private void initializeImageSlider() {


        Retrofit retrofit = API.getRetrofit();
        AdvertisesService advertises = retrofit.create(AdvertisesService.class);

        String accessKey = Util.getAccessKey(getApplicationContext());

        ImageSliderAdapter adapter = new ImageSliderAdapter(getApplicationContext(), imageList);
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
        }, 1000, 3000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_credit) {
            Intent intent = new Intent(MainActivity.this, CreditActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_favorites) {
            Intent intent = new Intent(MainActivity.this, BookmarksActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_free_reservation) {
            Intent intent = new Intent(MainActivity.this, GetGiftActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_aboutus) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_faq) {
            Intent intent = new Intent(MainActivity.this, FaqActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_profile) {
            Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_logout) {

            Call<com.sorinaidea.ghaichi.webservice.model.responses.Response> info =
                    API.getRetrofit().create(UserProfileService.class).logout(Util.getAccessKey(getApplicationContext()));

            info.enqueue(new Callback<com.sorinaidea.ghaichi.webservice.model.responses.Response>() {
                @Override
                public void onResponse(Call<com.sorinaidea.ghaichi.webservice.model.responses.Response> call, Response<com.sorinaidea.ghaichi.webservice.model.responses.Response> response) {
                    if (response.isSuccessful()){
                        if (!response.body().hasError()){
                            // Remove Keys
                            GhaichiPrefrenceManager.removeKey(getApplicationContext(), Util.md5(Util.PREFRENCES_KEYS.USER_ACCESS_KEY));
                            GhaichiPrefrenceManager.removeKey(getApplicationContext(), Util.md5(Util.PREFRENCES_KEYS.USER_ROLE));

                            // Exit From Application
                            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                            homeIntent.addCategory(Intent.CATEGORY_HOME);
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeIntent);

                            finish();
                        }
                    }else{

                    }
                }

                @Override
                public void onFailure(Call<com.sorinaidea.ghaichi.webservice.model.responses.Response> call, Throwable t) {
                }
            });


        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateNavigationHeader() {
        Call<UserShortInfo> info = API.getRetrofit().create(UserProfileService.class).shortInfo(Util.getAccessKey(getApplicationContext()));

        info.enqueue(new Callback<UserShortInfo>() {
            @Override
            public void onResponse(Call<UserShortInfo> call, Response<UserShortInfo> response) {
                if (response.body() != null) {

                    UserShortInfo info = response.body();
                    if (info.getImage() != null) {
                        try {
                            Picasso.with(getApplicationContext())
                                    .load(API.BASE_URL
                                            + URLDecoder.decode(info.getImage(), "UTF-8"))
                                    .centerCrop()
                                    .fit()
                                    .into(imgProfileImage);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    txtUserInfo.setText(info.getName() + " " + info.getFamily());
                    txtCity.setText("سنندج");
                }
            }

            @Override
            public void onFailure(Call<UserShortInfo> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        fontIranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_menu_open, R.string.navigation_menu_close);
        drawer.setDrawerListener(toggle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);


        txtUserInfo = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtUserInfo);
        imgProfileImage = (AppCompatImageView) navigationView.getHeaderView(0).findViewById(R.id.imgProfileImage);
        txtCity = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtCity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            navigationView.setLayoutDirection(NavigationView.LAYOUT_DIRECTION_RTL);
        }
        navigationView.setNavigationItemSelectedListener(this);
        disableNavigationViewScrollbars(navigationView);


        txtUserInfo.setText("کاربر مهمان");
        txtCity.setText("سنندج");

        mPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        recCategories = (RecyclerView) findViewById(R.id.recCategories);
        fabMap = (FloatingActionButton) findViewById(R.id.fabMap);


        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        recCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        BarberShopCategoryAdapter adapter = new BarberShopCategoryAdapter(getApplicationContext(), initProductItems());
        recCategories.setAdapter(adapter);
        recCategories.setNestedScrollingEnabled(false);


        initializeImageSlider();
        updateNavigationHeader();

        FontManager.setFont(txtCity, fontIranSans);
        FontManager.setFont(txtUserInfo, fontIranSans);

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

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_reservations) {
            Intent intent = new Intent(MainActivity.this, ReservationActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportActionBar().setTitle("قیچی");
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                super.onBackPressed();
            }
        }
    }


}
