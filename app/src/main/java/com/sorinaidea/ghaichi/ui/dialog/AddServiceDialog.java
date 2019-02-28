package com.sorinaidea.ghaichi.ui.dialog;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
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

public class AddServiceDialog extends Dialog implements View.OnClickListener {

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

    public AddServiceDialog(AppCompatActivity activity) {
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

    private TextInputLayout inputLayoutPrice;
    private TextInputEditText txtPrice;

    private TextInputLayout inputLayoutName;
    private TextInputEditText txtName;

    private TextInputLayout inputLayoutDuration;
    private TextInputEditText txtDuration;
    private Spinner spnCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_service);

        btnCancel = findViewById(R.id.btnCancel);
        btnOk = findViewById(R.id.btnOk);
        txtTitle = findViewById(R.id.txtTitle);
        spnCategories = findViewById(R.id.spnCategories);

        inputLayoutPrice = findViewById(R.id.inputLayoutPrice);
        txtPrice = findViewById(R.id.txtPrice);
        inputLayoutName = findViewById(R.id.inputLayoutName);
        txtName = findViewById(R.id.txtName);
        inputLayoutDuration = findViewById(R.id.inputLayoutDuration);
        txtDuration = findViewById(R.id.txtDuration);

        txtTitle.setText("افزودن خدمت");

        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);


        String[] ITEMS = {"دسته 1", "دسته 2", "دسته 3", "دسته 4", "دسته 5", "دسته 6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategories.setAdapter(adapter);


        FontManager.setFont(btnCancel, fontIranSans);
        FontManager.setFont(btnOk, fontIranSans);
        FontManager.setFont(txtTitle, fontIranSans);

        FontManager.setFont(inputLayoutPrice, fontIranSans);
        FontManager.setFont(txtPrice, fontIranSans);
        FontManager.setFont(inputLayoutName, fontIranSans);
        FontManager.setFont(txtName, fontIranSans);
        FontManager.setFont(inputLayoutDuration, fontIranSans);
        FontManager.setFont(txtDuration, fontIranSans);
    }
}
