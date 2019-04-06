//package com.sorinaidea.ghaichi.ui;
//
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.preference.PreferenceActivity;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.SwitchCompat;
//import android.support.v7.widget.Toolbar;
//import android.view.MenuItem;
//import android.widget.TextView;
//
//import com.sorinaidea.ghaichi.R;
//import com.sorinaidea.ghaichi.util.FontManager;
//
//
///**
// * Created by mr-code on 5/15/2018.
// */
//
//public class SettingActivity extends AppCompatActivity {
//
//    private Toolbar toolbar;
//    private SwitchCompat swShowNotification;
//    private SwitchCompat swGetNews;
//
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_settings);
//        setContentView(R.layout.activity_barber_settings);
//
//        toolbar = findViewById(R.id.toolbar);
//        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
//        mTitle.setText("تنظیمات");
//        swShowNotification = findViewById(R.id.swShowNotification);
//        swGetNews = findViewById(R.id.swGetNews);
//
//        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);
//
//        FontManager.setFont(swShowNotification, iranSans);
//        FontManager.setFont(swGetNews, iranSans);
//        FontManager.setFont(mTitle, iranSans);
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        switch (id) {
//            case android.R.id.home:
//                onBackPressed();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
