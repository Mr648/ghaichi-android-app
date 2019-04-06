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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.ImageSliderAdapter;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.adapter.barbershop.BarbershopReservationsAdabper;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Data;
import com.sorinaidea.ghaichi.models.Image;
import com.sorinaidea.ghaichi.ui.barbershop.activity.BannersActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.BarbersActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.BarbershopProfileActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.BusinessTimesActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.CategoriesActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.PaymentActivity;
import com.sorinaidea.ghaichi.ui.barbershop.activity.ServicesActivity;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.AdvertisesService;
import com.sorinaidea.ghaichi.webservice.ProfileServices;

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

    private CardView cardCategory;
    private CardView cardServices;
    private CardView cardBarbers;


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
        setContentView(R.layout.activity_barbershop_admin);
        Pushe.initialize(this, true);
        setupUi();
        initToolbar(R.string.toolbar_app_name, true, false, true);
        TextView txtSubTitle = toolbar.findViewById(R.id.toolbar_subtitle);

        scrViewRoot = findViewById(R.id.scrViewRoot);

        cardCategory = findViewById(R.id.cardCategory);
        cardServices = findViewById(R.id.cardServices);
        cardBarbers = findViewById(R.id.cardBarbers);

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

        recReservations = findViewById(R.id.recReservations);
        recReservations.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recReservations.addItemDecoration(new ItemOffsetDecoration(Util.dp(8, this)));
        recReservations.setAdapter(new BarbershopReservationsAdabper(getApplicationContext()));
        recReservations.setNestedScrollingEnabled(false);

        drawer = findViewById(R.id.drawerLayout);
        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_menu_open, R.string.navigation_menu_close);

        recReservations.setFocusable(false);

        scrViewRoot.fullScroll(ScrollView.FOCUS_UP);
        scrViewRoot.smoothScrollTo(0, 0);

        drawer.setDrawerListener(toggle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toggle.syncState();

        navigationView = findViewById(R.id.navView);

        imgProfileImage = navigationView.getHeaderView(0).findViewById(R.id.imgProfileImage);
        txtUserInfo = navigationView.getHeaderView(0).findViewById(R.id.txtUserInfo);
        txtCity = navigationView.getHeaderView(0).findViewById(R.id.txtCity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            navigationView.setLayoutDirection(NavigationView.LAYOUT_DIRECTION_RTL);
        }

        navigationView.setNavigationItemSelectedListener(this);
        disableNavigationViewScrollbars(navigationView);

        txtUserInfo.setText("کاربر آرایشگر");
        txtCity.setText("سنندج");

        imageList = new ArrayList<>();

        initializeImageSlider();
        updateNavigationHeader();
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
        API.getRetrofit()
                .create(ProfileServices.class)
                .home(Auth.getAccessKey(getApplicationContext()))
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

    private void initializeImageSlider() {


        AdvertisesService advertises = API.getRetrofit().create(AdvertisesService.class);

        ImageSliderAdapter adapter = new ImageSliderAdapter(getApplicationContext(), imageList);
        mPager.setAdapter(adapter);

        advertises.advertises(Auth.getAccessKey(getApplicationContext())).enqueue(new Callback<List<Image>>() {
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
            Intent intent = new Intent(BarberMainActivity.this, AdvertismentActivity.class);
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
