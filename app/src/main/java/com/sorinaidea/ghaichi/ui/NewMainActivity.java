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

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BarberShopCategoryAdapter;
import com.sorinaidea.ghaichi.adapter.BarberShopMiniItemAdapter;
import com.sorinaidea.ghaichi.adapter.ImageSliderAdapter;
import com.sorinaidea.ghaichi.adapter.main.DiscountBarbershopsAdapter;
import com.sorinaidea.ghaichi.adapter.main.FeaturedBarbershopsAdapter;
import com.sorinaidea.ghaichi.adapter.main.NearestBarbershopsAdapter;
import com.sorinaidea.ghaichi.adapter.main.NewBarbershopsAdapter;
import com.sorinaidea.ghaichi.adapter.main.TopBarbershopsAdapter;
import com.sorinaidea.ghaichi.fast.Advertise;
import com.sorinaidea.ghaichi.fast.BarbershopCard;
import com.sorinaidea.ghaichi.fast.UserShortInfo;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.GhaichiPrefrenceManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.AdvertisesService;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;
import com.sorinaidea.ghaichi.webservice.UserProfileService;

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

public class NewMainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TextView txtUserInfo;
    private AppCompatImageView imgProfileImage;
    private TextView txtCity;
    private Typeface fontIranSans;


    private TextView btnMoreFeatured;
    private TextView btnMoreDiscount;
    private TextView btnMoreNew;
    private TextView btnMoreNearest;
    private TextView btnMoreTop;


    private RecyclerView recTop;
    private RecyclerView recFeatured;
    private RecyclerView recDiscount;
    private RecyclerView recNew;
    private RecyclerView recNearest;


    private DiscountBarbershopsAdapter recDiscountAdapter;
    private FeaturedBarbershopsAdapter recFeaturedAdapter;
    private NewBarbershopsAdapter recNewAdapter;
    private NearestBarbershopsAdapter recNearestAdapter;
    private TopBarbershopsAdapter recTopAdapter;

    private TextView txtTop;
    private TextView txtFeatured;
    private TextView txtDiscount;
    private TextView txtNearest;
    private TextView txtNew;


    private ViewPager mPager;

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
            Intent intent = new Intent(NewMainActivity.this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_credit) {
            Intent intent = new Intent(NewMainActivity.this, CreditActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_favorites) {
            Intent intent = new Intent(NewMainActivity.this, BookmarksActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_free_reservation) {
            Intent intent = new Intent(NewMainActivity.this, GetGiftActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_aboutus) {
            Intent intent = new Intent(NewMainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_faq) {
            Intent intent = new Intent(NewMainActivity.this, FaqActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_profile) {
            Intent intent = new Intent(NewMainActivity.this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_logout) {

            Call<com.sorinaidea.ghaichi.webservice.model.responses.Response> info =
                    API.getRetrofit().create(UserProfileService.class).logout(Util.getAccessKey(getApplicationContext()));

            info.enqueue(new Callback<com.sorinaidea.ghaichi.webservice.model.responses.Response>() {
                @Override
                public void onResponse(Call<com.sorinaidea.ghaichi.webservice.model.responses.Response> call, Response<com.sorinaidea.ghaichi.webservice.model.responses.Response> response) {
                    if (response.isSuccessful()) {
                        if (!response.body().hasError()) {
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
                    } else {

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
                            API.getPicasso(getApplicationContext())
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
        setContentView(R.layout.activity_main_new_design);

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

        btnMoreFeatured = (TextView) findViewById(R.id.btnMoreFeatured);
        btnMoreDiscount = (TextView) findViewById(R.id.btnMoreDiscount);
        btnMoreNew = (TextView) findViewById(R.id.btnMoreNew);
        btnMoreNearest = (TextView) findViewById(R.id.btnMoreNearest);
        btnMoreTop = (TextView) findViewById(R.id.btnMoreTop);


        btnMoreFeatured.setOnClickListener((view) -> {
            Intent intent = new Intent(getApplicationContext(), BarberShopGridActivity.class);
            intent.putExtra("TITLE", "پیشنهادات ویژه");
            startActivity(intent);
        });
        btnMoreDiscount.setOnClickListener((view) -> {
            Intent intent = new Intent(getApplicationContext(), BarberShopGridActivity.class);
            intent.putExtra("TITLE", "در تخفیف");
            startActivity(intent);
        });
        btnMoreNew.setOnClickListener((view) -> {
            Intent intent = new Intent(getApplicationContext(), BarberShopGridActivity.class);
            intent.putExtra("TITLE", "جدیدترین‌ها");
            startActivity(intent);
        });
        btnMoreNearest.setOnClickListener((view) -> {
            Intent intent = new Intent(getApplicationContext(), BarberShopGridActivity.class);
            intent.putExtra("TITLE", "نزدیک‌ترین‌ها");
            startActivity(intent);
        });
        btnMoreTop.setOnClickListener((view) -> {
            Intent intent = new Intent(getApplicationContext(), BarberShopGridActivity.class);
            intent.putExtra("TITLE", "برترین‌ها");
            startActivity(intent);
        });

        txtUserInfo.setText("کاربر مهمان");
        txtCity.setText("سنندج");

        mPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);


        recTop = (RecyclerView) findViewById(R.id.recTop);
        recFeatured = (RecyclerView) findViewById(R.id.recFeatured);
        recDiscount = (RecyclerView) findViewById(R.id.recDiscount);
        recNew = (RecyclerView) findViewById(R.id.recNew);
        recNearest = (RecyclerView) findViewById(R.id.recNearest);

        recTop.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recFeatured.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recDiscount.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recNew.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recNearest.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));


        scrTop = Skeleton.bind(recTop).adapter(recTopAdapter).load(R.layout.bshops_top).show();
        scrFeatured = Skeleton.bind(recFeatured).adapter(recFeaturedAdapter).load(R.layout.bshops_featured).show();
        scrDiscount = Skeleton.bind(recDiscount).adapter(recDiscountAdapter).load(R.layout.bshops_discount).show();
        scrNew = Skeleton.bind(recNew).adapter(recNewAdapter).load(R.layout.bshops_new).show();
        scrNearest = Skeleton.bind(recNearest).adapter(recNearestAdapter).load(R.layout.bshops_nearest).show();


//        BarberShopCategoryAdapter adapter = new BarberShopCategoryAdapter(getApplicationContext(), initProductItems());
//        recCategories.setAdapter(adapter);
//        recCategories.setNestedScrollingEnabled(false);


        initializeImageSlider();
        updateNavigationHeader();

        FontManager.setFont(txtCity, fontIranSans);
        FontManager.setFont(txtUserInfo, fontIranSans);
        loadData();
    }

    SkeletonScreen scrTop;
    SkeletonScreen scrFeatured;
    SkeletonScreen scrDiscount;
    SkeletonScreen scrNew;
    SkeletonScreen scrNearest;

    private void reload() {
        recTopAdapter = new TopBarbershopsAdapter(getApplicationContext(), barbershops);
        recFeaturedAdapter = new FeaturedBarbershopsAdapter(getApplicationContext(), barbershops);
        recDiscountAdapter = new DiscountBarbershopsAdapter(getApplicationContext(), barbershops);
        recNewAdapter = new NewBarbershopsAdapter(getApplicationContext(), barbershops);
        recNearestAdapter = new NearestBarbershopsAdapter(getApplicationContext(), barbershops);

//
//
//
//        scrTop.hide();
//        scrFeatured.hide();
//        scrDiscount.hide();
//        scrNew.hide();
//        scrNearest.hide();
//
        recTop.setAdapter(recTopAdapter);
        recFeatured.setAdapter(recFeaturedAdapter);
        recDiscount.setAdapter(recDiscountAdapter);
        recNew.setAdapter(recNewAdapter);
        recNearest.setAdapter(recNearestAdapter);


        recTopAdapter.notifyDataSetChanged();
        recFeaturedAdapter.notifyDataSetChanged();
        recDiscountAdapter.notifyDataSetChanged();
        recNewAdapter.notifyDataSetChanged();
        recNearestAdapter.notifyDataSetChanged();


    }

    private ArrayList<BarbershopCard> barbershops;

    private void loadData() {
        Retrofit retrofit = API.getRetrofit();

        BarbershopServices service = retrofit.create(BarbershopServices.class);

        Call<List<BarbershopCard>> barbershopCall = service.barbershopsCards(Util.getAccessKey(NewMainActivity.this));

        barbershopCall.enqueue(new Callback<List<BarbershopCard>>() {
            @Override
            public void onResponse(Call<List<BarbershopCard>> call, Response<List<BarbershopCard>> response) {
                if (response.body() != null) {
                    barbershops = new ArrayList<>();
                    barbershops.addAll(response.body());
                    reload();
                }
                Log.d("BARBERSHOP", call.isCanceled() + " C E " + call.isExecuted() + " " + response);
            }

            @Override
            public void onFailure(Call<List<BarbershopCard>> call, Throwable t) {
                Log.d("FAILED", t.toString());
            }
        });
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
        getMenuInflater().inflate(R.menu.main_map_added, menu);
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
            Intent intent = new Intent(NewMainActivity.this, ReservationActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_map) {

            Intent intent = new Intent(NewMainActivity.this, MapsActivity.class);
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
