package com.sorinaidea.arayeshgah.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.TransactionAdabper;
import com.sorinaidea.arayeshgah.layout.ActivationFragment;
import com.sorinaidea.arayeshgah.layout.CreditFragment;
import com.sorinaidea.arayeshgah.layout.GetGiftFragment;
import com.sorinaidea.arayeshgah.layout.LoginFragment;
import com.sorinaidea.arayeshgah.layout.PersonalInfoFragment;
import com.sorinaidea.arayeshgah.layout.ReservationFragment;
import com.sorinaidea.arayeshgah.model.Transaction;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mr-code on 2/10/2018.
 */

public class PaymentCallBack extends SorinaActivity
        implements LoginFragment.OnFragmentInteractionListener,
        ActivationFragment.OnActivationFragmentInteractionListener,
        PersonalInfoFragment.OnPersonalInfoFragmentInteractionListener,
        CreditFragment.OnCreditFragmentInteractionListener,
        ReservationFragment.OnReservationFragmentInteractionListener,
        GetGiftFragment.OnGetGiftFragmentInteractionListener {

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        new DrawerBuilder().withActivity(this).build();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, new CreditFragment());
        ft.commit();

    }
    private RecyclerView recFaq;

    CoordinatorLayout mCoordinatorLayout;
    private ArrayList<Transaction> initDataset() {
        ArrayList<Transaction> mDataset = new ArrayList<>();
        Date date = new Date();
        for (int i = 0; i < 20; i++) {
            mDataset.add(new Transaction(((i % 4 == 0) ? (-1 * 100 * i) : (i * 1000)), "آرایشگاه تست " + i, "دوشنبه 30 بهمن 96"));
        }
        return mDataset;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.testss);
//        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
//        recFaq = (RecyclerView) findViewById(R.id.recTransactions);
//        recFaq.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//
//        recFaq.setAdapter(new TransactionAdabper(initDataset(), getApplicationContext()));
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        if (fab != null) {
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Snackbar.make(mCoordinatorLayout, "I'm a Snackbar :D", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(PaymentCallBack.this, "Snackbar Action", Toast.LENGTH_LONG).show();
//                        }
//                    }).show();
//                }
//            });
//        }
//    }

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

    @Override
    public void onReservationFragmentInteraction(Uri uri) {

    }
}
