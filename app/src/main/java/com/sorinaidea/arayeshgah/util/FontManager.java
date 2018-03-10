package com.sorinaidea.arayeshgah.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
