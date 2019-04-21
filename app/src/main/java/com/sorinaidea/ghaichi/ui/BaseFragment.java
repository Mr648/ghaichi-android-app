package com.sorinaidea.ghaichi.ui;

import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.sorinaidea.ghaichi.util.FontManager;

public abstract class BaseFragment extends Fragment {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fontText = FontManager.getTypeface(getActivity(), FontManager.IRANSANS_TEXTS);
        fontIcon = FontManager.getTypeface(getActivity(), FontManager.MATERIAL_ICONS);
    }

    protected void toast(String message) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    protected abstract void setup(View view);

    protected abstract void setFonts();

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        setup(view);
        setFonts();
    }
}
