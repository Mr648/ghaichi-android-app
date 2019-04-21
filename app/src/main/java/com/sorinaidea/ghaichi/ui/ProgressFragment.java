package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;


public class ProgressFragment extends BaseFragment {

    private static final String ARG_TEXT = "text";

    @StringRes
    private int text = R.string.empty_string;


    public ProgressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param text
     * @return instance of ProgressFragment.
     */
    public static ProgressFragment newInstance(@StringRes int text) {
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text = getArguments().getInt(ARG_TEXT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_progress, container, false);
    }

    TextView txt;

    @Override
    protected void setup(View view) {
        txt = view.findViewById(R.id.txt);
        txt.setText(text);
    }

    @Override
    protected void setFonts() {
        applyTextFont(txt);
    }
}
