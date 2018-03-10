package com.sorinaidea.arayeshgah.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.layout.ActivationFragment;
import com.sorinaidea.arayeshgah.layout.CreditFragment;
import com.sorinaidea.arayeshgah.layout.GetGiftFragment;
import com.sorinaidea.arayeshgah.layout.LoginFragment;
import com.sorinaidea.arayeshgah.layout.PersonalInfoFragment;

/**
 * Created by mr-code on 2/10/2018.
 */

public class PaymentCallBack extends SorinaActivity
        implements LoginFragment.OnFragmentInteractionListener,
        ActivationFragment.OnActivationFragmentInteractionListener,
        PersonalInfoFragment.OnPersonalInfoFragmentInteractionListener ,
        CreditFragment.OnCreditFragmentInteractionListener ,
        GetGiftFragment.OnGetGiftFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, new GetGiftFragment());
        ft.commit();

    }

    @Override
    public void onActivationFragmentInteraction(Uri uri) {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onPersonalInfoFragmentInteraction(Uri uri) {
    }

    @Override
    public void onGetGiftFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCreditFragmentInteraction(Uri uri) {

    }
}
