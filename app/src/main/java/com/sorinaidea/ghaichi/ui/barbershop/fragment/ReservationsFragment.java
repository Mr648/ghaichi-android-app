package com.sorinaidea.ghaichi.ui.barbershop.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.adapter.barbershop.BarbershopAllReservationsAdabper;
import com.sorinaidea.ghaichi.ui.BaseFragment;
import com.sorinaidea.ghaichi.util.Util;


public class ReservationsFragment extends BaseFragment {


    private  RecyclerView recReservations;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_banner_advertise, container, false);
    }

    @Override protected void setup(View view) {
        recReservations = view.findViewById(R.id.recReservations);
        recReservations.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recReservations.addItemDecoration(new ItemOffsetDecoration(Util.dp(8, getActivity())));
        recReservations.setAdapter(new BarbershopAllReservationsAdabper(getActivity()));
        recReservations.setNestedScrollingEnabled(false);
    }

    @Override protected void setFonts() {
//        applyTextFont();
    }

}
