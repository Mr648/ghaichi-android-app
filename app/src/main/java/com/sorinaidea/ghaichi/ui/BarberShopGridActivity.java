package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.GridItemsAdabper;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.fast.BarbershopCard;

import java.util.ArrayList;
import java.util.List;

public class BarberShopGridActivity extends AppCompatActivity {

    private RecyclerView recAllItems;
    private Toolbar toolbar;
//    private FloatingActionButton fabRefresh;
    private GridItemsAdabper adapter;
    private ArrayList<BarbershopCard> dataset;
    private static final int NUM_COLUMNS = 3;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);

        toolbar = findViewById(R.id.toolbar);

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

        recAllItems = findViewById(R.id.recAllItems);
        recAllItems.setLayoutManager(new GridLayoutManager(getApplicationContext(), NUM_COLUMNS));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen._4dp);
        recAllItems.addItemDecoration(itemDecoration);
        initDataset();




    }


    private void initDataset() {

        List<BarbershopCard> barbers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            barbers.add(new BarbershopCard());
        }
        adapter = new GridItemsAdabper(barbers, getApplicationContext());
        recAllItems.setAdapter(adapter);

//        Call<List<BarbershopCard>> barbershopCall = API.getRetrofit().create(BarbershopServices.class).barbershopsCards(Auth.getAccessKey(getApplicationContext()));
//
//        barbershopCall.enqueue(new Callback<List<BarbershopCard>>() {
//            @Override
//            public void onResponse(Call<List<BarbershopCard>> call, Response<List<BarbershopCard>> response) {
//                if (response.body() != null) {
//                    adapter = new GridItemsAdabper(response.body(), getApplicationContext());
//                    recAllItems.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<BarbershopCard>> call, Throwable t) {
//
//            }
//        });
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
