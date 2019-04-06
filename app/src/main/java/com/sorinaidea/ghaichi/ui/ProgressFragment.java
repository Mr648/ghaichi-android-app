package com.sorinaidea.ghaichi.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;


public class ProgressFragment extends BaseFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public ProgressFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_progress, container, false);
    }

    TextView txt;

    @Override
    protected void setup(View view) {
        txt = view.findViewById(R.id.txt);
    }

    @Override
    protected void setFonts() {
        applyTextFont(txt);
    }
}
