package com.sorinaidea.ghaichi.adapter.barbershop;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.Barber;
import com.sorinaidea.ghaichi.model.Empty;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class BarberAdabper extends RecyclerView.Adapter<BarberAdabper.ViewHolder> {
    private static final String TAG = "BarberAdabper";


    private ArrayList<Barber> mDataSet;
    private Context mContext;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtBarber;
        private CheckBox chkSelected;
        public ViewHolder(View v) {
            super(v);

            txtBarber = v.findViewById(R.id.txtBarber);
            chkSelected = v.findViewById(R.id.chkSelected);
        }

        public CheckBox getChkSelected() {
            return chkSelected;
        }

        public TextView getTxtBarber() {
            return txtBarber;
        }
    }


    public BarberAdabper(Context context) {
        mDataSet = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Barber b = new Barber();
            b.setId(i);
            b.setSelected(false);
            b.setName("محمد کریمی #" + i);
            mDataSet.add(b);
        }
        mContext = context;
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_barber, viewGroup, false);
            return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Barber b = mDataSet.get(position);
        viewHolder.getTxtBarber().setText(b.getName());
        viewHolder.getChkSelected().setChecked(b.isSelected());

        viewHolder.getChkSelected().setOnCheckedChangeListener((buttonView, isChecked) -> {
            mDataSet.get(position).setSelected(isChecked);
        });
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
