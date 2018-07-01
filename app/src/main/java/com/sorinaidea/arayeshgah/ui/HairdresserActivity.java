package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.ReservationAdabper;
import com.sorinaidea.arayeshgah.model.Reservation;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mr-code on 5/6/2018.
 */

public class HairdresserActivity extends AppCompatActivity {

    FloatingActionButton fabAddServiceCategory;
    FloatingActionButton fabAddService;
    RecyclerView recReservations;


    private ArrayList<Reservation> initDataset() {
        ArrayList<Reservation> mDataset = new ArrayList<>();
        Date date = new Date();
        for (int i = 0; i < 20; i++) {
            mDataset.add(new Reservation(R.drawable.background_green, "نام کاربر", "1396/11/12", "16:45 ب.ظ", "لیست مختصر خدمات..."));
        }
        return mDataset;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hairdresser);


        fabAddServiceCategory = (FloatingActionButton) findViewById(R.id.fabAddServiceCategory);
        fabAddService = (FloatingActionButton) findViewById(R.id.fabAddService);

        fabAddServiceCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HairdresserActivity.this, AddServiceCategoryActivity.class);
                startActivity(intent);
            }
        });


        fabAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HairdresserActivity.this, AddServiceActivity.class);
                startActivity(intent);
            }
        });

        recReservations = (RecyclerView) findViewById(R.id.recReservations);
        recReservations.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recReservations.setAdapter(new ReservationAdabper(initDataset(), getApplicationContext()));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    boolean menuAction;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_todo) {
//            if (menuAction) {
//                item.setIcon(android.R.drawable.ic_menu_add);
//            } else {
//                item.setIcon(android.R.drawable.ic_menu_delete);
//            }
//            menuAction = !menuAction;
//            return true;
//        }


        return super.onOptionsItemSelected(item);
    }


}
