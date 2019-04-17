package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.ImageSliderAdapter;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.adapter.ReservationAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Data;
import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.models.Reserve;
import com.sorinaidea.ghaichi.ui.barbershop.activity.BannersActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.BarbersActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.BarbershopProfileActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.BusinessTimesActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.CategoriesActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.PaymentActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.ReservationActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.ServicesActivity;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.AdvertisesService;
import com.sorinaidea.ghaichi.webservice.ProfileServices;
import com.sorinaidea.ghaichi.webservice.barbershop.ReserveServices;

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

public class BarberMainActivity extends ToolbarActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    AppCompatImageView imgProfileImage;
    private TextView txtUserInfo;
    private TextView txtCity;
    private Typeface fontIranSans;


    private DrawerLayout drawer;
    RecyclerView recReservations;
    private ViewPager mPager;
    private CircleIndicator indicator;
    private static int currentPage = 0;
    private NestedScrollView scrViewRoot;
    private SwipeRefreshLayout swiper;

    private CardView cardCategory;
    private CardView cardServices;
    private CardView cardBarbers;


    TextView txtSubTitle;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txtTotalReserves;
    TextView txtMorningReserves;
    TextView txtEveningReserves;

    TextView txtCategories;
    TextView txtServices;
    TextView txtBarbers;

    TextView txtListOfReservations;

    NavigationView navigationView;

    private void setupUi() {
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txtTotalReserves = findViewById(R.id.txtTotalReserves);
        txtMorningReserves = findViewById(R.id.txtMorningReserves);
        txtEveningReserves = findViewById(R.id.txtEveningReserves);
        txtCategories = findViewById(R.id.txtCategories);
        txtServices = findViewById(R.id.txtServices);
        txtBarbers = findViewById(R.id.txtBarbers);
        txtListOfReservations = findViewById(R.id.txtListOfReservations);
        txtSubTitle = toolbar.findViewById(R.id.toolbar_subtitle);
        scrViewRoot = findViewById(R.id.scrViewRoot);
        swiper = findViewById(R.id.swiper);
        cardCategory = findViewById(R.id.cardCategory);
        cardServices = findViewById(R.id.cardServices);
        cardBarbers = findViewById(R.id.cardBarbers);
        drawer = findViewById(R.id.drawerLayout);
        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);
        recReservations = findViewById(R.id.recReservations);
        navigationView = findViewById(R.id.navView);


        applyTextFont(
                txtSubTitle,
                txtCity,
                txtUserInfo,
                txt1,
                txt2,
                txt3,
                txtTotalReserves,
                txtMorningReserves,
                txtEveningReserves,
                txtCategories,
                txtServices,
                txtBarbers,
                txtListOfReservations
        );
    }

//    private void applyMenuFont(){
//        Menu m = navigationView.getMenu();
//        for (int i=0;i<m.size();i++) {
//            MenuItem mi = m.getItem(i);
//
//            //for aapplying a font to subMenu ...
//            SubMenu subMenu = mi.getSubMenu();
//            if (subMenu!=null && subMenu.size() >0 ) {
//                for (int j=0; j <subMenu.size();j++) {
//                    MenuItem subMenuItem = subMenu.getItem(j);
//                    applyTextFont(subMenuItem);
//                }
//            }
//
//            //the method we have create in activity
////            applyFontToMenuItem(mi);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbershop_admin_swipe);
        Pushe.initialize(this, true);

        initToolbar(R.string.toolbar_app_name, true, false, true);

        setupUi();


        cardCategory.setOnClickListener((view) -> {
            Intent intent = new Intent(BarberMainActivity.this, CategoriesActivity.class);
            startActivity(intent);
        });
        cardServices.setOnClickListener((view) -> {
            Intent intent = new Intent(BarberMainActivity.this, ServicesActivity.class);
            startActivity(intent);
        });
        cardBarbers.setOnClickListener((view) -> {
            Intent intent = new Intent(BarberMainActivity.this, BarbersActivity.class);
            startActivity(intent);
        });


        recReservations.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recReservations.addItemDecoration(new ItemOffsetDecoration(Util.dp(8, this)));
        recReservations.setNestedScrollingEnabled(false);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_menu_open, R.string.navigation_menu_close);

        recReservations.setFocusable(false);


        drawer.setDrawerListener(toggle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toggle.syncState();

        imgProfileImage = navigationView.getHeaderView(0).findViewById(R.id.imgProfileImage);
        txtUserInfo = navigationView.getHeaderView(0).findViewById(R.id.txtUserInfo);
        txtCity = navigationView.getHeaderView(0).findViewById(R.id.txtCity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            navigationView.setLayoutDirection(NavigationView.LAYOUT_DIRECTION_RTL);
        }

        navigationView.setNavigationItemSelectedListener(this);
        disableNavigationViewScrollbars(navigationView);

        swiper.setOnRefreshListener(this::reloadReserves);

        initializeImageSlider();
        updateNavigationHeader();
        reloadReserves();
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

    private ArrayList<Image> imageList;

    private void hideToolbar() {
        toolbar.animate().translationY(-256).setInterpolator(new AccelerateInterpolator()).start();
    }

    private void showToolbar() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
    }


    private void loadReserves() {

    }


    private void reloadReserves() {
        API.getRetrofit(this).create(ReserveServices.class)
                .reserves(  null,null)
                .enqueue(new Callback<List<Reserve>>() {
                    @Override
                    public void onResponse(Call<List<Reserve>> call, Response<List<Reserve>> response) {
                        if (response.isSuccessful()) {
                            try {
                                recReservations.setAdapter(new ReservationAdapter(Objects.requireNonNull(response.body()), BarberMainActivity.this));
                                swiper.setRefreshing(false);
                            } catch (NullPointerException ex) {
                                toast("خطا در دریافت اطلاعات");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Reserve>> call, Throwable t) {
                        if (t instanceof IOException)
                            toast(R.string.err_unable_to_connect_to_server);
                    }
                });
    }


    private List<Reserve> initList() {
        List<Reserve> reserves = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            reserves.add(new Reserve());
        }
        return reserves;
    }

    private void initializeImageSlider() {
        imageList = new ArrayList<>();


        ImageSliderAdapter adapter = new ImageSliderAdapter(getApplicationContext(), imageList);
        mPager.setAdapter(adapter);

        API.getRetrofit(this)
                .create(AdvertisesService.class)
                .advertises().enqueue(new Callback<List<Image>>() {
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

    private boolean show = false;

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

// TODO remove comments to activate settings activity.
//        if (id == R.id.action_setting) {
//            Intent intent = new Intent(BarberMainActivity.this, SettingActivity.class);
//            startActivity(intent);
//        }

        if (id == R.id.action_aboutus) {
            Intent intent = new Intent(BarberMainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_credit) {
            Intent intent = new Intent(BarberMainActivity.this, PaymentActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_advertise) {
            Intent intent = new Intent(BarberMainActivity.this, AdvertisementActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_faq) {
            Intent intent = new Intent(BarberMainActivity.this, FaqActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_profile) {
            Intent intent = new Intent(BarberMainActivity.this, UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_barbershop) {
            Intent intent = new Intent(BarberMainActivity.this, BarbershopProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_business) {
            Intent intent = new Intent(BarberMainActivity.this, BusinessTimesActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_reservations) {
            Intent intent = new Intent(BarberMainActivity.this, ReservationActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_banners) {
            Intent intent = new Intent(BarberMainActivity.this, BannersActivity.class);
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

}
