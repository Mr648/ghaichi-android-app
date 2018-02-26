package com.sorinaidea.arayeshgah;

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

//        ImageView imgInstagram = (ImageView) findViewById(R.id.imgInstagram);
//        ImageView imgTwitter = (ImageView) findViewById(R.id.imgTwitter);
//        ImageView imgLinkedIn = (ImageView) findViewById(R.id.imgLinkedIn);
        final TextView txtInstagram = (TextView) findViewById(R.id.txtInstagram);
        final TextView txtTwitter = (TextView) findViewById(R.id.txtTwitter);
        final TextView txtLinkedIn = (TextView) findViewById(R.id.txtLinkedIn);

        txtInstagram.setOnTouchListener(new View.OnTouchListener() {

            private int clickCounter = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (clickCounter % 2 == 0) {
                        txtInstagram.setTextColor(getResources().getColor(R.color.ic_instagram));
                        txtInstagram.setBackgroundColor(Color.WHITE);
                    } else {
                        txtInstagram.setTextColor(Color.WHITE);
                        txtInstagram.setBackgroundColor(getResources().getColor(R.color.ic_instagram));
                    }
                    clickCounter++;
                }
                return false;
            }
        });
//        txtTwitter.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    txtInstagram.setTextColor(getResources().getColor(R.color.ic_instagram));
//                    txtInstagram.setBackgroundColor(Color.WHITE);
//                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    txtInstagram.setTextColor(Color.WHITE);
//                    txtInstagram.setBackgroundColor(getResources().getColor(R.color.ic_instagram));
//                }
//                return false;
//            }
//        });
//        txtInstagram.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    txtInstagram.setTextColor(getResources().getColor(R.color.ic_instagram));
//                    txtInstagram.setBackgroundColor(Color.WHITE);
//                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    txtInstagram.setTextColor(Color.WHITE);
//                    txtInstagram.setBackgroundColor(getResources().getColor(R.color.ic_instagram));
//                }
//                return false;
//            }
//        });
        Typeface typeface = FontManager.getTypeface(getApplicationContext(), FontManager.SOCIAL_ICONS);

        FontManager.markAsIconContainer(txtInstagram, typeface);
        FontManager.markAsIconContainer(txtTwitter, typeface);
        FontManager.markAsIconContainer(txtLinkedIn, typeface);


        String text;
        text = "<html><body dir=\"rtl\"; style=\"text-align:justify;\">";
        text += getResources().getString(R.string.about_us);
        text += "</body></html>";

        ((WebView) findViewById(R.id.webView)).loadData(text, "text/html; charset=UTF-8", "utf-8");
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.placeholder, new ActivationFragment());
////        ft.replace(R.id.placeholder, new PersonalInfoFragment());
//        ft.commit();

    }

    @Override
    public void onActivationFragmentInteraction(Uri uri) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(uri);
//        startActivity(Intent.createChooser(intent, "Select an application"));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(uri);
//        startActivity(Intent.createChooser(intent, "Select an application"));
    }

    @Override
    public void onPersonalInfoFragmentInteraction(Uri uri) {

    }
}
