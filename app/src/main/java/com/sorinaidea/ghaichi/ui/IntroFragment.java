package com.sorinaidea.ghaichi.ui;


import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;

/**
 * A simple {@link BaseFragment} subclass.
 * Use the {@link IntroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroFragment extends BaseFragment {


    private static final String ARG_TEXT = "text";
    private static final String ARG_ICON = "icon";

    @StringRes
    private int text;

    @DrawableRes
    private int icon;


    public IntroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param text
     * @param icon
     * @return instance of IntroFragment.
     */
    public static IntroFragment newInstance(@StringRes int text, @DrawableRes int icon) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TEXT, text);
        args.putInt(ARG_ICON, icon);
        fragment.setArguments(args);
        return fragment;
    }


    private AppCompatImageView img;
    private TextView txt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text = getArguments().getInt(ARG_TEXT);
            icon = getArguments().getInt(ARG_ICON);
        }
    }

    @Override
    protected void setup(View view) {
        txt = view.findViewById(R.id.txt);
        img = view.findViewById(R.id.img);

        txt.setText(text);
        img.setImageResource(icon);
    }

    @Override
    protected void setFonts() {
        applyTextFont(txt);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

}
