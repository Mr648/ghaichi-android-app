package com.sorinaidea.ghaichi.ui.dialog;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.function.Predicate;

/**
 * Created by mr-code on 6/14/2018.
 */

public class AddServiceCategory extends Dialog implements View.OnClickListener {

    private AppCompatActivity activity;
    private ActivityUpdater activityUpdater;
    private String TAG = "InputDialog";
    private String message;
    private String hint;
    private int icon;
    private Typeface fontIranSans;


    private Predicate<String> validator;

    private void setActivityUpdater(ActivityUpdater activityUpdater) {
        this.activityUpdater = activityUpdater;
    }

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

    public AddServiceCategory(AppCompatActivity activity, AppCompatButton btn, Predicate validator, int icon, int message, int hint) {
        super(activity);
        this.setActivity(activity);
        this.setValidator(null);
        setMessage(activity.getResources().getString(message));
        setHint(activity.getResources().getString(hint));
        setIcon(icon);
        this.setActivityUpdater(new ActivityUpdater(btn));
        this.fontIranSans = FontManager.getTypeface(activity, FontManager.IRANSANS_TEXTS);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnOk:
                selectValue();
                break;
        }
    }

    private AppCompatButton btnCancel;
    private AppCompatButton btnOk;
    private AppCompatTextView txtTitle;
    private AppCompatImageView imgIcon;
    private TextInputLayout inputLayoutValue;
    private TextInputEditText txtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_input);

        btnCancel = (AppCompatButton) findViewById(R.id.btnCancel);
        btnOk = (AppCompatButton) findViewById(R.id.btnOk);
        txtTitle = (AppCompatTextView) findViewById(R.id.txtTitle);
        imgIcon = (AppCompatImageView) findViewById(R.id.imgIcon);
        inputLayoutValue = (TextInputLayout) findViewById(R.id.inputLayoutValue);
        txtValue = (TextInputEditText) findViewById(R.id.txtValue);


        txtTitle.setText(message);
        inputLayoutValue.setHint(hint);
        imgIcon.setImageResource(icon);

        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        FontManager.setFont(btnCancel, fontIranSans);
        FontManager.setFont(btnOk, fontIranSans);
        FontManager.setFont(txtTitle, fontIranSans);
        FontManager.setFont(inputLayoutValue, fontIranSans);
        FontManager.setFont(txtValue, fontIranSans);

    }


    private void selectValue() {
//        if (!validator.test(txtValue.getText().toString())) {
//            return;
//        }
        activity.runOnUiThread(activityUpdater);
    }


    private class ActivityUpdater implements Runnable {
        private AppCompatButton btn;

        public ActivityUpdater(AppCompatButton btnSelectGender) {
            this.btn = btnSelectGender;
        }

        @Override
        public void run() {
            btn.setText(txtValue.getText());
            dismiss();
        }

    }
}
