package com.sorinaidea.ghaichi.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;

public class CityChooserActivity extends AppCompatActivity {

    private Spinner spnProvinces;
    private Spinner spnCities;
    private Toolbar toolbar;
    private Typeface fontIranSans;
    private TextView mTitle;
    private Button btnNextStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citychooser);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.toolbar_choose_city);
        setup();
    }

    private static int selectedCity = -1;
    private static int selectedProvince = -1;

    private void setFonts() {
        FontManager.setFont(mTitle, fontIranSans);
        FontManager.setFont(btnNextStep, fontIranSans);
    }

    private void setup() {
        spnProvinces = (Spinner) findViewById(R.id.spnProvinces);
        spnCities = (Spinner) findViewById(R.id.spnCities);
        btnNextStep = (Button) findViewById(R.id.btnNextStep);


        String[] provinces = {
                "انتخاب استان ...",
                "کردستان",
                "تهران",
        };

        String[][] cities = {
                {
                        "انتخاب شهرستان ..."
                },
                {
                        "سنندج",
                        "سقز",
                        "بانه",
                        "مریوان",
                        "قروه",
                        "کامیاران",
                        "بیجار",
                        "دیواندره",
                        "دهگلان",
                        "سروآباد"
                },
                {
                        "تهران",
                        "شهریار",
                        "اسلامشهر",
                        "بهارستان",
                        "ملارد",
                        "پاکدشت",
                        "ری",
                        "قدس",
                        "رباط",
                        "کریم",
                        "ورامین",
                        "قرچک",
                        "پردیس",
                        "دماوند",
                        "پیشوا",
                        "شمیرانات",
                        "فیروزکوه"
                },
        };


        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinces);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProvinces.setAdapter(provinceAdapter);
        spnProvinces.setSelection(0);

        ArrayAdapter<String> cityDefaultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities[0]);
        cityDefaultAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCities.setAdapter(cityDefaultAdapter);
        spnCities.setSelection(0);


        spnProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedProvince = position;
                    spnCities.setEnabled(true);
                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(CityChooserActivity.this, android.R.layout.simple_spinner_item, cities[position]);
                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnCities.setAdapter(cityAdapter);
                    spnCities.setSelection(0);
                } else {
                    spnCities.setAdapter(cityDefaultAdapter);
                    spnCities.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fontIranSans = FontManager.getTypeface(this, FontManager.IRANSANS_TEXTS);
        setFonts();
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
