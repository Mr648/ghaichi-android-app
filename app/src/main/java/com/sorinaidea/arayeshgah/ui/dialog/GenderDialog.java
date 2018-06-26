package com.sorinaidea.arayeshgah.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.datahelper.Gender;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/**
 * Created by mr-code on 6/14/2018.
 */

public class GenderDialog extends Dialog implements View.OnClickListener {

    private AppCompatActivity activity;
    private ActivityUpdater activityUpdater;
    private String TAG = "GenderDialog";

    private void setActivityUpdater(ActivityUpdater activityUpdater) {
        this.activityUpdater = activityUpdater;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public GenderDialog(AppCompatActivity activity, AppCompatButton btnSelectGender) {
        super(activity);
        selectedGender = null;
        this.setActivity(activity);
        this.setActivityUpdater(new ActivityUpdater(btnSelectGender));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnSelect:
                selectGender();
                break;
        }
    }

    private AppCompatRadioButton btnMale;
    private AppCompatRadioButton btnFemale;
    private AppCompatButton btnSelect;
    private Gender selectedGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_gender);

        btnSelect = (AppCompatButton) findViewById(R.id.btnSelect);
        btnMale = (AppCompatRadioButton) findViewById(R.id.btnMale);
        btnFemale = (AppCompatRadioButton) findViewById(R.id.btnFemale);

        btnSelect.setOnClickListener(this);
    }

    private boolean validateGender() {
        return btnFemale.isChecked() || btnMale.isChecked();
    }

    private void selectGender() {
        if (!validateGender()) {
            return;
        }
        if (btnMale.isChecked()) {
            selectedGender = Gender.MALE;
        } else {
            selectedGender = Gender.FEMALE;
        }
        activity.runOnUiThread(activityUpdater);
    }


    private class ActivityUpdater implements Runnable {
        private AppCompatButton btnSelectGender;

        public ActivityUpdater(AppCompatButton btnSelectGender) {
            this.btnSelectGender = btnSelectGender;
        }

        @Override
        public void run() {
            btnSelectGender.setText(selectedGender.getType());
            dismiss();
        }

    }
}
