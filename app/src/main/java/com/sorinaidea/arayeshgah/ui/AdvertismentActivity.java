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
import com.sorinaidea.arayeshgah.model.Advertise;
import com.sorinaidea.arayeshgah.ui.dialog.AddCreditDialog;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;

public class AdvertismentActivity extends AppCompatActivity {

    private RecyclerView recAdvertises;

    private FloatingActionButton fab;

    private Typeface fontIranSans;

    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("تبلیغات");

        fontIranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);


        recAdvertises = (RecyclerView) findViewById(R.id.recAdvertises);
        recAdvertises.setNestedScrollingEnabled(false);


        fab = (FloatingActionButton) findViewById(R.id.fab);


        recAdvertises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        ArrayList<Advertise> dataset = initDataset();
        recAdvertises.setAdapter(new AdvertisementAdabper(dataset, getApplicationContext()));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCreditDialog dialog = new AddCreditDialog(AdvertismentActivity.this);
                dialog.show();
            }
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
