package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.CommentsAdabper;
import com.sorinaidea.ghaichi.adapter.EmptyAdabper;
import com.sorinaidea.ghaichi.adapter.ServiceSelectionAdabper;
import com.sorinaidea.ghaichi.fast.Barbershop;
import com.sorinaidea.ghaichi.fast.Comment;
import com.sorinaidea.ghaichi.model.Empty;
import com.sorinaidea.ghaichi.model.Service;
import com.sorinaidea.ghaichi.model.ServiceList;
import com.sorinaidea.ghaichi.ui.dialog.CommentDialog;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by mr-code on 6/17/2018.
 */

public class CommentsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fabComment;
    private int barbershopId;
    private RecyclerView recComments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        recComments = (RecyclerView) findViewById(R.id.recComments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recComments.setLayoutManager(layoutManager);

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            barbershopId = Integer.parseInt(extras.getString("BARBERSHOP_ID"));
            initComments(barbershopId);
        } else {
            finish();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fabComment = (FloatingActionButton) findViewById(R.id.fabComment);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("نظرات");



        fabComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentDialog dialog = new CommentDialog(CommentsActivity.this, barbershopId,
                        () -> initComments(barbershopId));
                dialog.show();
            }
        });

    }

    private void initComments(int barbershopId) {

        Retrofit retrofit = API.getRetrofit();

        BarbershopServices service = retrofit.create(BarbershopServices.class);

        Call<List<Comment>> commentsCall = service.comments(barbershopId, Util.getAccessKey(getApplicationContext()));

        commentsCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.body() != null) {
                    if (response.body().isEmpty()) {
                        recComments.setAdapter(new EmptyAdabper(getApplicationContext()));
                    } else {
                        recComments.setAdapter(new CommentsAdabper(getApplicationContext(), response.body()));
                        recComments.setNestedScrollingEnabled(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });

    }



    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_comment, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
