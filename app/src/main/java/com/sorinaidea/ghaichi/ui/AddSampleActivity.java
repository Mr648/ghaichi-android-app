package com.sorinaidea.ghaichi.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;

/**
 * Created by mr-code on 5/15/2018.
 */

public class AddSampleActivity extends AppCompatActivity {

    private AppCompatButton btnCancel;
    private AppCompatButton btnOk;
    private AppCompatTextView txtTitle;


    private TextInputLayout inputLayoutName;
    private TextInputEditText txtName;


    private Spinner spnCategories;
    private Spinner spnServices;
    private Typeface fontIranSans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_add_sample_work);
        this.fontIranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);


        btnCancel = (AppCompatButton) findViewById(R.id.btnCancel);
        btnOk = (AppCompatButton) findViewById(R.id.btnOk);
        txtTitle = (AppCompatTextView) findViewById(R.id.txtTitle);
        spnCategories = (Spinner) findViewById(R.id.spnCategories);
        spnServices = (Spinner) findViewById(R.id.spnServices);

        inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        txtName = (TextInputEditText) findViewById(R.id.txtName);

        txtTitle.setText("افزودن خدمت");


        String[] CATEGORIES = {"دسته 1", "دسته 2", "دسته 3", "دسته 4", "دسته 5", "دسته 6"};
        ArrayAdapter<String> adapterCategories = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, CATEGORIES);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategories.setAdapter(adapterCategories);


        String[] SERVICES = {"سرویس 1", "سرویس 2", "سرویس 3", "سرویس 4", "سرویس 5", "سرویس 6"};
        ArrayAdapter<String> adapterServices = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, SERVICES);
        adapterServices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnServices.setAdapter(adapterServices);


        FontManager.setFont(btnCancel, fontIranSans);
        FontManager.setFont(btnOk, fontIranSans);
        FontManager.setFont(txtTitle, fontIranSans);

        FontManager.setFont(inputLayoutName, fontIranSans);
        FontManager.setFont(txtName, fontIranSans);
    }

}
