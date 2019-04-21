package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.GhaichiPreferenceManager;

import me.relex.circleindicator.CircleIndicator;

public class ActivityIntro extends BaseActivity {

    private final static String KEY_INTRO_SHOWN = "intro_shown";
    private final static int INTRO_PAGES_COUNT = 4;

    ViewPager pager;
    CircleIndicator indicator;
    AppCompatButton btnGo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        boolean shown = GhaichiPreferenceManager.getBoolean(this, KEY_INTRO_SHOWN, false);
        if (shown) {
            goNext(null);
        } else {
            pager = findViewById(R.id.pager);
            indicator = findViewById(R.id.indicator);
            btnGo = findViewById(R.id.btnGo);
            pager.setAdapter(new IntroAdapter(getSupportFragmentManager()));
            indicator.setViewPager(pager);
            btnGo.setOnClickListener(this::goNext);
            applyTextFont(btnGo);
        }
    }

    private void goNext(View view) {
        GhaichiPreferenceManager.putBoolean(this, KEY_INTRO_SHOWN, true);
        Intent intent = new Intent(this, LoginCheckActivity.class);
        startActivity(intent);
        finish();
    }

    private class IntroAdapter extends FragmentPagerAdapter {

        public IntroAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public IntroFragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return IntroFragment.newInstance(R.string.intro_more_customers, R.drawable.intro_more_customers);
                case 1:
                    return IntroFragment.newInstance(R.string.intro_online_anytime, R.drawable.intro_online_anytime);
                case 2:
                    return IntroFragment.newInstance(R.string.intro_easy_reserve, R.drawable.intro_easy_reserve);
                case 3:
                    return IntroFragment.newInstance(R.string.intro_compare, R.drawable.intro_compare);
                default:
                    return IntroFragment.newInstance(R.string.intro_compare, R.drawable.intro_compare);
            }
        }

        @Override
        public int getCount() {
            return INTRO_PAGES_COUNT;
        }
    }
}
