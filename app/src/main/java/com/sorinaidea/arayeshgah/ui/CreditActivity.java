package com.sorinaidea.arayeshgah.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.TransactionAdabper;
import com.sorinaidea.arayeshgah.model.Transaction;
import com.sorinaidea.arayeshgah.ui.dialog.AddCreditDialog;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;
import java.util.Date;

public class CreditActivity extends AppCompatActivity {

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


    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("اعتبار");

        fontMaterialIcons = FontManager.getTypeface(getApplicationContext(), FontManager.MATERIAL_ICONS);
        fontIranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        arcView = (DecoView) findViewById(R.id.dynamicArcView);

        recFaq = (RecyclerView) findViewById(R.id.recTransactions);
        recFaq.setNestedScrollingEnabled(false);


        txtRemainingLabel = (TextView) findViewById(R.id.txtRemainingLabel);
        txtReservationLabel = (TextView) findViewById(R.id.txtReservationLabel);
        txtRemaining = (TextView) findViewById(R.id.txtRemaining);
        txtReservation = (TextView) findViewById(R.id.txtReservations);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        txtMessage = (TextView) findViewById(R.id.txtMessage);
        txtCredit = (TextView) findViewById(R.id.txtCredit);

//        btnAddCash = (Button) view.findViewById(R.id.btnAddCash);

        recFaq.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        recFaq.setAdapter(new TransactionAdabper(initDataset(), getApplicationContext()));

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
                AddCreditDialog dialog = new AddCreditDialog(CreditActivity.this);
                dialog.show();
            }
        });

        FontManager.setFont(txtRemainingLabel, fontIranSans);
        FontManager.setFont(txtReservationLabel, fontIranSans);
        FontManager.setFont(txtRemaining, fontIranSans);
        FontManager.setFont(txtReservation, fontIranSans);
        FontManager.setFont(txtMessage, fontIranSans);
        FontManager.setFont(txtCredit, fontIranSans);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    private ArrayList<Transaction> initDataset() {
        ArrayList<Transaction> mDataset = new ArrayList<>();
        Date date = new Date();
        for (int i = 0; i < 20; i++) {
            mDataset.add(new Transaction(((i % 4 == 0) ? (-1 * 100 * i) : (i * 1000)), "آرایشگاه تست " + i, "دوشنبه 30 بهمن 96"));
        }
        return mDataset;
    }


}
