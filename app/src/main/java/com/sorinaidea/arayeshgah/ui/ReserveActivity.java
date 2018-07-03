package com.sorinaidea.arayeshgah.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.ServiceSelectionAdabper;
import com.sorinaidea.arayeshgah.model.Service;
import com.sorinaidea.arayeshgah.model.ServiceList;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mr-code on 6/17/2018.
 */

public class ReserveActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtPrice;
    private RelativeLayout relativeLayout;
    View parentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_service);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        relativeLayout.animate().alpha(0.0f).setDuration(1).start();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("رزرو خدمات");


        List<ServiceList> serviceLists = serviceLists();
        RecyclerView recServices = (RecyclerView) findViewById(R.id.recServices);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        parentLayout = findViewById(android.R.id.content);


        //instantiate your adapter with the list of genres
        ServiceSelectionAdabper adapter = new ServiceSelectionAdabper(serviceLists, new PriceUpdater(txtPrice) {
            @Override
            public void add(Service service) {
                runOnUiThread(() -> {
                    super.add(service);
                    if (!selectedServices.isEmpty()) {
                        relativeLayout.animate().alpha(1.0f).setDuration(500).start();
                        Log.i("ADD_TAG_SERVICE", "SERVICE " + serviceLists.size());
                    }
                });
            }

            @Override
            public void delete(Service service) {
                super.delete(service);
                if (selectedServices.isEmpty()) {
                    relativeLayout.animate().alpha(0.0f).setDuration(500).start();
                }
                Log.i("DELETE_TAG_SERVICE", "SERVICE " + serviceLists.size());
            }
        });
        recServices.setLayoutManager(layoutManager);
        recServices.setAdapter(adapter);


    }

    private List<ServiceList> serviceLists() {
        return Arrays.asList(
                new ServiceList("مو",
                        Arrays.asList(
                                new Service("مدل"),
                                new Service("کوتاه کردن", 0.099f),
                                new Service("کچل کردن")
                        )
                ),
                new ServiceList("پوست",
                        Arrays.asList(
                                new Service("اپیلاسیون"),
                                new Service("برنزه کردن"),
                                new Service("مرطوب کردن", 0.10f)
                        )
                ),
                new ServiceList("زیبایی",
                        Arrays.asList(
                                new Service("آرایش"),
                                new Service("پیرایش", 0.35f),
                                new Service("ویرایش")
                        )
                ),
                new ServiceList("ناخن",
                        Arrays.asList(
                                new Service("پدیکور"),
                                new Service("مانیکور"),
                                new Service("لاک", 0.2f),
                                new Service("کوتاه کردن"),
                                new Service("ناخن مصنوعی")

                        )
                )
        );
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_reserve) {
            // TODO Show Reserve Dialog
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_reserve, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
