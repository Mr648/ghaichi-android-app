package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.layout.ActivationFragment;
import com.sorinaidea.arayeshgah.layout.LoginFragment;
import com.sorinaidea.arayeshgah.layout.PersonalInfoFragment;
import com.sorinaidea.arayeshgah.ui.SorinaActivity;
import com.sorinaidea.arayeshgah.util.FontManager;

/**
 * Created by mr-code on 2/10/2018.
 */

public class PaymentCallBack extends SorinaActivity
        implements LoginFragment.OnFragmentInteractionListener,
        ActivationFragment.OnActivationFragmentInteractionListener,
        PersonalInfoFragment.OnPersonalInfoFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, new ActivationFragment());
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
}
