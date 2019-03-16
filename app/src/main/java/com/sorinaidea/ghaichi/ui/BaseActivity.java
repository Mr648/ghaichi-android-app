package com.sorinaidea.ghaichi.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.Arrays;


public class BaseActivity extends AppCompatActivity {

    protected Typeface fontText;
    protected Typeface fontIcon;

    protected void applyTextFont(View... views) {
        for (View view : views) {
            FontManager.setFont(view, fontText);

        }
    }

    protected void applyIconsFont(View... views) {
        for (View view : views) {
            FontManager.setFont(view, fontIcon);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fontText = FontManager.getTypeface(this, FontManager.IRANSANS_TEXTS);
        fontIcon = FontManager.getTypeface(this, FontManager.MATERIAL_ICONS);
    }

    protected void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static final String TAG_INFO = "ghaichi_info";
    public static final String TAG_DEBUG = "ghaichi_debug";
    public static final String TAG_ERROR = "ghaichi_error";
    public static final String TAG_VERBOSE = "ghaichi_verbose";
    public static final String TAG_WARNING = "ghaichi_warning";


    protected void logInfo(String message) {
        Log.i(TAG_INFO, message);
    }

    protected void logDebug(String message) {
        Log.d(TAG_DEBUG, message);
    }

    protected void logError(String message) {
        Log.e(TAG_ERROR, message);
    }

    protected void logVerbose(String message) {
        Log.v(TAG_VERBOSE, message);
    }

    protected void logWarning(String message) {
        Log.w(TAG_WARNING, message);
    }


    protected void logInfo(String message, Throwable t) {
        Log.i(TAG_INFO, message, t);
    }

    protected void logInfo(String... message) {
        Log.i(TAG_INFO, Arrays.toString(message));
    }

    protected void logDebug(String message, Throwable t) {
        Log.d(TAG_DEBUG, message, t);
    }

    protected void logError(String message, Throwable t) {
        Log.e(TAG_ERROR, message, t);
    }

    protected void logVerbose(String message, Throwable t) {
        Log.v(TAG_VERBOSE, message, t);
    }

    protected void logWarning(String message, Throwable t) {
        Log.w(TAG_WARNING, message, t);
    }


    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void toast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void longToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    protected void actionAlert(String title, String message, @DrawableRes int icon, @ColorRes int color, View.OnClickListener positiveButtonOnClickListener) {
        new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(color)
                .setButtonsColorRes(R.color.colorPrimary)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string._lbl_confirm, positiveButtonOnClickListener)
                .show();
    }

    protected void confirmAlert(String title, String message, @DrawableRes int icon, @ColorRes int color, View.OnClickListener positiveButtonOnClickListener) {
        new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(color)
                .setButtonsColorRes(R.color.colorPrimary)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string._lbl_confirm, positiveButtonOnClickListener)
                .setNegativeButton(R.string._lbl_cancel, null)
                .show();
    }

    protected void confirmAlert(String title, String message, @DrawableRes int icon, @ColorRes int color, String psvBtn, View.OnClickListener positiveButtonOnClickListener, String ngvBtn, View.OnClickListener negativeButtonOnClickListener) {

        LovelyStandardDialog dialog = new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(color)
                .setButtonsColorRes(R.color.colorPrimary)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(psvBtn, positiveButtonOnClickListener)
                .configureTitleView(this::applyTextFont)
                .configureMessageView(this::applyTextFont);

        if (negativeButtonOnClickListener != null) {
            dialog.setNegativeButton(ngvBtn, negativeButtonOnClickListener);
        } else {
            dialog.setNegativeButton(ngvBtn, v -> dialog.dismiss());
        }

        dialog.show();

    }

    protected void alert(String title, String message, @DrawableRes int icon, @ColorRes int color) {
        new LovelyInfoDialog(this)
                .setTopColorRes(color)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(message)
                .configureMessageView(this::applyTextFont)
                .configureTitleView(this::applyTextFont)
                .show();
    }
}
