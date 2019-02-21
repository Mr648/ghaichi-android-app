package com.sorinaidea.ghaichi.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

public class ToolbarActivity extends BaseActivity {

    protected Toolbar toolbar;
    protected TextView toolbarTitle;
    protected LovelyProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new LovelyProgressDialog(this);
    }


    private void initToolbar(boolean displayHomeAsUpEnabled, boolean displayShowTitleEnabled) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
        getSupportActionBar().setDisplayShowTitleEnabled(displayShowTitleEnabled);

        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        applyTextFont(toolbarTitle);
    }

    protected void initToolbar(String title, boolean displayHomeAsUpEnabled, boolean displayShowTitleEnabled) {
        initToolbar(displayHomeAsUpEnabled, displayShowTitleEnabled);
        toolbarTitle.setText(title);

    }

    protected void initToolbar(@StringRes int title, boolean displayHomeAsUpEnabled, boolean displayShowTitleEnabled) {
        initToolbar(displayHomeAsUpEnabled, displayShowTitleEnabled);
        toolbarTitle.setText(title);
    }


    protected void showProgressDialog(String title, String message, boolean cancelable) {
        if (title == null)
            progressDialog.setTitle("بارگزاری داده");
        else
            progressDialog.setTitle(title);

        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }

    protected void showProgressDialog(@StringRes int title, @StringRes int message, @DrawableRes int icon, @ColorRes int color, boolean cancelable) {
        progressDialog.setTitle(title);
        progressDialog.setTopColorRes(color);
        progressDialog.setIcon(icon);
        progressDialog.setMessage(message);
        progressDialog.configureMessageView(this::applyTextFont);
        progressDialog.configureTitleView(this::applyTextFont);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}
