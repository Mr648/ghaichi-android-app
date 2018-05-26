package com.sorinaidea.arayeshgah.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.FAQAdabper;
import com.sorinaidea.arayeshgah.adapter.HairdresserListAdabper;
import com.sorinaidea.arayeshgah.adapter.TopBarberShopAdabper;
import com.sorinaidea.arayeshgah.model.BarberShop;
import com.sorinaidea.arayeshgah.model.FAQ;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mr-code on 3/10/2018.
 */

public class HairdresserListActivity extends SorinaActivity {

    private RecyclerView recHairdressers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hairdresser_list);

        recHairdressers = (RecyclerView) findViewById(R.id.recHairdressers);
        recHairdressers.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recHairdressers.setAdapter(new HairdresserListAdabper(initDatasetTops(), getApplicationContext()));


    }
    private ArrayList<BarberShop> initDatasetTops() {
        ArrayList<BarberShop> mDataset = new ArrayList<>();
        Date date = new Date();
        for (int i = 0; i < 20; i++) {
            mDataset.add(new BarberShop(R.drawable.icbg_twitter, "آرایشگاه تست " + i));
        }
        return mDataset;
    }
}
