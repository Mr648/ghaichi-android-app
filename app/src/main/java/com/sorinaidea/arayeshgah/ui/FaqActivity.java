package com.sorinaidea.arayeshgah.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.FAQAdabper;
import com.sorinaidea.arayeshgah.layout.PersonalInfoFragment;
import com.sorinaidea.arayeshgah.model.FAQ;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class FaqActivity extends SorinaActivity {

    private RecyclerView recFaq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        recFaq = (RecyclerView) findViewById(R.id.recFaq);
        recFaq.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recFaq.setAdapter(new FAQAdabper(initDataset(), getApplicationContext()));


    }

    private ArrayList<FAQ> initDataset() {
        ArrayList<FAQ> mDataset = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mDataset.add(new FAQ("یک جواب نمونه، این یک نمونه است", "سوال نمونه، سوال نمونه"));
        }
        return mDataset;
    }
}
