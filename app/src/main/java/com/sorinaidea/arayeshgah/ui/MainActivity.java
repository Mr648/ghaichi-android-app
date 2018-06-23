package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.layout.HomePageFragment;
import com.sorinaidea.arayeshgah.layout.UserReservationFragment;

import org.w3c.dom.Text;

/**
 * Created by mr-code on 4/8/2018.
 */

public class MainActivity extends SorinaActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TextView txtUserInfo;
    private TextView txtCity;
    private AppCompatImageView imgProfile;

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
//            Intent intent = new Intent(MainActivity.this, CreditActivity.class);
//            startActivity(intent);
        } else if (id == R.id.action_free_reservation) {
            Intent intent = new Intent(MainActivity.this, GetGiftActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_aboutus) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_faq) {
            Intent intent = new Intent(MainActivity.this, FaqActivity.class);
            startActivity(intent);
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_menu_open, R.string.navigation_menu_close);
        drawer.setDrawerListener(toggle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);


        txtUserInfo = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtUserInfo);
        txtCity = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtCity);
        imgProfile = (AppCompatImageView) navigationView.getHeaderView(0).findViewById(R.id.imgProfile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            navigationView.setLayoutDirection(NavigationView.LAYOUT_DIRECTION_RTL);
        }
        navigationView.setNavigationItemSelectedListener(this);
        disableNavigationViewScrollbars(navigationView);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.scale_up, R.anim.scale_down);
        ft.add(R.id.content, new HomePageFragment()).commit();

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
        txtUserInfo.setText("کاربر مهمان");
        txtCity.setText("سنندج");

    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }


//    @Override
//    public void onRegistrationFirstStepInteraction() {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.setCustomAnimations(R.anim.scale_up, R.anim.scale_down);
//        ft.replace(R.id.content, new HomeFragment()).commit();
//    }
//
//

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
