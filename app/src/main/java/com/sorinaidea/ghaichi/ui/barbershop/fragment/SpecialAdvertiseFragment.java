package com.sorinaidea.ghaichi.ui.barbershop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.Pricing;
import com.sorinaidea.ghaichi.ui.barbershop.activity.UiUpdater;


public class SpecialAdvertiseFragment extends AdvertiseFragment implements UiUpdater {


    private TextInputLayout inputLayoutDescription;
    private TextInputEditText txtDescription;
    private AppCompatButton btnNumberOfViews;
    private Button btnRequest;
    private Pricing selectedPricing;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Advertiser) {
            advertiser = (Advertiser) context;
        } else {
            throw new RuntimeException("must implement Advertiser");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_special_advertise, container, false);
    }


    @Override
    public void update(Pricing selectedPricing) {
        if (btnNumberOfViews != null) {
            btnNumberOfViews.setText(selectedPricing.getDescription());
            this.selectedPricing = selectedPricing;
        }
    }

    @Override
    protected void setup(View view) {
        inputLayoutDescription = view.findViewById(R.id.inputLayoutDescription);
        txtDescription = view.findViewById(R.id.txtDescription);
        btnNumberOfViews = view.findViewById(R.id.btnNumberOfViews);
        btnRequest = view.findViewById(R.id.btnRequest);
        btnNumberOfViews.setOnClickListener(pricingClickListener);
        btnRequest.setOnClickListener((v) -> {
            if (selectedPricing == null) {
                toast("ابتدا تعداد بازدید مورد نظر را انتخاب نمایید.");
                return;
            }
            if (txtDescription.getText().toString().isEmpty()) {
                toast("درباره تبلیغات خود حداقل یک جمله توضیح بنویسید.");
                return;
            }
            advertiser.requestSpecialAdvertise(selectedPricing, txtDescription.getText().toString());
        });
    }

    @Override
    protected void setFonts() {
        applyTextFont(
                inputLayoutDescription,
                txtDescription,
                btnRequest
        );
    }


}
