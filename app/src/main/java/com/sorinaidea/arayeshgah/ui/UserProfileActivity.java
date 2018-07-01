package com.sorinaidea.arayeshgah.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.util.FontManager;

/**
 * Created by mr-code on 6/17/2018.
 */

public class UserProfileActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    private TextView txtLb1;
    private TextView txtName;
    private TextView txtLb2;
    private TextView txtFamily;
    private TextView txtLb3;
    private TextView txtGender;
    private TextView txtLb4;
    private TextView txtPhone;
    private TextView txtLb5;
    private TextView txtTitle;
    private TextView txtHeaderName;
    private TextView txtHeaderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        bindActivity();

        mAppBarLayout.addOnOffsetChangedListener(this);

        mToolbar.inflateMenu(R.menu.activity_user_profile);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);


        txtLb1 = (TextView) findViewById(R.id.txtLb1);
        txtName = (TextView) findViewById(R.id.txtName);
        txtLb2 = (TextView) findViewById(R.id.txtLb2);
        txtFamily = (TextView) findViewById(R.id.txtFamily);
        txtLb3 = (TextView) findViewById(R.id.txtLb3);
        txtGender = (TextView) findViewById(R.id.txtGender);
        txtLb4 = (TextView) findViewById(R.id.txtLb4);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtLb5 = (TextView) findViewById(R.id.txtLb5);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtHeaderName = (TextView) findViewById(R.id.txtHeaderName);
        txtHeaderNumber = (TextView) findViewById(R.id.txtHeaderNumber);

        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);


        FontManager.setFont(txtLb1, iranSans);
        FontManager.setFont(txtName, iranSans);
        FontManager.setFont(txtLb2, iranSans);
        FontManager.setFont(txtFamily, iranSans);
        FontManager.setFont(txtLb3, iranSans);
        FontManager.setFont(txtGender, iranSans);
        FontManager.setFont(txtLb4, iranSans);
        FontManager.setFont(txtPhone, iranSans);
        FontManager.setFont(txtLb5, iranSans);
        FontManager.setFont(txtTitle, iranSans);
        FontManager.setFont(txtHeaderName, iranSans);
        FontManager.setFont(txtHeaderNumber, iranSans);

    }

    private void bindActivity() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle = (TextView) findViewById(R.id.txtTitle);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_user_profile, menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

}
