package com.sorinaidea.ghaichi.ui.barbershop.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;


public class AccountActivationFragment extends Fragment {


    private Typeface fontIranSans;

    private Button btnPay;

    private TextView txt;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;


    private CheckBox chk1Month;
    private CheckBox chk3Month;
    private CheckBox chk6Month;
    private CheckBox chk12Month;


    private void setupInputs(View view) {

        btnPay = (Button) view.findViewById(R.id.btnPay);

        txt = (TextView) view.findViewById(R.id.txtContent);
        txt1 = (TextView) view.findViewById(R.id.txt1);
        txt2 = (TextView) view.findViewById(R.id.txt2);
        txt3 = (TextView) view.findViewById(R.id.txt3);
        txt4 = (TextView) view.findViewById(R.id.txt4);

        chk1Month = (CheckBox) view.findViewById(R.id.radio1Month);
        chk3Month = (CheckBox) view.findViewById(R.id.radio3Month);
        chk6Month = (CheckBox) view.findViewById(R.id.radio6Month);
        chk12Month = (CheckBox) view.findViewById(R.id.radio12Month);

        btnPay.setOnClickListener((v) -> {
            Toast.makeText(getContext(), "درخواست در حال ارسال...", Toast.LENGTH_SHORT).show();
        });

        chk1Month.setOnCheckedChangeListener((btnView, isChecked) -> {
            clearRadioChecked();
            chk1Month.setChecked(isChecked);
        });
        chk3Month.setOnCheckedChangeListener((btnView, isChecked) -> {
            clearRadioChecked();
            chk3Month.setChecked(isChecked);
        });
        chk6Month.setOnCheckedChangeListener((btnView, isChecked) -> {
            clearRadioChecked();
            chk6Month.setChecked(isChecked);
        });
        chk12Month.setOnCheckedChangeListener((btnView, isChecked) -> {
            clearRadioChecked();
            chk12Month.setChecked(isChecked);
        });
    }

    private void clearRadioChecked() {
        chk1Month.setChecked(false);
        chk3Month.setChecked(false);
        chk6Month.setChecked(false);
        chk12Month.setChecked(false);
    }


    private void setFonts() {
        fontIranSans = FontManager.getTypeface(getContext(), FontManager.IRANSANS_TEXTS);
        FontManager.setFont(btnPay, fontIranSans);
        FontManager.setFont(txt, fontIranSans);
        FontManager.setFont(txt1, fontIranSans);
        FontManager.setFont(txt2, fontIranSans);
        FontManager.setFont(txt3, fontIranSans);
        FontManager.setFont(txt4, fontIranSans);
        FontManager.setFont(chk1Month, fontIranSans);
        FontManager.setFont(chk3Month, fontIranSans);
        FontManager.setFont(chk6Month, fontIranSans);
        FontManager.setFont(chk12Month, fontIranSans);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_activation, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        setupInputs(view);
        setFonts();
    }
}
