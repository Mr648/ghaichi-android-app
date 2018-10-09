package com.sorinaidea.ghaichi.ui;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
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

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.BarberShopMiniItemAdapter;
import com.sorinaidea.ghaichi.adapter.FAQAdabper;
import com.sorinaidea.ghaichi.adapter.GridItemsAdabper;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.fast.BarbershopCard;
import com.sorinaidea.ghaichi.model.BarberShop;
import com.sorinaidea.ghaichi.model.FAQ;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarberShopGridActivity extends AppCompatActivity {

    private RecyclerView recAllItems;
    private Toolbar toolbar;
//    private FloatingActionButton fabRefresh;
    private GridItemsAdabper adapter;
    private ArrayList<BarbershopCard> dataset;
    private static final int NUM_COLUMNS = 2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        fabRefresh = (FloatingActionButton) findViewById(R.id.fabRefresh);

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            String title = extras.getString("TITLE");
            getSupportActionBar().setTitle(title);
        } else {
            getSupportActionBar().setTitle("آرایشگران");
        }

        recAllItems = (RecyclerView) findViewById(R.id.recAllItems);
        recAllItems.setLayoutManager(new GridLayoutManager(getApplicationContext(), NUM_COLUMNS));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen._4dp);
        recAllItems.addItemDecoration(itemDecoration);
        initDataset();




    }


    private void initDataset() {
        Call<List<BarbershopCard>> barbershopCall = API.getRetrofit().create(BarbershopServices.class).barbershopsCards(Util.getAccessKey(getApplicationContext()));

        barbershopCall.enqueue(new Callback<List<BarbershopCard>>() {
            @Override
            public void onResponse(Call<List<BarbershopCard>> call, Response<List<BarbershopCard>> response) {
                if (response.body() != null) {
                    adapter = new GridItemsAdabper(response.body(), getApplicationContext());
                    recAllItems.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<BarbershopCard>> call, Throwable t) {

            }
        });
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
