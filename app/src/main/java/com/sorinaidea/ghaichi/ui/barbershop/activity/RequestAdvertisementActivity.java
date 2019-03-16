package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.barbershop.SCDPricesAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Pricing;
import com.sorinaidea.ghaichi.ui.ImageUploaderActivity;
import com.sorinaidea.ghaichi.ui.barbershop.fragment.BannerAdvertiseFragment;
import com.sorinaidea.ghaichi.ui.barbershop.fragment.SpecialAdvertiseFragment;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.AdvertiseServices;
import com.sorinaidea.ghaichi.webservice.image.UploadTask;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAdvertisementActivity extends ImageUploaderActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Pricing> listPricing;
    private Pricing selectedPricing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise);
        listPricing = new ArrayList<>();

        if (savedInstanceState == null || !savedInstanceState.containsKey("pricing")) {
            fetchAdvertisePricingList();
        } else {
            listPricing = savedInstanceState.getParcelableArrayList("pricing");
        }

        initToolbar(R.string.toolbar_request_advertise, true, true);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("pricing", listPricing);
        super.onSaveInstanceState(outState);
    }

    public void fetchAdvertisePricingList() {
        showProgressDialog(null, "در حال دریافت لیست قیمت‌ها", false);
        AdvertiseServices service = API.getRetrofit().create(AdvertiseServices.class);
        service.fetchPricingList(Auth.getAccessKey(this)).enqueue(new Callback<List<Pricing>>() {
            @Override
            public void onResponse(Call<List<Pricing>> call, Response<List<Pricing>> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    listPricing.clear();
                    try {
                        List<Pricing> result = response.body();
                        Objects.requireNonNull(result);
                        listPricing.addAll(result);
                    } catch (NullPointerException ex) {
                        alert("هشدار", "اطلاعات به درستی بارگزاری نشده اند، لطفا مجددا سعی نمایید.", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pricing>> call, Throwable t) {
                hideProgressDialog();
                if (t instanceof IOException) {
                    toast(R.string.err_unable_to_connect_to_server);
                }
                logVerbose(t.getMessage(), t);
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        BannerAdvertiseFragment bannerAdvertiseFragment = new BannerAdvertiseFragment();
        SpecialAdvertiseFragment specialAdvertiseFragment = new SpecialAdvertiseFragment();

        bannerAdvertiseFragment.setPricingClickListener(view -> showPricingList(bannerAdvertiseFragment));
        specialAdvertiseFragment.setPricingClickListener(view -> showPricingList(specialAdvertiseFragment));

        adapter.addFragment(bannerAdvertiseFragment, getString(R.string.banner_advertises));
        adapter.addFragment(specialAdvertiseFragment, getString(R.string.special_advertises));
        viewPager.setAdapter(adapter);
    }

    private int[] tabIcons = {
            R.drawable.ic_photo_white_18dp,
            R.drawable.ic_star_white_24dp
    };


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }


    private void showPricingList(final UiUpdater updater) {
        RecyclerView recPricing;
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_single_choice, null);
        recPricing = dialogView.findViewById(R.id.recItems);
        recPricing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        final LovelyCustomDialog selectPricingDialog = new LovelyCustomDialog(this)
                .setView(dialogView)
                .setTopColorRes(R.color.colorGold)
                .setTitle(R.string._lbl_select_pricing)
                .setIcon(R.drawable.ic_attach_money)
                .setMessage("تعداد بازدید مورد نظر خود را انتخاب کنید")
                .configureMessageView(this::applyTextFont)
                .configureTitleView(this::applyTextFont);

        SCDPricesAdapter adapter = new SCDPricesAdapter(listPricing, this) {
            @Override
            protected void checked(Pricing price) {
                selectedPricing = price;
                selectPricingDialog.dismiss();
                updater.update(price.getDescription());
            }
        };
        recPricing.setAdapter(adapter);
        selectPricingDialog.show();

    }

    @Override
    protected UploadTask generateTask(File... files) throws NullPointerException {
        return null;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
