package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.adapter.barbershop.SamplesAdapter;
import com.sorinaidea.ghaichi.util.FontManager;

public class SamplesActivity extends AppCompatActivity {

    private RecyclerView recSamples;
    private Toolbar toolbar;
    private TextView mTitle;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samples);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.toolbar_samples);

        recSamples = (RecyclerView) findViewById(R.id.recSamples);
        recSamples.setLayoutManager(new GridLayoutManager(this, 2));
        recSamples.addItemDecoration(new ItemOffsetDecoration(8));
        recSamples.setAdapter(new SamplesAdapter(this));

        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);
        FontManager.setFont(mTitle, iranSans);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sample, menu);
        return true;
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
