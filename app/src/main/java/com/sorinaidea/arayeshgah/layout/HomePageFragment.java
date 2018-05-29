package com.sorinaidea.arayeshgah.layout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.ImageSliderAdapter;
import com.sorinaidea.arayeshgah.model.FAQ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class HomePageFragment extends Fragment {

    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fargment_home, container, false);
    }

    private ViewPager mPager;
    private CircleIndicator indicator;
    private static int currentPage = 0;
    private static final List<Integer> images = Arrays.asList(R.drawable.ic_bug_report_black_18dp,
            R.drawable.ic_credit_card_black_18dp,
            R.drawable.ic_favorite_black_18dp,
            R.drawable.ic_help_black_18dp,
            R.drawable.ic_home_black_18dp,
            R.drawable.ic_instagram,
            R.drawable.ic_linkedin,
            R.drawable.ic_twitter);

    private ArrayList<Integer> imageList = new ArrayList<Integer>();

    private void initializeImageSlider() {

        imageList.addAll(images);
        mPager.setAdapter(new ImageSliderAdapter(getContext(), imageList));
        indicator.setViewPager(mPager);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.size()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        mPager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        initializeImageSlider();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
