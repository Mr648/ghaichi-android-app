package com.sorinaidea.ghaichi.ui.barbershop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.ui.BaseFragment;


public class AccountActivationFragment extends BaseFragment {


    private Button btnPay;

    private TextView txt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_activation, container, false);
    }

    @Override
    protected void setup(View view) {

        btnPay = view.findViewById(R.id.btnPay);

        txt = view.findViewById(R.id.txtContent);

        btnPay.setOnClickListener((v) -> {
            Toast.makeText(getActivity(), "درخواست در حال ارسال...", Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    protected void setFonts() {
        applyTextFont(
                btnPay,
                txt
        );
    }
}
