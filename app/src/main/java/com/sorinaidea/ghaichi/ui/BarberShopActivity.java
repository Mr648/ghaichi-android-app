package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BannerAdapter;
import com.sorinaidea.ghaichi.adapter.BarberShopProfileServiceAdapter;
import com.sorinaidea.ghaichi.adapter.BarbersAdapter;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.BannerService;
import com.sorinaidea.ghaichi.models.BarberShortInfo;
import com.sorinaidea.ghaichi.models.BarbershopProfile;
import com.sorinaidea.ghaichi.util.Security;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;
import com.sorinaidea.ghaichi.webservice.UserProfileService;
import com.sorinaidea.ghaichi.webservice.model.responses.IsBookmarked;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by mr-code on 6/17/2018.
 */

public class BarberShopActivity extends ToolbarActivity {

    private ViewPager mPager;
    private ScrollView scrViewRoot;
    private CircleIndicator indicator;

    //    private AppCompatImageView imgComment;

    private CircleImageView imgLogo;
    private TextView txtName;
    private TextView txtServices;
    private TextView txtServicesCount;
    private TextView txtBarbers;
    private TextView txtBarbersCount;
    private TextView txtRating;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txtAbout;
    private TextView txtAddress;

    private LinearLayout rating;
    private RatingBar ratingBar;
    private RecyclerView recServices;
    private RecyclerView recBarbers;
    private int barbershopId;


    private void updateUI(BarbershopProfile barbershop) {

        toolbarTitle.setText(barbershop.getName());
        txtName.setText(barbershop.getName());
        txtAddress.setText(barbershop.getAddress());
        txtAbout.setText(barbershop.getAbout());
        ratingBar.setRating(Float.parseFloat(barbershop.getRating()));
        txtRating.setText(String.format(App.LOCALE, "%.2f", (Float.parseFloat(barbershop.getRating()) * 1.0f)));
        txtServicesCount.setText(String.format(App.LOCALE, "%d", barbershop.getServicesCount()));
        txtBarbersCount.setText(String.format(App.LOCALE, "%d", barbershop.getBarbersCount()));
        initImageSlider(barbershop.getBanners());

        initServices(barbershop.getServices());
        initBarbers(barbershop.getBarbers());

        API.getPicasso(getApplicationContext())
                .load(barbershop.getLogo())
                .centerCrop()
                .fit()
                .placeholder(R.drawable.preview_small)
                .error(R.drawable.preview_small)
                .into(imgLogo);


    }

    private static int currentPage = 0;

    private void initImageSlider(List<String> images) {

        BannerAdapter adapter = new BannerAdapter(getApplicationContext(), images);
        mPager.setAdapter(adapter);
        indicator.setViewPager(mPager);
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
            if (currentPage == images.size()) {
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

    private void getBarbershopInfo(int barbershopId) {

        showProgress();
        API.getRetrofit(this).create(BarbershopServices.class)
                .barbershop(  barbershopId)
                .enqueue(new Callback<BarbershopProfile>() {
                    @Override
                    public void onResponse(Call<BarbershopProfile> call, Response<BarbershopProfile> response) {
                        hideProgress();
                        if (response.isSuccessful()) {
                            try {
                                updateUI(Objects.requireNonNull(response.body()));
                            } catch (NullPointerException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BarbershopProfile> call, Throwable t) {
                        hideProgress();
                        if (t instanceof IOException)
                            toast(R.string.err_unable_to_connect_to_server);
                    }
                });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbershop_shaped);
        initToolbar("", true, false);

        rating = findViewById(R.id.rating);


        try {
            String id = Security.base64decode(Objects.requireNonNull(getIntent().getData()).getLastPathSegment(), 1);
            if (id.matches("^[0-9]{1,9}$")) {
                if (Auth.isUser(this)) {
                    barbershopId = Integer.parseInt(id);
                    getBarbershopInfo();
                } else {
                    finish();
                }
            }
        } catch (NullPointerException ignored) {
            checkIntent();
        }


        recServices = findViewById(R.id.recServices);
        recBarbers = findViewById(R.id.recBarbers);
        recServices.setFocusable(false);

        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);
        imgLogo = findViewById(R.id.imgLogo);


        txtAddress = findViewById(R.id.txtAddress);
        txtRating = findViewById(R.id.txtRating);
        txtAbout = findViewById(R.id.txtAbout);
        txtName = findViewById(R.id.txtName);
        ratingBar = findViewById(R.id.ratingBar);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txtServices = findViewById(R.id.txtServices);
        txtServicesCount = findViewById(R.id.txtServicesCount);
        txtBarbers = findViewById(R.id.txtBarbers);
        txtBarbersCount = findViewById(R.id.txtBarbersCount);


        recServices.setLayoutManager(new LinearLayoutManager(getApplicationContext(),  LinearLayoutManager.HORIZONTAL, false));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen._4dp);
        recServices.addItemDecoration(itemDecoration);

        recBarbers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recBarbers.addItemDecoration(itemDecoration);

        ratingBar.setIsIndicator(true);
        rating.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CommentsActivity.class);
            intent.putExtra(Util.COMMUNICATION_KEYS.BARBERSHOP_ID, Integer.toString(barbershopId));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        applyTextFont(
                txtAddress,
                txtRating,
                txtAbout,
                ratingBar,
                txtName,
                txtServices,
                txtServicesCount,
                txtBarbers,
                txtBarbersCount
        );
        applyTextBoldFont(
                txt1,
                txt2,
                txt3
        );
    }

    private void getBarbershopInfo() {
        getBarbershopInfo(barbershopId);
        isBookmarked();
    }

    private void checkIntent() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            barbershopId = Integer.parseInt(extras.getString(Util.COMMUNICATION_KEYS.BARBERSHOP_ID));
            getBarbershopInfo();
        } else {
            finish();
        }
    }

    private void initServices(List<BannerService> list) {
        recServices.setAdapter(new BarberShopProfileServiceAdapter(list, BarberShopActivity.this, barbershopId));
        recServices.setNestedScrollingEnabled(false);
    }

    private void initBarbers(List<BarberShortInfo> list) {
        recBarbers.setAdapter(new BarbersAdapter(list, BarberShopActivity.this));
        recBarbers.setNestedScrollingEnabled(false);
    }


    private void hideToolbar() {
        toolbar.animate().translationY(-256).setInterpolator(new AccelerateInterpolator()).start();
    }

    private void showToolbar() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_share) {
            Toast.makeText(this, "این قابلیت هنوز فعال نشده است.", Toast.LENGTH_SHORT).show();

//            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//            sharingIntent.setType("text/plain");
//            String shareBody = "آرایشگاه تست";
//            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
//            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//            startActivity(Intent.createChooser(sharingIntent, "به اشتراک گذاری با"));
        } else if (id == R.id.action_bookmark) {
            Toast.makeText(this, "این قابلیت هنوز فعال نشده است.", Toast.LENGTH_SHORT).show();

//            bookmark(Util.getAccessKey(getApplicationContext()), String.valueOf(barbershopId));

        } else if (id == R.id.action_reserve) {
            Intent intent = new Intent(BarberShopActivity.this, ReserveStep1Activity.class);
            intent.putExtra("BARBERSHOP_ID", Integer.toString(barbershopId));
            startActivity(intent);
        } else if (id == R.id.action_route) {
//            Intent intent = new Intent(BarberShopActivity.this, ShowDirectionActivity.class);
//            startActivity(intent);

            Toast.makeText(this, "این قابلیت هنوز فعال نشده است.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void bookmark(String barbershopId) {
        Call<com.sorinaidea.ghaichi.webservice.model.responses.Response> bookmark =
                API.getRetrofit(this).create(UserProfileService.class)
                        .createOrRemove( String.valueOf(barbershopId));

        bookmark.enqueue(new Callback<com.sorinaidea.ghaichi.webservice.model.responses.Response>() {
            @Override
            public void onResponse(Call<com.sorinaidea.ghaichi.webservice.model.responses.Response> call, Response<com.sorinaidea.ghaichi.webservice.model.responses.Response> response) {
                if (response.isSuccessful()) {
                    if (!response.body().hasError()) {
                        updateMenuTitles();
                    }
                }
            }

            @Override
            public void onFailure(Call<com.sorinaidea.ghaichi.webservice.model.responses.Response> call, Throwable t) {

            }
        });
    }


    boolean isBookmarked = false;

    public boolean isBookmarked() {

        Call<IsBookmarked> bookmarkExists =
                API.getRetrofit(this).create(UserProfileService.class)
                        .bookmarkExists(  String.valueOf(barbershopId));

        bookmarkExists.enqueue(new Callback<IsBookmarked>() {
            @Override
            public void onResponse(Call<IsBookmarked> call, Response<IsBookmarked> response) {
                if (response.isSuccessful()) {
                    if (!response.body().hasError()) {
                        isBookmarked = response.body().isBookmarked();
                        updateMenuTitles();
                    }
                }
            }

            @Override
            public void onFailure(Call<IsBookmarked> call, Throwable t) {

            }
        });

        return isBookmarked;
    }


    private void updateMenuTitles() {
        MenuItem bedMenuItem = appMenu.findItem(R.id.action_bookmark);
        if (isBookmarked) {
            bedMenuItem.setTitle(getResources().getString(R.string.action_remove_bookmark));
            bedMenuItem.setIcon(R.drawable.ic_bookmark_white_24dp);
        } else {
            bedMenuItem.setTitle(getResources().getString(R.string.action_bookmark));
            bedMenuItem.setIcon(R.drawable.ic_bookmark_border_white_24dp);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private Menu appMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.show_barbershop, menu);
        appMenu = menu;

        return true;
    }
}
