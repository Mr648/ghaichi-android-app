package com.sorinaidea.ghaichi.ui.barbershop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.ui.BaseFragment;


public class AccountActivatedFragment extends BaseFragment {


    private TextView txtNum;
    private TextView txtLabel;
    private TextView txtDescription;
    private TextView txtContent;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_activated, container, false);
    }

    @Override
    protected void setup(View view) {

        txtNum = view.findViewById(R.id.txtNum);
        txtLabel = view.findViewById(R.id.txtLabel);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtContent = view.findViewById(R.id.txtContent);

    }

    @Override
    protected void setFonts() {
        applyTextFont(
                txtNum,
                txtLabel,
                txtDescription,
                txtContent
        );
    }


}
