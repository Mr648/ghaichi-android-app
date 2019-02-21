package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BarberShopProfileServiceAdapter;
import com.sorinaidea.ghaichi.adapter.ImageSliderAdapter;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.fast.Barbershop;
import com.sorinaidea.ghaichi.fast.Photo;
import com.sorinaidea.ghaichi.fast.Service;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;
import com.sorinaidea.ghaichi.webservice.UserProfileService;
import com.sorinaidea.ghaichi.webservice.model.responses.IsBookmarked;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by mr-code on 6/17/2018.
 */

public class BarberShopActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mPager;
    private ScrollView scrViewRoot;
    private CircleIndicator indicator;

    //    private CircleImageView imgLogo;
    //    private AppCompatImageView imgComment;

    private CircleImageView imgLogo;
    private TextView txtAddress;
    private TextView txtRating;
    private TextView txtName;
    private TextView txtDescription;
    private LinearLayout lnrRating;
    private RatingBar ratingBar;
    private RecyclerView recServices;
    private Typeface fontIranSans;
    private static int currentPage = 0;
    private static final int NUM_COLUMNS = 2;
    private int barbershopId;


    private void updateUI(Barbershop barbershop) {

        txtName.setText(barbershop.getName());
        txtAddress.setText(barbershop.getAddress());
        txtDescription.setText(barbershop.getDescription());
        ratingBar.setRating(Float.parseFloat(barbershop.getRating()));
        txtRating.setText(String.format("%.2f", (Float.parseFloat(barbershop.getRating()) * 1.0f)));
        initializeImageSlider(barbershop.getBanners());
        initServices(barbershop.getServices());
        try {
            API.getPicasso(getApplicationContext())
                    .load(API.BASE_URL
                            + URLDecoder.decode(barbershop.getIcon(), "UTF-8"))
                    .fit()
                    .into(imgLogo);

//                    .resize(Util.dp(48, getApplicationContext()), Util.dp(48, getApplicationContext()))
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void getBarbershopInfo(int barbershopId) {

        Retrofit retrofit = API.getRetrofit();

        BarbershopServices service = retrofit.create(BarbershopServices.class);

        Call<Barbershop> barbershopCall = service.barbershop(barbershopId, Auth.getAccessKey(getApplicationContext()));

        barbershopCall.enqueue(new Callback<Barbershop>() {
            @Override
            public void onResponse(Call<Barbershop> call, Response<Barbershop> response) {
                if (response.body() != null) {
                    updateUI(response.body());
                }
            }

            @Override
            public void onFailure(Call<Barbershop> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbershop);

        lnrRating = (LinearLayout) findViewById(R.id.lnrRating);


        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            barbershopId = Integer.parseInt(extras.getString(Util.COMMUNICATION_KEYS.BARBERSHOP_ID));
            getBarbershopInfo(barbershopId);
            isBookmarked();
            lnrRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CommentsActivity.class);
                    intent.putExtra(Util.COMMUNICATION_KEYS.BARBERSHOP_ID, Integer.toString(barbershopId));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        } else {
            finish();
        }


        scrViewRoot = (ScrollView) findViewById(R.id.scrViewRoot);
        recServices = (RecyclerView) findViewById(R.id.recBanners);

        recServices.setFocusable(false);

        scrViewRoot.fullScroll(ScrollView.FOCUS_UP);
        scrViewRoot.smoothScrollTo(0, 0);


        fontIranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        imgLogo = (CircleImageView) findViewById(R.id.imgLogo);

        //        imgComment = (AppCompatImageView) findViewById(R.id.imgComment);

        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtRating = (TextView) findViewById(R.id.txtRating);
        txtName = (TextView) findViewById(R.id.txtTime);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);


        recServices.setLayoutManager(new GridLayoutManager(getApplicationContext(), NUM_COLUMNS, GridLayoutManager.VERTICAL, false));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen._4dp);
        recServices.addItemDecoration(itemDecoration);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ratingBar.setIsIndicator(true);


        scrViewRoot.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrViewRoot.getScrollY(); // For ScrollView
                int scrollX = scrViewRoot.getScrollX(); // For HorizontalScrollView

                if (scrollY <= 0) {
                    showToolbar();
                }
                if (scrollY > 100) {
                    hideToolbar();
                }
                // DO SOMETHING WITH THE SCROLL COORDINATES
            }
        });


        FontManager.setFont(txtAddress, fontIranSans);
        FontManager.setFont(txtRating, fontIranSans);
        FontManager.setFont(txtName, fontIranSans);
        FontManager.setFont(txtDescription, fontIranSans);
        FontManager.setFont(ratingBar, fontIranSans);


    }

    private void initServices(List<com.sorinaidea.ghaichi.fast.Service> list) {
        ArrayList<Service> services = new ArrayList<>();
        services.addAll(list);
        recServices.setAdapter(new BarberShopProfileServiceAdapter(services, BarberShopActivity.this, barbershopId));
        recServices.setNestedScrollingEnabled(false);
    }


    private ArrayList<String> imageList = new ArrayList<>();

    private void hideToolbar() {
        toolbar.animate().translationY(-256).setInterpolator(new AccelerateInterpolator()).start();
    }

    private void showToolbar() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
    }

    private void initializeImageSlider(List<Photo> photos) {


        ImageSliderAdapter adapter = new ImageSliderAdapter(getApplicationContext(), imageList);
        mPager.setAdapter(adapter);
        indicator.setViewPager(mPager);


        for (Photo photo :
                photos) {
            imageList.add(photo.getPath());
        }
        adapter.notifyDataSetChanged();


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == imageList.size()) {
                    currentPage = 0;
                }

                mPager.setCurrentItem(currentPage++, true);
            }
        };

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter.setImageOnCLickListener(view -> {
            if (!show) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToolbar();
                    }
                });
            } else {
                runOnUiThread(() -> hideToolbar());
            }
            show = !show;
        });

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 3000);
    }

    private boolean show = false;

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

    public void bookmark(String accessKey, String barbershopId) {
        Call<com.sorinaidea.ghaichi.webservice.model.responses.Response> bookmark =
                API.getRetrofit().create(UserProfileService.class)
                        .createOrRemove(Auth.getAccessKey(getApplicationContext()), String.valueOf(barbershopId));

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
                API.getRetrofit().create(UserProfileService.class)
                        .bookmarkExists(Auth.getAccessKey(getApplicationContext()), String.valueOf(barbershopId));

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
