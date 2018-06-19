package com.sorinaidea.arayeshgah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.datahelper.GenderItem;

import java.util.ArrayList;

/**
 * Created by mr-code on 6/18/2018.
 */

public class GenderSpinnerAdapter extends ArrayAdapter<GenderItem> {
    int layoutId;
    Context context;
    ArrayList<GenderItem> items;
    LayoutInflater inflater;

    public GenderSpinnerAdapter(Context context, int layoutId, int id, ArrayList<GenderItem> items) {
        super(context, id, items);
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(layoutId, parent, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imgIcon);
        imageView.setImageResource(items.get(position).getImgIconResource());
        TextView textView = (TextView) itemView.findViewById(R.id.txtTitle);
        textView.setText(items.get(position).getTitle());
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
