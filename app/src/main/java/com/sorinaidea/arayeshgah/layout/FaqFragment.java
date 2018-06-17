package com.sorinaidea.arayeshgah.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.FAQAdabper;
import com.sorinaidea.arayeshgah.model.FAQ;
import com.sorinaidea.arayeshgah.ui.SendSupportTicketActivity;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.Util;

import java.util.ArrayList;


public class FaqFragment extends Fragment {

    public FaqFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }


    private RecyclerView recFaq;
    private FloatingActionButton fabSupport;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        recFaq = (RecyclerView) view.findViewById(R.id.recFaq);
        fabSupport = (FloatingActionButton) view.findViewById(R.id.fabSupport);
        fabSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SendSupportTicketActivity.class);
                getActivity().startActivity(intent);
            }
        });
        recFaq.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recFaq.setAdapter(new FAQAdabper(initDataset(), getContext()));

    }

    private ArrayList<FAQ> initDataset() {
        ArrayList<FAQ> mDataset = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mDataset.add(new FAQ("یک جواب نمونه، این یک نمونه است", "سوال نمونه، سوال نمونه"));
        }
        return mDataset;
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
