package com.sorinaidea.ghaichi.ui.barbershop.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.ItemOffsetDecoration;
import com.sorinaidea.ghaichi.adapter.barbershop.BarbershopAllReservationsAdabper;
import com.sorinaidea.ghaichi.adapter.barbershop.BarbershopReservationsAdabper;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.Util;


public class ReservationsFragment extends Fragment {


    private Typeface fontIranSans;
    private  RecyclerView recReservations;

    private int selectedNumberOfViews = Integer.MIN_VALUE;

    private void setupInputs(View view) {
        recReservations = view.findViewById(R.id.recReservations);
        recReservations.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recReservations.addItemDecoration(new ItemOffsetDecoration(Util.dp(8, getActivity())));
        recReservations.setAdapter(new BarbershopAllReservationsAdabper(getActivity()));
        recReservations.setNestedScrollingEnabled(false);
    }

    private void setFonts() {
        fontIranSans = FontManager.getTypeface(getContext(), FontManager.IRANSANS_TEXTS);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_banner_advertise, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        setupInputs(view);
        setFonts();
    }
}
