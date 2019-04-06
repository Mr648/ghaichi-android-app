package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.barbershop.SCDPricesAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Pricing;
import com.sorinaidea.ghaichi.ui.ImageUploaderActivity;
import com.sorinaidea.ghaichi.ui.barbershop.fragment.AdvertiseFragment;
import com.sorinaidea.ghaichi.ui.barbershop.fragment.BannerAdvertiseFragment;
import com.sorinaidea.ghaichi.ui.barbershop.fragment.SpecialAdvertiseFragment;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.AdvertiseServices;
import com.sorinaidea.ghaichi.webservice.image.UploadTask;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAdvertisementActivity extends ImageUploaderActivity implements BannerAdvertiseFragment.ImagePicker, AdvertiseFragment.Advertiser {

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
                updater.update(price);
            }
        };
        recPricing.setAdapter(adapter);
        selectPricingDialog.show();

    }

    @Override
    protected UploadTask generateTask(File... files) throws NullPointerException {
        try {
            Objects.requireNonNull(files);
            banner = files[0];
            final int MAX_WIDTH = 1024;
            final int MAX_HEIGHT = 768;

            int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
            API.getPicasso(this)
                    .load(banner)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.banner_image_placeholder)
                    .error(R.drawable.banner_image_placeholder)
                    .into(into);
        } catch (NullPointerException ignored) {
        }
        return null;
    }

    AppCompatImageView into;
    File banner;

    @Override
    public void pick(AppCompatImageView into) {
        this.into = into;
        pickSingleImage();
    }

    @Override
    public File picked() {
        try {
            return Objects.requireNonNull(banner).exists() ? banner : null;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    @Override
    public void requestBannerAdvertise(@NonNull File image, @NonNull Pricing price, @NonNull String description) {
        confirmAlert("درخواست تبلیغ بنری", "درخواست تبلیغ ثبت شود؟", R.drawable.advertising, R.color.colorGreenAccent200, view -> {
            AdvertiseServices advertiseServices = API.getRetrofit().create(AdvertiseServices.class);

            showProgress();

            MultipartBody.Part img = MultipartBody.Part.createFormData("advertise", image.getName(),
                    RequestBody.create(MediaType.parse("image/*"), image));

            advertiseServices.requestBannerAdvertise(Auth.getAccessKey(this), img, description, price.getId())
                    .enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
                        @Override
                        public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, Response<com.sorinaidea.ghaichi.models.Response> response) {
                            hideProgress();
                            if (response.isSuccessful()) {
                                try {
                                    toast(Objects.requireNonNull(response.body()).getMessage());
                                    return;
                                } catch (NullPointerException ignore) {
                                }
                            }
                            toast("خطایی رخ داده است.");
                        }

                        @Override
                        public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                            hideProgress();
                            if (t instanceof IOException) {
                                toast("خطا در ارتباط با سرور");
                            }
                        }
                    });
        });
    }

    @Override
    public void requestSpecialAdvertise(@NonNull Pricing price, @NonNull String description) {
        confirmAlert("درخواست تبلیغ ویژه", "درخواست تبلیغ ثبت شود؟", R.drawable.advertising, R.color.colorGreenAccent200, view -> {
            AdvertiseServices advertiseServices = API.getRetrofit().create(AdvertiseServices.class);
            showProgress();
            advertiseServices.requestSpecialAdvertise(Auth.getAccessKey(this), description, price.getId())
                    .enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
                        @Override
                        public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, Response<com.sorinaidea.ghaichi.models.Response> response) {
                            hideProgress();
                            if (response.isSuccessful()) {
                                try {
                                    toast(Objects.requireNonNull(response.body()).getMessage());
                                    return;
                                } catch (NullPointerException ignore) {
                                }
                            }
                            toast("خطایی رخ داده است.");
                        }

                        @Override
                        public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                            hideProgress();
                            if (t instanceof IOException) {
                                toast("خطا در ارتباط با سرور");
                            }
                        }
                    });
        });
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
