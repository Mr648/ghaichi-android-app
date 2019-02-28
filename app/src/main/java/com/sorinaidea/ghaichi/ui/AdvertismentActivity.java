package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.AdvertisementAdabper;
import com.sorinaidea.ghaichi.model.Advertise;
import com.sorinaidea.ghaichi.ui.barbershop.activity.RequestAdvertisementActivity;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.ArrayList;

public class AdvertismentActivity extends AppCompatActivity {

    private RecyclerView recAdvertises;

    private FloatingActionButton fabAddAdvertise;

    private Typeface fontIranSans;

    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisment);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);

        mTitle.setText(R.string.toolbar_advertises);

        fontIranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);


        recAdvertises = findViewById(R.id.recAdvertises);
        recAdvertises.setNestedScrollingEnabled(false);


        fabAddAdvertise = findViewById(R.id.fabAddAdvertise);


        recAdvertises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        ArrayList<Advertise> dataset = initDataset();
        recAdvertises.setAdapter(new AdvertisementAdabper(dataset, getApplicationContext()));


        fabAddAdvertise.setOnClickListener(view -> {
            Intent intent = new Intent(AdvertismentActivity.this, RequestAdvertisementActivity.class);
            startActivity(intent);
        });

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

    private ArrayList<Advertise> initDataset() {
        ArrayList<Advertise> mDataset = new ArrayList<>();
        for (int amount = 10000, j = 0; amount <= 10020; amount++, j += 5) {
            int views = (amount % 4 == 0) ? amount : (int) ((j == 0 ? 1 : j) * (j == 100 ? 1 : Math.random()) * (amount / (j == 0 ? 1 : j)));
            int clicks = (int) ((j == 0 ? 1 : j) * (j == 100 ? 1 : Math.random()) * (amount / (j == 0 ? 1 : j)));
            mDataset.add(new Advertise(
                            amount
                            , views
                            , clicks
                            , 100000
                            , (j == 100)
                    )
            );
        }
        return mDataset;
    }


}
