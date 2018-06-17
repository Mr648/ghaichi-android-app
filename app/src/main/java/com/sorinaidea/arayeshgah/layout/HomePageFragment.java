package com.sorinaidea.arayeshgah.layout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.BarberShopCategoryAdapter;
import com.sorinaidea.arayeshgah.adapter.ImageSliderAdapter;
import com.sorinaidea.arayeshgah.adapter.TopBarberShopUserAdabper;
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


    private RecyclerView recCategories;
    private RecyclerView recTopBarberShops;
    private ViewPager mPager;
    private CircleIndicator indicator;
    private static int currentPage = 0;
    private static final List<String> images = Arrays.asList("", "","","","");

    /*private static final List<String> images = Arrays.asList(R.drawable.ic_bug_report_black_18dp,
            R.drawable.ic_credit_card_black_18dp,
            R.drawable.ic_favorite_black_18dp,
            R.drawable.ic_help_black_18dp,
            R.drawable.ic_home_black_18dp,
            R.drawable.ic_instagram,
            R.drawable.ic_linkedin,
            R.drawable.ic_twitter);
*/
    private ArrayList<String> imageList = new ArrayList<>();

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
        recCategories = (RecyclerView) view.findViewById(R.id.recCategories);

        recCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        BarberShopCategoryAdapter adapter = new BarberShopCategoryAdapter(getContext(), initProductItems());
        recCategories.setAdapter(adapter);
        recCategories.setNestedScrollingEnabled(false);


        initializeImageSlider();
    }
    private ArrayList<String> initProductItems() {
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList("Category #1", "Category #2", "Category #3", "Category #4", "Category #5", "Category #6"
                , "Category #7"
                , "Category #8"
                , "Category #9"
                , "Category #10"
                , "Category #11"
                , "Category #12"
                , "Category #13"
                , "Category #14"
                , "Category #15"
                , "Category #16"));
        return list;
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
