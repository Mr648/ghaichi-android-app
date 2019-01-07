package com.sorinaidea.ghaichi.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;

public class SpinnerAdapter extends ArrayAdapter<String> {


    private LayoutInflater flater;
    private Typeface fontIranSans;

    public SpinnerAdapter(Activity context, int resouceId, int textviewId, String... list) {

        super(context, resouceId, textviewId, list);
        flater = context.getLayoutInflater();
        fontIranSans = FontManager.getTypeface(context, FontManager.IRANSANS_TEXTS);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String item = getItem(position);

        View view = flater.inflate(R.layout.item_spinner, parent, false);

        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtTitle.setText(item);


        FontManager.setFont(txtTitle, fontIranSans);
        return view;
    }
}
