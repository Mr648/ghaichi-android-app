package com.sorinaidea.arayeshgah.ui;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.SorinaApplication;

/**
 * Created by mr-code on 2/17/2018.
 */

public class SorinaActivity extends AppCompatActivity {
    @Override
    protected void onResume() {

        super.onResume();
        SorinaApplication.currentActivity = this;
    }
}
