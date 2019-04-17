package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.ImageSliderAdapter;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.adapter.main.DiscountBarbershopsAdapter;
import com.sorinaidea.ghaichi.adapter.main.FeaturedBarbershopsAdapter;
import com.sorinaidea.ghaichi.adapter.main.NearestBarbershopsAdapter;
import com.sorinaidea.ghaichi.adapter.main.NewBarbershopsAdapter;
import com.sorinaidea.ghaichi.adapter.main.TopBarbershopsAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Data;
import com.sorinaidea.ghaichi.models.HomeData;
import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.AdvertisesService;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;
import com.sorinaidea.ghaichi.webservice.ProfileServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import co.ronash.pushe.Pushe;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mr-code on 4/8/2018.
 */

public class NewMainActivity extends ToolbarActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private TextView txtUserInfo;
    private AppCompatImageView imgProfileImage;
    private TextView txtCity;


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
    private ScrollView scrViewRoot;

    private static int currentPage = 0;

    private ArrayList<Image> imageList;

    private void initializeImageSlider() {


        AdvertisesService advertises = API.getRetrofit(this).create(AdvertisesService.class);

        ImageSliderAdapter adapter = new ImageSliderAdapter(getApplicationContext(), imageList);
        mPager.setAdapter(adapter);

        advertises.advertises().enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (response.body() != null) {
                    imageList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    indicator.setViewPager(mPager);
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {

            }
        });

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            synchronized public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == imageList.size()) {
                currentPage = 0;
            }
            mPager.setCurrentItem(currentPage++, true);
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

        // TODO Enable Settings activity
//        if (id == R.id.action_setting) {
//            Intent intent = new Intent(NewMainActivity.this, SettingActivity.class);
//            startActivity(intent);
//        }

        // TODO Enable Credits activity
//        if (id == R.id.action_credit) {
//            Intent intent = new Intent(NewMainActivity.this, CreditActivity.class);
//            startActivity(intent);
//        }


        if (id == R.id.action_favorites) {
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
            confirmAlert(
                    "خروج از حساب کاربری",
                    "آیا می‌خواهید از حساب کاربری خود خارج شوید؟",
                    R.drawable.ic_exit_to_app_black_18dp,
                    R.color.colorAmberAccent200,
                    view -> Auth.logout(this)
            );
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateNavigationHeader(List<Data> info) {

        for (Data i : info) {
            if (i.getKeyEn().equals("city")) {
                txtCity.setText(i.getValue());
            }
            if (i.getKeyEn().equals("avatar")) {
                API.getPicasso(this)
                        .load(i.getValue())
                        .centerCrop()
                        .fit()
                        .into(imgProfileImage);
            }
        }

        StringBuilder str = new StringBuilder();
        for (Data i : info) {
            if (i.getKeyEn().equals("name") || i.getKeyEn().equals("family"))
                str.append(i.getValue()).append(" ");
        }

        txtUserInfo.setText(str.toString());
    }

    private void updateNavigationHeader() {
        API.getRetrofit(this)
                .create(ProfileServices.class)
                .home()
                .enqueue(new Callback<List<Data>>() {
                    @Override
                    public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                        if (response.isSuccessful()) {
                            try {
                                updateNavigationHeader(Objects.requireNonNull(response.body()));
                            } catch (NullPointerException ignore) {
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Data>> call, Throwable t) {

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new_design);

        Pushe.initialize(this, true);

        initToolbar(R.string.app_name, false, false, true);

        drawer = findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_menu_open, R.string.navigation_menu_close);
        drawer.setDrawerListener(toggle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navView);

        imgProfileImage = navigationView.getHeaderView(0).findViewById(R.id.imgProfileImage);
        txtUserInfo = navigationView.getHeaderView(0).findViewById(R.id.txtUserInfo);
        txtCity = navigationView.getHeaderView(0).findViewById(R.id.txtCity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            navigationView.setLayoutDirection(NavigationView.LAYOUT_DIRECTION_RTL);
        }
        navigationView.setNavigationItemSelectedListener(this);
        disableNavigationViewScrollbars(navigationView);

        scrViewRoot = findViewById(R.id.scrViewRoot);
        btnMoreFeatured = findViewById(R.id.btnMoreFeatured);
        btnMoreDiscount = findViewById(R.id.btnMoreDiscount);
        btnMoreNew = findViewById(R.id.btnMoreNew);
        btnMoreNearest = findViewById(R.id.btnMoreNearest);
        btnMoreTop = findViewById(R.id.btnMoreTop);
        txtTop = findViewById(R.id.txtTop);
        txtFeatured = findViewById(R.id.txtFeatured);
        txtDiscount = findViewById(R.id.txtDiscount);
        txtNearest = findViewById(R.id.txtNearest);
        txtNew = findViewById(R.id.txtNew);

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

        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);


        recTop = findViewById(R.id.recTop);
        recFeatured = findViewById(R.id.recFeatured);
        recDiscount = findViewById(R.id.recDiscount);
        recNew = findViewById(R.id.recNew);
        recNearest = findViewById(R.id.recNearest);

        recTop.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recFeatured.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recFeatured.addItemDecoration(new ItemOffsetDecoration(getApplicationContext(), R.dimen._8dp));
        recDiscount.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recNew.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recNearest.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        imageList = new ArrayList<>();
        initializeImageSlider();

        updateNavigationHeader();

        applyTextFont(
                txtCity,
                txtUserInfo,
                btnMoreFeatured,
                btnMoreDiscount,
                btnMoreNew,
                btnMoreNearest,
                btnMoreTop,
                txtTop,
                txtFeatured,
                txtDiscount,
                txtNearest,
                txtNew
        );


        recDiscount.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
        loadData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (barbershops != null) {
            loadData();
            updateNavigationHeader();
        }
    }

    private void reload() {
        recTopAdapter = new TopBarbershopsAdapter(barbershops.getBests(), getApplicationContext());
        recFeaturedAdapter = new FeaturedBarbershopsAdapter(barbershops.getFeatured(), getApplicationContext());
        recDiscountAdapter = new DiscountBarbershopsAdapter(barbershops.getDiscounts(), getApplicationContext());
        recNewAdapter = new NewBarbershopsAdapter(barbershops.getNewest(), getApplicationContext());
        recNearestAdapter = new NearestBarbershopsAdapter(barbershops.getNearest(), getApplicationContext());
        recTop.setAdapter(recTopAdapter);
        recFeatured.setAdapter(recFeaturedAdapter);
        recDiscount.setAdapter(recDiscountAdapter);
        recNew.setAdapter(recNewAdapter);
        recNearest.setAdapter(recNearestAdapter);
    }

    HomeData barbershops;

    private void loadData() {
        barbershops = new HomeData();
        BarbershopServices service = API.getRetrofit(this).create(BarbershopServices.class);
        Call<HomeData> barbershopCall = service.barbershopsCards();
        showProgress();
        barbershopCall.enqueue(new Callback<HomeData>() {
            @Override
            public void onResponse(Call<HomeData> call, Response<HomeData> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    try {
                        barbershops = Objects.requireNonNull(response.body());
                        reload();
                    } catch (NullPointerException ignore) {
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeData> call, Throwable t) {
                hideProgress();
                if (t instanceof IOException) {
                    toast("خطا در برقراری ارتباط با سرور");
                }
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
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                super.onBackPressed();
            }
        }
    }
}
