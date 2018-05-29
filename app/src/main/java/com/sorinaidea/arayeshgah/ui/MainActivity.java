package com.sorinaidea.arayeshgah.ui;

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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.layout.AboutUsFragment;
import com.sorinaidea.arayeshgah.layout.CreditFragment;
import com.sorinaidea.arayeshgah.layout.FaqFragment;
import com.sorinaidea.arayeshgah.layout.GetGiftFragment;
import com.sorinaidea.arayeshgah.layout.HomePageFragment;
import com.sorinaidea.arayeshgah.layout.LoginFragment;
import com.sorinaidea.arayeshgah.layout.ReservationFragment;
import com.sorinaidea.arayeshgah.layout.SettingFragment;
import com.sorinaidea.arayeshgah.layout.UserReservationFragment;

/**
 * Created by mr-code on 4/8/2018.
 */

public class MainActivity extends SorinaActivity implements
        LoginFragment.OnLoginInteraction,
        NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {

            gotoFragment(new SettingFragment());
        } else if (id == R.id.action_credit) {
            gotoFragment(new CreditFragment());
        } else if (id == R.id.action_top_barbershops) {

        } else if (id == R.id.action_favorites) {

        } else if (id == R.id.action_free_reservation) {
            gotoFragment(new GetGiftFragment());
        } else if (id == R.id.action_aboutus) {
            gotoFragment(new AboutUsFragment());
        } else if (id == R.id.action_faq) {
            gotoFragment(new FaqFragment());

        } else if (id == R.id.action_send_support_ticket) {

        } else if (id == R.id.action_report_bug) {

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void gotoFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.scale_up, R.anim.scale_down);
        ft.replace(R.id.content, fragment).commit();
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            navigationView.setLayoutDirection(NavigationView.LAYOUT_DIRECTION_RTL);
        }
        navigationView.setNavigationItemSelectedListener(this);
        disableNavigationViewScrollbars(navigationView);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.scale_up, R.anim.scale_down);
        ft.add(R.id.content, new HomePageFragment()).commit();
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);


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
            gotoFragment(new UserReservationFragment());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void login() {

    }
}
