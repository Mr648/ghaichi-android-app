package com.sorinaidea.ghaichi.ui.dialog;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.components.JDF;
import com.alirezaafkar.sundatepicker.interfaces.DateSetListener;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mr-code on 6/14/2018.
 */

public class TransactionDialog extends Dialog implements View.OnClickListener, DateSetListener {

    private AppCompatActivity activity;
    private String TAG = "TransactionDialog";
    private Typeface fontIranSans;

    public TransactionDialog(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
        this.fontIranSans = FontManager.getTypeface(activity, FontManager.IRANSANS_TEXTS);

    }

    private AppCompatButton btnCancel;
    private AppCompatButton btnOk;
    private AppCompatButton btnDate;
    private AppCompatTextView txtMessage;
    private AppCompatImageView imgIconLeft;
    private AppCompatImageView imgIconRight;

    private String textMessage;
    private int imgIconResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.transaction_dialog);

        btnCancel = (AppCompatButton) findViewById(R.id.btnCancel);
        btnOk = (AppCompatButton) findViewById(R.id.btnOk);
        btnDate = (AppCompatButton) findViewById(R.id.btnDate);

        txtMessage = (AppCompatTextView) findViewById(R.id.txtMessage);

        btnDate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        FontManager.setFont(btnCancel, fontIranSans);
        FontManager.setFont(btnOk, fontIranSans);
        FontManager.setFont(txtMessage, fontIranSans);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnOk:
                dismiss();
                break;

            case R.id.btnDate:
                @StyleRes int theme =
                        R.style.DialogTheme;
                Date mStartDate = new Date(System.currentTimeMillis());
                int id = 0x1010101;
                ;
                new DatePicker.Builder()
                        .id(id)
                        .theme(theme)
                        .date(Calendar.getInstance(new Locale("fa")))
                        .build(TransactionDialog.this)
                        .show(activity.getSupportFragmentManager(), "");
                break;
        }
    }

    @Override
    public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year) {
        if (id == 0x1010101) {
            btnDate.setText(String.format("%d/%d/%d", year, month, day));
        }
    }


}
