package com.sorinaidea.arayeshgah.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.TopBarberShopAdabper;
import com.sorinaidea.arayeshgah.adapter.TransactionAdabper;
import com.sorinaidea.arayeshgah.model.BarberShop;
import com.sorinaidea.arayeshgah.model.Transaction;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mr-code on 4/8/2018.
 */

public class MainActivity extends SorinaActivity {

    RecyclerView recTopBarberShops;
    RecyclerView recBarberShops;


    private ArrayList<BarberShop> initDatasetTops() {
        ArrayList<BarberShop> mDataset = new ArrayList<>();
        Date date = new Date();
        for (int i = 0; i < 20; i++) {
            mDataset.add(new BarberShop(R.drawable.ic_hairdresser));
        }
        return mDataset;
    }


    private ArrayList<Transaction> initDatasetNormals() {
        ArrayList<Transaction> mDataset = new ArrayList<>();
        Date date = new Date();
        for (int i = 0; i < 20; i++) {
            mDataset.add(new Transaction(((i % 4 == 0) ? (-1 * 100 * i) : (i * 1000)), "آرایشگاه تست " + i, "دوشنبه 30 بهمن 96"));
        }
        return mDataset;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_barbershop);
        recTopBarberShops = (RecyclerView) findViewById(R.id.recTopBarberShops);
        recBarberShops = (RecyclerView) findViewById(R.id.recBarberShops);

        recTopBarberShops.setNestedScrollingEnabled(false);
        recBarberShops.setNestedScrollingEnabled(false);

        recTopBarberShops.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recTopBarberShops.setAdapter(new TopBarberShopAdabper(initDatasetTops(), getApplicationContext()));

        recBarberShops.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recBarberShops.setAdapter(new TransactionAdabper(initDatasetNormals(), getApplicationContext()));
//        slider = findViewById(R.id.banner_slider1);
//        slider.setAdapter(new MainActivitySliderAdapter());
    }
}
