package com.sorinaidea.ghaichi.ui.barbershop.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;


public class SpecialAdvertiseFragment extends Fragment {

    private final String[] ITEMS = {
            "انتخاب تعداد بازدید",
            "1000",
            "5000",
            "10000",
            "15000"
    };

    private Typeface fontIranSans;
    private TextInputLayout inputLayoutDescription;
    private TextInputEditText txtDescription;
    private Spinner spnNumberOfViews;
    private Button btnRequest;
    private int selectedNumberOfViews = Integer.MIN_VALUE;

    private void setupViews(View view) {
        inputLayoutDescription = view.findViewById(R.id.inputLayoutDescription);
        txtDescription = view.findViewById(R.id.txtDescription);
        spnNumberOfViews = view.findViewById(R.id.spnNumberOfViews);
        btnRequest = view.findViewById(R.id.btnRequest);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnNumberOfViews.setAdapter(adapter);
        spnNumberOfViews.setSelection(0);

        spnNumberOfViews.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedNumberOfViews = Integer.parseInt(ITEMS[position]);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRequest.setOnClickListener((v) -> {
            Toast.makeText(getContext(), "درخواست در حال ارسال...", Toast.LENGTH_SHORT).show();
        });
    }

    private void setFonts() {
        fontIranSans = FontManager.getTypeface(getContext(), FontManager.IRANSANS_TEXTS);
        FontManager.setFont(inputLayoutDescription, fontIranSans);
        FontManager.setFont(txtDescription, fontIranSans);
        FontManager.setFont(btnRequest, fontIranSans);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_special_advertise, container, false);
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        setupViews(view);
        setFonts();
    }
}
