package com.sorinaidea.ghaichi.ui;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.ImagePreviewAdapter;
import com.sorinaidea.ghaichi.ui.dialog.AddServiceDialog;
import com.sorinaidea.ghaichi.ui.dialog.TransactionDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr-code on 5/15/2018.
 */

public class AddServiceActivity extends AppCompatActivity {

    private RecyclerView recServices;

    private Toolbar toolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recServices = (RecyclerView) findViewById(R.id.recServices);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener((view) -> {
            AddServiceDialog dialog = new AddServiceDialog(AddServiceActivity.this);
            dialog.show();
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("مدیریت خدمات");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_service, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_single_category) {

        } else if (id == R.id.action_all_categories) {

        }
        return super.onOptionsItemSelected(item);
    }

}
