package com.sorinaidea.arayeshgah.ui;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.FAQAdabper;
import com.sorinaidea.arayeshgah.adapter.GridItemsAdabper;
import com.sorinaidea.arayeshgah.model.BarberShop;
import com.sorinaidea.arayeshgah.model.FAQ;

import java.util.ArrayList;

public class BarberShopGridActivity extends AppCompatActivity {

    private RecyclerView recAllItems;
    private Toolbar toolbar;
    private FloatingActionButton fabRefresh;
    private GridItemsAdabper adapter;
    private ArrayList<BarberShop> dataset;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            String title = extras.getString("TITLE");
            getSupportActionBar().setTitle(title);
        } else {
            getSupportActionBar().setTitle("آرایشگران");
        }

        recAllItems = (RecyclerView) findViewById(R.id.recAllItems);
        recAllItems.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        dataset = initDataset();
        adapter = new GridItemsAdabper(dataset, getApplicationContext());

        recAllItems.setAdapter(adapter);

        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabRefresh.animate().rotation(360).setDuration(1500).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        dataset.addAll(initDataset());
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).start();
            }
        });

    }

    private ArrayList<BarberShop> initDataset() {
        ArrayList<BarberShop> mDataset = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDataset.add(new BarberShop(R.drawable.barbershop, "BarberShop #" + i));
        }
        return mDataset;
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
