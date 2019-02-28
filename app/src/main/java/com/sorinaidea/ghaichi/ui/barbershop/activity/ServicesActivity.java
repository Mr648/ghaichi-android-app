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
import com.sorinaidea.ghaichi.adapter.barbershop.ServiceAdabper;
import com.sorinaidea.ghaichi.util.FontManager;

public class ServicesActivity extends AppCompatActivity {

    private RecyclerView recServices;
    private FloatingActionButton fabAddService;
    private Toolbar toolbar;
    private TextView mTitle;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.toolbar_manage_services);
        recServices = findViewById(R.id.recBanners);

        fabAddService = findViewById(R.id.fabAddService);
        fabAddService.setOnClickListener((view) -> {
            Intent intent = new Intent(ServicesActivity.this, AddServiceActivity.class);
            startActivity(intent);
        });


        recServices.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recServices.setAdapter(new ServiceAdabper(this));

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
