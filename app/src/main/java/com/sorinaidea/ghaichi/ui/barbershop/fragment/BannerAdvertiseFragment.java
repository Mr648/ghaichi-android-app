package com.sorinaidea.ghaichi.ui.barbershop.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;


public class BannerAdvertiseFragment extends Fragment {



    View.OnClickListener pricingClickListener;

    public void setPricingClickListener(View.OnClickListener pricingClickListener) {
        this.pricingClickListener = pricingClickListener;
    }

    private Typeface fontIranSans;
    private TextInputLayout inputLayoutDescription;
    private TextInputEditText txtDescription;
    private AppCompatButton btnNumberOfViews;
    private Button btnRequest;

    private int selectedNumberOfViews = Integer.MIN_VALUE;

    private void setupInputs(View view) {
        inputLayoutDescription = view.findViewById(R.id.inputLayoutDescription);
        txtDescription = view.findViewById(R.id.txtDescription);
        btnNumberOfViews = view.findViewById(R.id.btnNumberOfViews);
        btnRequest = view.findViewById(R.id.btnRequest);
        btnNumberOfViews.setOnClickListener(pricingClickListener);
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
        return inflater.inflate(R.layout.fragment_banner_advertise, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        setupInputs(view);
        setFonts();
    }
}
