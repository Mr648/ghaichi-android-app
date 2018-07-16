package com.sorinaidea.arayeshgah.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.AdvertisementAdabper;
import com.sorinaidea.arayeshgah.adapter.AdvertisementInfoAdabper;
import com.sorinaidea.arayeshgah.model.Advertise;
import com.sorinaidea.arayeshgah.ui.dialog.AddCreditDialog;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;

public class AdvertismentInfoActivity extends AppCompatActivity {


    private Typeface fontIranSans;

    private Toolbar toolbar;
    private RecyclerView recInfo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_advertisment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {

            Advertise ad = (Advertise) extras.get("ADVERTISE");
            recInfo = (RecyclerView) findViewById(R.id.recInfo);
            recInfo.setLayoutManager(new LinearLayoutManager(AdvertismentInfoActivity.this, LinearLayoutManager.VERTICAL, false));
            recInfo.setAdapter(new AdvertisementInfoAdabper(ad.getInfo(), AdvertismentInfoActivity.this));

        } else {
            finish();
        }
        mTitle.setText("جزییات تبلیغ");

        fontIranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        FontManager.setFont(mTitle, fontIranSans);
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
