package com.sorinaidea.ghaichi.ui.barbershop.fragment;

import android.support.annotation.NonNull;
import android.view.View;

import com.sorinaidea.ghaichi.models.Pricing;
import com.sorinaidea.ghaichi.ui.BaseFragment;

import java.io.File;

public abstract class AdvertiseFragment extends BaseFragment {

    protected View.OnClickListener pricingClickListener;
    protected Advertiser advertiser;


    public void setPricingClickListener(View.OnClickListener pricingClickListener) {
        this.pricingClickListener = pricingClickListener;
    }

    public interface Advertiser {
        void requestBannerAdvertise(@NonNull File banner, @NonNull Pricing price, @NonNull String description);

        void requestSpecialAdvertise(@NonNull Pricing price, @NonNull String description);
    }
}
