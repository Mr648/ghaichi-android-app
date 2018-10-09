package com.sorinaidea.ghaichi.ui.dialog;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.function.Predicate;

/**
 * Created by mr-code on 6/14/2018.
 */

public class AddSampleDialog extends Dialog implements View.OnClickListener {

    private AppCompatActivity activity;
    private String TAG = "AddServiceDialog";
    private String message;
    private String hint;
    private int icon;
    private Typeface fontIranSans;


    private Predicate<String> validator;


    private void setValidator(Predicate<String> validator) {
        this.validator = validator;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private void setIcon(int icon) {
        this.icon = icon;
    }


    private void setHint(String hint) {
        this.hint = hint;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public AddSampleDialog(AppCompatActivity activity) {
        super(activity);
        this.setActivity(activity);
        this.fontIranSans = FontManager.getTypeface(activity, FontManager.IRANSANS_TEXTS);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnOk:

                break;
        }
    }

    private AppCompatButton btnCancel;
    private AppCompatButton btnOk;
    private AppCompatTextView txtTitle;


    private TextInputLayout inputLayoutName;
    private TextInputEditText txtName;


    private Spinner spnCategories;
    private Spinner spnServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_add_sample_work);

        btnCancel = (AppCompatButton) findViewById(R.id.btnCancel);
        btnOk = (AppCompatButton) findViewById(R.id.btnOk);
        txtTitle = (AppCompatTextView) findViewById(R.id.txtTitle);
        spnCategories = (Spinner) findViewById(R.id.spnCategories);
        spnServices = (Spinner) findViewById(R.id.spnServices);

        inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        txtName = (TextInputEditText) findViewById(R.id.txtName);

        txtTitle.setText("افزودن خدمت");

        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);


        String[] CATEGORIES = {"دسته 1", "دسته 2", "دسته 3", "دسته 4", "دسته 5", "دسته 6"};
        ArrayAdapter<String> adapterCategories = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, CATEGORIES);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategories.setAdapter(adapterCategories);


        String[] SERVICES = {"سرویس 1", "سرویس 2", "سرویس 3", "سرویس 4", "سرویس 5", "سرویس 6"};
        ArrayAdapter<String> adapterServices = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, SERVICES);
        adapterServices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnServices.setAdapter(adapterServices);


        FontManager.setFont(btnCancel, fontIranSans);
        FontManager.setFont(btnOk, fontIranSans);
        FontManager.setFont(txtTitle, fontIranSans);

        FontManager.setFont(inputLayoutName, fontIranSans);
        FontManager.setFont(txtName, fontIranSans);

    }


}
