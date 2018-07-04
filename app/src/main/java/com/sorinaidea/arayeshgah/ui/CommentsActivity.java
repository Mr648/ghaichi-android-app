package com.sorinaidea.arayeshgah.ui;

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

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.CommentsAdabper;
import com.sorinaidea.arayeshgah.adapter.ServiceSelectionAdabper;
import com.sorinaidea.arayeshgah.model.Empty;
import com.sorinaidea.arayeshgah.model.Service;
import com.sorinaidea.arayeshgah.model.ServiceList;
import com.sorinaidea.arayeshgah.ui.dialog.CommentDialog;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mr-code on 6/17/2018.
 */

public class CommentsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fabComment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fabComment = (FloatingActionButton) findViewById(R.id.fabComment);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("نظرات");


        RecyclerView recComments = (RecyclerView) findViewById(R.id.recComments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recComments.setLayoutManager(layoutManager);
        recComments.setAdapter(new CommentsAdabper(getApplicationContext()));

        fabComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentDialog dialog = new CommentDialog(CommentsActivity.this);
                dialog.show();
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
