package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.barbershop.BarberAdabper;
import com.sorinaidea.ghaichi.util.FontManager;

public class EditBarberActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutPrice;
    private TextInputLayout inputLayoutDuration;
    private TextInputLayout inputLayoutDiscount;
    private TextInputLayout inputLayoutDescription;

    private TextInputEditText txtName;
    private TextInputEditText txtPrice;
    private TextInputEditText txtDuration;
    private TextInputEditText txtDiscount;
    private TextInputEditText txtDescription;

    private TextView txtListOfBarbers;
    private TextView txtListOfReservations;
    private TextView mTitle;

    private RecyclerView recBarbers;
    private RecyclerView recPhotos;

    private Spinner spnCategories;

    private FloatingActionButton fabAddPhoto;

    private Typeface fontIranSans;

    private void setupInputs() {
        inputLayoutName = findViewById(R.id.inputLayoutName);
        inputLayoutPrice = findViewById(R.id.inputLayoutPrice);
        inputLayoutDuration = findViewById(R.id.inputLayoutDuration);
        inputLayoutDiscount = findViewById(R.id.inputLayoutDiscount);
        inputLayoutDescription = findViewById(R.id.inputLayoutDescription);

        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDuration = findViewById(R.id.txtDuration);
        txtDiscount = findViewById(R.id.txtDiscount);
        txtDescription = findViewById(R.id.txtDescription);
    }

    private void setupLists() {
        recBarbers = findViewById(R.id.recBanners);
        spnCategories = findViewById(R.id.spnCategories);

        recBarbers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recBarbers.setAdapter(new BarberAdabper(this));

        String[] ITEMS = {"انتخاب تعداد بازدید",
                "دسته 1", "دسته 2", "دسته 3", "دسته 4", "دسته 5", "دسته 6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategories.setAdapter(adapter);
        spnCategories.setSelection(0);

    }

    private void setFonts() {
        fontIranSans = FontManager.getTypeface(this, FontManager.IRANSANS_TEXTS);


        FontManager.setFont(inputLayoutName, fontIranSans);
        FontManager.setFont(inputLayoutPrice, fontIranSans);
        FontManager.setFont(inputLayoutDuration, fontIranSans);
        FontManager.setFont(inputLayoutDiscount, fontIranSans);
        FontManager.setFont(inputLayoutDescription, fontIranSans);

        FontManager.setFont(txtName, fontIranSans);
        FontManager.setFont(txtPrice, fontIranSans);
        FontManager.setFont(txtDuration, fontIranSans);
        FontManager.setFont(txtDiscount, fontIranSans);
        FontManager.setFont(txtDescription, fontIranSans);

        FontManager.setFont(txtListOfBarbers, fontIranSans);
        FontManager.setFont(txtListOfReservations, fontIranSans);

        FontManager.setFont(mTitle, fontIranSans);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.toolbar_add_service);


        setupInputs();
        setupLists();
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
