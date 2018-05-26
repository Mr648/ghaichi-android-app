package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.TopBarberShopAdabper;
import com.sorinaidea.arayeshgah.adapter.TransactionAdabper;
import com.sorinaidea.arayeshgah.model.BarberShop;
import com.sorinaidea.arayeshgah.model.Transaction;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mr-code on 4/8/2018.
 */

public class MainActivity extends SorinaActivity {

    RecyclerView recTopBarberShops;
    TextView txtMoreText;
    TextView txtMoreIcon;
    TextView txtCategory;
    LinearLayout lnrMore;

    RecyclerView recTopBarberShops2;
    TextView txtMoreText2;
    TextView txtMoreIcon2;
    TextView txtCategory2;
    LinearLayout lnrMore2;

    RecyclerView recTopBarberShops3;
    TextView txtMoreText3;
    TextView txtMoreIcon3;
    TextView txtCategory3;
    LinearLayout lnrMore3;


    private ArrayList<BarberShop> initDatasetTops() {
        ArrayList<BarberShop> mDataset = new ArrayList<>();
        Date date = new Date();
        for (int i = 0; i < 20; i++) {
            mDataset.add(new BarberShop(R.drawable.img, "آرایشگاه تست " + i));
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

    private Typeface fontMaterialIcon;
    private Typeface fontIransans;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_barbershop);

        fontMaterialIcon = FontManager.getTypeface(getApplicationContext(), FontManager.MATERIAL_ICONS);
        fontIransans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        recTopBarberShops = (RecyclerView) findViewById(R.id.recTopBarberShops);
        txtMoreText = (TextView) findViewById(R.id.txtMoreText);
        txtMoreIcon = (TextView) findViewById(R.id.txtMoreIcon);
        txtCategory = (TextView) findViewById(R.id.txtCategory);
        lnrMore = (LinearLayout) findViewById(R.id.lnrMore);

        recTopBarberShops2 = (RecyclerView) findViewById(R.id.recTopBarberShops2);
        txtMoreText2 = (TextView) findViewById(R.id.txtMoreText2);
        txtMoreIcon2 = (TextView) findViewById(R.id.txtMoreIcon2);
        txtCategory2 = (TextView) findViewById(R.id.txtCategory2);
        lnrMore2 = (LinearLayout) findViewById(R.id.lnrMore2);

        recTopBarberShops3 = (RecyclerView) findViewById(R.id.recTopBarberShops3);
        txtMoreText3 = (TextView) findViewById(R.id.txtMoreText3);
        txtMoreIcon3 = (TextView) findViewById(R.id.txtMoreIcon3);
        txtCategory3 = (TextView) findViewById(R.id.txtCategory3);
        lnrMore3 = (LinearLayout) findViewById(R.id.lnrMore3);

        recTopBarberShops.setNestedScrollingEnabled(false);

        recTopBarberShops.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recTopBarberShops.setAdapter(new TopBarberShopAdabper(initDatasetTops(), getApplicationContext()));

        FontManager.setFont(txtMoreIcon, fontMaterialIcon);
        FontManager.setFont(txtMoreText, fontIransans);
        FontManager.setFont(txtCategory, fontIransans);


        recTopBarberShops2.setNestedScrollingEnabled(false);

        recTopBarberShops2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recTopBarberShops2.setAdapter(new TopBarberShopAdabper(initDatasetTops(), getApplicationContext()));

        FontManager.setFont(txtMoreIcon2, fontMaterialIcon);
        FontManager.setFont(txtMoreText2, fontIransans);
        FontManager.setFont(txtCategory2, fontIransans);


        recTopBarberShops3.setNestedScrollingEnabled(false);
        recTopBarberShops3.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recTopBarberShops3.setAdapter(new TopBarberShopAdabper(initDatasetTops(), getApplicationContext()));

        FontManager.setFont(txtMoreIcon3, fontMaterialIcon);
        FontManager.setFont(txtMoreText3, fontIransans);
        FontManager.setFont(txtCategory3, fontIransans);


        txtCategory.setText("جدیدترین‌ها");
        txtCategory2.setText("پیشنهاد ویژه");
        txtCategory3.setText("خدمات جدید");

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HairdresserListActivity.class);
                startActivity(intent);
            }
        };


        lnrMore.setOnClickListener(listener);
        lnrMore2.setOnClickListener(listener);
        lnrMore3.setOnClickListener(listener);


//        slider = findViewById(R.id.banner_slider1);
//        slider.setAdapter(new MainActivitySliderAdapter());
    }
}
