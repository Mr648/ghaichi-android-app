package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.FAQAdabper;
import com.sorinaidea.arayeshgah.model.FAQ;

import java.util.ArrayList;

public class BarberShopGridActivity extends AppCompatActivity {

    private RecyclerView recAllItems;
    private Toolbar toolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_faq);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("سوالات متداول");

        recAllItems = (RecyclerView) findViewById(R.id.recFaq);
        });
        recAllItems.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recAllItems.setAdapter(new FAQAdabper(initDataset(), getApplicationContext()));

    }

    private ArrayList<FAQ> initDataset() {
        ArrayList<FAQ> mDataset = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mDataset.add(new FAQ("یک جواب نمونه، این یک نمونه است", "سوال نمونه، سوال نمونه"));
        }
        return mDataset;
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
