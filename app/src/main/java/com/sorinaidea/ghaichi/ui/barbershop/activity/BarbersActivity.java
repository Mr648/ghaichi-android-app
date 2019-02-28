package com.sorinaidea.ghaichi.ui.barbershop.activity;

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
import com.sorinaidea.ghaichi.adapter.barbershop.BarberInfoAdabper;
import com.sorinaidea.ghaichi.util.FontManager;

public class BarbersActivity extends AppCompatActivity {

    private RecyclerView recBarbers;
    private FloatingActionButton fabAddBarber;
    private Toolbar toolbar;
    private TextView mTitle;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbers);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.toolbar_manage_barbers);

        recBarbers = findViewById(R.id.recBanners);


        // TODO Enable Chat here!
        fabAddBarber = findViewById(R.id.fabAddBarber);
        fabAddBarber.setOnClickListener((view) -> {
            Intent intent = new Intent(BarbersActivity.this, AddBarberActivity.class);
            startActivity(intent);
        });
        recBarbers.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recBarbers.setAdapter(new BarberInfoAdabper(this));

        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);
        FontManager.setFont(mTitle, iranSans);

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
