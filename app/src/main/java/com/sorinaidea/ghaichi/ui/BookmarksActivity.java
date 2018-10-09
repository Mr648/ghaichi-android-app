package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.EmptyAdabper;
import com.sorinaidea.ghaichi.adapter.GridItemsAdabper;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.fast.BarbershopCard;
import com.sorinaidea.ghaichi.model.BarberShop;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.UserProfileService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarksActivity extends AppCompatActivity {

    private RecyclerView recAllItems;
    private Toolbar toolbar;

    private GridItemsAdabper adapter;

    private static final int NUM_COLUMNS = 2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            String title = extras.getString("TITLE");
            getSupportActionBar().setTitle(title);
        } else {
            getSupportActionBar().setTitle("آرایشگران");
        }

        recAllItems = (RecyclerView) findViewById(R.id.recAllItems);

        initDataset();


    }

    private int from = 1;

    private void initDataset() {
        Call<List<BarbershopCard>> bookmarks =
                API.getRetrofit().create(UserProfileService.class)
                        .bookmarks(Util.getAccessKey(getApplicationContext()));

        bookmarks.enqueue(new Callback<List<BarbershopCard>>() {
            @Override
            public void onResponse(Call<List<BarbershopCard>> call, Response<List<BarbershopCard>> response) {
                if (response.body() != null) {
                    if (response.body().isEmpty()) {
                        recAllItems.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recAllItems.setAdapter(new EmptyAdabper(getApplicationContext()));
                    } else {
                        recAllItems.setLayoutManager(new GridLayoutManager(getApplicationContext(), NUM_COLUMNS));
                        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen._4dp);
                        recAllItems.addItemDecoration(itemDecoration);
                        adapter = new GridItemsAdabper(response.body(), getApplicationContext());
                        recAllItems.setAdapter(adapter);
                    }
                }else{
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
