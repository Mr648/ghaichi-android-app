package com.sorinaidea.ghaichi.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by mr-code on 2/14/2018.
 */

public class FontManager {

    public static final String ROOT = "fonts/";

    public static final String MATERIAL_ICONS = ROOT + "materialIcons-regular.ttf";
    public static final String SOCIAL_ICONS = ROOT + "socicon.ttf";
    public static final String IRANSANS_TEXTS = ROOT + "irsans.ttf";


    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    public static void setFont(final View view, final Typeface typeface) {
        if (view instanceof TextView) {
            ((TextView)view).setTypeface(typeface);
        } else if (view instanceof Button) {
            ((Button)view).setTypeface(typeface);
        }else if (view instanceof AppCompatButton) {
            ((AppCompatButton)view).setTypeface(typeface);
        }else if (view instanceof AppCompatTextView) {
            ((AppCompatTextView)view).setTypeface(typeface);
        }else if (view instanceof AppCompatRadioButton) {
            ((AppCompatRadioButton)view).setTypeface(typeface);
        }else if (view instanceof TextInputLayout) {
            ((TextInputLayout)view).setTypeface(typeface);
        }else if (view instanceof TextInputEditText) {
            ((TextInputEditText)view).setTypeface(typeface);
        }else if (view instanceof SwitchCompat) {
            ((SwitchCompat)view).setTypeface(typeface);
        }else if (view instanceof RadioButton) {
            ((RadioButton)view).setTypeface(typeface);
        }else if (view instanceof CheckBox) {
            ((CheckBox)view).setTypeface(typeface);
        }
    }
    public static void markAsContainer(final View view, final Typeface typeface) {
        for (View v: view.getTouchables()) {
            if (v instanceof TextView) {
                ((TextView)v).setTypeface(typeface);
            } else if (view instanceof Button) {
                ((Button)v).setTypeface(typeface);
            }
        }
    }
}
