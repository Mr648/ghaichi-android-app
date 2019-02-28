package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.FAQAdabper;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.fast.FAQ;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.SystemServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FaqActivity extends AppCompatActivity {

    private RecyclerView recFaq;
    private FloatingActionButton fabSupport;
    private Toolbar toolbar;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_faq);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.toolbar_faq);

        recFaq = findViewById(R.id.recFaq);


        // TODO Enable Chat here!
        fabSupport = findViewById(R.id.fabSupport);
        fabSupport.setOnClickListener(view -> {
            Intent intent = new Intent(FaqActivity.this, SendSupportTicketActivity.class);
            startActivity(intent);
        });


        recFaq.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

//        fetchFAQs();

        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);
        FontManager.setFont(mTitle, iranSans);

    }

    private void fetchFAQs() {
        Retrofit retrofit = API.getRetrofit();

        SystemServices systemServices = retrofit.create(SystemServices.class);
        Call<List<FAQ>> fetcher = systemServices.faqs(Auth.getAccessKey(getApplicationContext()));
        fetcher.enqueue(new Callback<List<FAQ>>() {
            @Override
            public void onResponse(Call<List<FAQ>> call, Response<List<FAQ>> response) {
                if (response.body() != null) {
                    recFaq.setAdapter(new FAQAdabper(response.body(), getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<List<FAQ>> call, Throwable t) {

            }
        });
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
