package com.sorinaidea.ghaichi.ui.barbershop.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;


public class AccountActivatedFragment extends Fragment {


    private Typeface fontIranSans;

    private TextView txtNum;
    private TextView txtLabel;
    private TextView txtDescription;
    private TextView txtContent;




    private void setupInputs(View view) {

        txtNum= (TextView) view.findViewById(R.id.txtContent);
        txtLabel= (TextView) view.findViewById(R.id.txt1);
        txtDescription= (TextView) view.findViewById(R.id.txt2);
        txtContent= (TextView) view.findViewById(R.id.txt3);


    }

    private void setFonts() {
        fontIranSans = FontManager.getTypeface(getContext(), FontManager.IRANSANS_TEXTS);
        FontManager.setFont(txtNum, fontIranSans);
        FontManager.setFont(txtLabel, fontIranSans);
        FontManager.setFont(txtDescription, fontIranSans);
        FontManager.setFont(txtContent, fontIranSans);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_activated, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        setupInputs(view);
        setFonts();
    }
}
