package com.sorinaidea.ghaichi.ui.barbershop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.models.Pricing;
import com.sorinaidea.ghaichi.ui.barbershop.activity.UiUpdater;

import java.io.File;


public class BannerAdvertiseFragment extends AdvertiseFragment implements UiUpdater {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ImagePicker) {
            imagePicker = (ImagePicker) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ImagePicker");
        }

        if (context instanceof Advertiser) {
            advertiser = (Advertiser) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Advertiser");
        }
    }


    private TextInputLayout inputLayoutDescription;
    private TextInputEditText txtDescription;
    private AppCompatButton btnNumberOfViews;
    private AppCompatImageView imgBanner;
    private Button btnRequest;


    private ImagePicker imagePicker;
    private Pricing selectedPricing;
//
//    // TODO: Rename and change types and number of parameters
//    public static PlusOneFragment newInstance(String param1, String param2) {
//        PlusOneFragment fragment = new PlusOneFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }


    public BannerAdvertiseFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_banner_advertise, container, false);
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
        imgBanner = view.findViewById(R.id.imgImage);
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

            if (imagePicker.picked() == null) {
                toast("برای این نوع تبلیغ ابتدا باید یک بنر انتخاب کنید.");
                return;
            }
            advertiser.requestBannerAdvertise(imagePicker.picked(), selectedPricing, txtDescription.getText().toString());

        });

        imgBanner.setOnClickListener((v) -> imagePicker.pick(imgBanner));
    }

    @Override
    protected void setFonts() {
        applyTextFont(
                inputLayoutDescription,
                txtDescription,
                btnRequest
        );
    }


    public interface ImagePicker {
        void pick(AppCompatImageView into);

        File picked();
    }
}
