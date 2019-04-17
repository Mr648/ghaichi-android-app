package com.sorinaidea.ghaichi.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.Objects;

public class ToolbarActivity extends BaseActivity {

    protected Toolbar toolbar;
    protected TextView toolbarTitle;
    protected LovelyProgressDialog progressDialog;
    private AlertDialog dialog;
    private ProgressFragment progressFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressFragment = new ProgressFragment();

        progressDialog = new LovelyProgressDialog(this);
        dialog = new AlertDialog.Builder(this, R.style.CustomDialog)
                .setView(getLayoutInflater().inflate(R.layout.dialog_progress, null))
                .create();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

    protected void initToolbar(@StringRes int title, boolean displayHomeAsUpEnabled, boolean displayShowTitleEnabled, boolean homeEnabled) {
        initToolbar(title, displayHomeAsUpEnabled, displayShowTitleEnabled);
        try {
            Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(homeEnabled);
        } catch (NullPointerException ignore) {
        }

    }

    protected void initToolbar(@StringRes int title, boolean displayHomeAsUpEnabled, boolean displayShowTitleEnabled) {
        initToolbar(displayHomeAsUpEnabled, displayShowTitleEnabled);
        toolbarTitle.setText(title);
    }


    protected void showProgress() {
//        dialog.show();

        if (!isFinishing() && !isDestroyed() && getSupportFragmentManager().findFragmentByTag("PROGRESS") == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, progressFragment, "PROGRESS")
                    .commitAllowingStateLoss();
        }

    }

    protected void hideProgress() {

        if (!isFinishing() && !isDestroyed() && !progressFragment.isDetached()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(progressFragment)
                    .commitAllowingStateLoss();
        }

//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }
    }

    protected void showProgressDialog(String title, String message, boolean cancelable) {

        showProgress();
//
//        if (title == null)
//            progressDialog.setTitle("بارگزاری داده");
//        else
//            progressDialog.setTitle(title);
//
//        progressDialog.setMessage(message);
//        progressDialog.configureMessageView(this::applyTextFont);
//        progressDialog.configureTitleView(this::applyTextFont);
//        progressDialog.setCancelable(cancelable);
//        progressDialog.show();
    }

    protected void showProgressDialog(@StringRes int title, @StringRes int message, @DrawableRes int icon, @ColorRes int color, boolean cancelable) {
//        progressDialog.setTitle(title);
//        progressDialog.setTopColorRes(color);
//        progressDialog.setIcon(icon);
//        progressDialog.setMessage(message);
//        progressDialog.configureMessageView(this::applyTextFont);
//        progressDialog.configureTitleView(this::applyTextFont);
//        progressDialog.setCancelable(cancelable);
//        progressDialog.show();
    }

    protected void hideProgressDialog() {
        hideProgress();

//        if (progressDialog != null) {
//            progressDialog.dismiss();
//        }
    }


}
