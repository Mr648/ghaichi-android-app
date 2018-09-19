package com.sorinaidea.arayeshgah.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.AdvertisementInfoAdabper;
import com.sorinaidea.arayeshgah.adapter.ItemOffsetDecoration;
import com.sorinaidea.arayeshgah.model.Advertise;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.Util;

public class AdvertismentInfoActivity extends AppCompatActivity {


    private Typeface fontIranSans;
    private DecoView arcView;

    private Toolbar toolbar;
    private RecyclerView recInfo;
    private TextView txtViews;

    private Advertise ad;
    private static final int NUM_COLUMNS = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_advertisment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        arcView = (DecoView) findViewById(R.id.dynamicArcView);
        txtViews = (TextView) findViewById(R.id.txtViews);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            ad = (Advertise) extras.get("ADVERTISE");
            recInfo = (RecyclerView) findViewById(R.id.recInfo);
            recInfo.setAdapter(new AdvertisementInfoAdabper(ad.getInfo(), AdvertismentInfoActivity.this));
            recInfo.setLayoutManager(new GridLayoutManager(getApplicationContext(), NUM_COLUMNS));
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen._4dp);
            recInfo.addItemDecoration(itemDecoration);
        } else {
            finish();
        }
        mTitle.setText("جزییات تبلیغ");
        txtViews.setText(ad.getViews() + " بازدید");

        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 255, 255, 255))
                .setRange(0, 100, 100)
                .setLineWidth(16f)
                .setInitialVisibility(true)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_PIE)
                .build());


        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 224, 224, 224))
                .setRange(0, 100, 100)
                .setLineWidth(16f)
                .setInitialVisibility(true)
                .setInterpolator(new OvershootInterpolator())
                .setSpinClockwise(true)
                .setSpinDuration(6000)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
                .build());


        SeriesItem series = new SeriesItem.Builder(Util.getSuitableColor(ad))
                .setRange(0, 100, 0)
                .setLineWidth(16f)
                .setInitialVisibility(false)
                .setInterpolator(new OvershootInterpolator())
                .setSpinClockwise(true)
                .setSpinDuration(6000)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
                .build();


        arcView.addSeries(series);
        arcView.addEvent(new DecoEvent.Builder((ad.getViews() * 1.0f / ad.getAmount() * 100))
                .setIndex(2)
                .setDelay(1000)
                .setDuration(2000)
                .setInterpolator(new OvershootInterpolator())
                .build());

        fontIranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        FontManager.setFont(mTitle, fontIranSans);
        FontManager.setFont(txtViews, fontIranSans);
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
