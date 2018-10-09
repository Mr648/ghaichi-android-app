package com.sorinaidea.ghaichi.layout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.TransactionAdabper;
import com.sorinaidea.ghaichi.model.Transaction;
import com.sorinaidea.ghaichi.ui.dialog.AddCreditDialog;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.ArrayList;
import java.util.Date;

public class CreditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreditFragment newInstance(String param1, String param2) {
        CreditFragment fragment = new CreditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_credit, container, false);
    }

    private DecoView arcView;
    private RecyclerView recFaq;

    private TextView txtRemainingLabel;
    private TextView txtReservationLabel;
    private TextView txtRemaining;
    private TextView txtReservation;
    private TextView txtMessage;
    private TextView txtCredit;

    private Button btnAddCash;

    private FloatingActionButton fab;

    private Typeface fontMaterialIcons;
    private Typeface fontIranSans;


    private ArrayList<Transaction> initDataset() {
        ArrayList<Transaction> mDataset = new ArrayList<>();
        Date date = new Date();
        for (int i = 1000, j = 1; i < 1020; i++, j += 4) {
            mDataset.add(new Transaction(((i % 4 == 0) ? (i * 1000) : (-1 * 100 * i)), (i % 4 == 0) ? "تمدید اعتبار" : "رزرو آرایشگاه" + j, " روز ماه سال"));
        }
        return mDataset;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        fontMaterialIcons = FontManager.getTypeface(getContext(), FontManager.MATERIAL_ICONS);
        fontIranSans = FontManager.getTypeface(getContext(), FontManager.IRANSANS_TEXTS);

        arcView = (DecoView) view.findViewById(R.id.dynamicArcView);

        recFaq = (RecyclerView) view.findViewById(R.id.recTransactions);
        recFaq.setNestedScrollingEnabled(false);


        txtRemainingLabel = (TextView) view.findViewById(R.id.txtRemainingLabel);
        txtReservationLabel = (TextView) view.findViewById(R.id.txtReservationLabel);
        txtRemaining = (TextView) view.findViewById(R.id.txtRemaining);
        txtReservation = (TextView) view.findViewById(R.id.txtReservations);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        txtMessage = (TextView) view.findViewById(R.id.txtMessage);
        txtCredit = (TextView) view.findViewById(R.id.txtCredit);

//        btnAddCash = (Button) view.findViewById(R.id.btnAddCash);

        recFaq.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        recFaq.setAdapter(new TransactionAdabper(initDataset(), getContext()));

// Create background track
        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(26f)
                .build());

//Create data series track
        SeriesItem seriesItem1 = new SeriesItem.Builder(getResources().getColor(R.color.credit_remaining))
                .setRange(0, 100, 100)
                .setLineWidth(18f)
                .build();

        SeriesItem seriesItem2 = new SeriesItem.Builder(getResources().getColor(R.color.credit_reservations))
                .setRange(0, 100, 60)
                .setLineWidth(18f)
                .build();


        arcView.addSeries(seriesItem1);
        arcView.addSeries(seriesItem2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCreditDialog dialog = new AddCreditDialog((AppCompatActivity) getActivity());
                dialog.show();
            }
        });

//        btnAddCash.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "btn clicked!", Toast.LENGTH_SHORT).show();
//            }
//        });

//        FontManager.setFont(btnAddCash, fontMaterialIcons);

        FontManager.setFont(txtRemainingLabel, fontIranSans);
        FontManager.setFont(txtReservationLabel, fontIranSans);
        FontManager.setFont(txtRemaining, fontIranSans);
        FontManager.setFont(txtReservation, fontIranSans);
        FontManager.setFont(txtMessage, fontIranSans);
        FontManager.setFont(txtCredit, fontIranSans);


    }

    // TODO: Rename method, add argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
