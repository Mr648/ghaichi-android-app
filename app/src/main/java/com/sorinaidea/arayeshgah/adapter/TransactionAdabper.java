package com.sorinaidea.arayeshgah.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.FAQ;
import com.sorinaidea.arayeshgah.model.Transaction;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class TransactionAdabper extends RecyclerView.Adapter<TransactionAdabper.ViewHolder> {
    private static final String TAG = "FAQAdabper";

    private ArrayList<Transaction> mDataSet;
    private Context mContext;
    private Typeface fontMaterialIcons;
    private Typeface fontIranSans;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtIcon;
        private final TextView txtTitle;
        private final TextView txtDate;
        private final TextView txtCashValue;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });

            txtIcon = (TextView) v.findViewById(R.id.txtIcon);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
            txtCashValue = (TextView) v.findViewById(R.id.txtCashValue);
        }

        public TextView getTxtIcon() {
            return txtIcon;
        }

        public TextView getTxtCashValue() {
            return txtCashValue;
        }

        public TextView getTxtDate() {
            return txtDate;
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }
    }


    public TransactionAdabper(ArrayList<Transaction> transactions, Context context) {
        mDataSet = transactions;
        mContext = context;
        fontMaterialIcons = FontManager.getTypeface(mContext, FontManager.MATERIAL_ICONS);
        fontIranSans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);

        // setting fonts for icons
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.transaction_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        FontManager.setFont(viewHolder.getTxtIcon(), fontMaterialIcons);

        viewHolder.getTxtTitle().setText(mDataSet.get(position).getTitle());
        viewHolder.getTxtDate().setText(mDataSet.get(position).getDate());
        viewHolder.getTxtCashValue().setText(String.valueOf(Math.abs(mDataSet.get(position).getCashValue())) + " " + "ریال");

        int cash = mDataSet.get(position).getCashValue();

        viewHolder.getTxtIcon().setText(
                (cash < 0) // condition
                        ? mContext.getString(R.string.m_icon_credit_down)
                        : mContext.getString(R.string.m_icon_credit_up)
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            viewHolder.getTxtIcon().setTextColor(
                    (cash < 0) // condition
                            ? mContext.getColor(R.color.credit_reservations)
                            : mContext.getColor(R.color.credit_remaining)
            );
            viewHolder.getTxtCashValue().setTextColor(
                    (cash < 0) // condition
                            ? mContext.getColor(R.color.credit_reservations)
                            : mContext.getColor(R.color.credit_remaining));
        } else {
            viewHolder.getTxtIcon().setTextColor(
                    (cash < 0) // condition
                            ? mContext.getResources().getColor(R.color.credit_reservations)
                            : mContext.getResources().getColor(R.color.credit_remaining)
            );
            viewHolder.getTxtCashValue().setTextColor(
                    (cash < 0) // condition
                            ? mContext.getResources().getColor(R.color.credit_reservations)
                            : mContext.getResources().getColor(R.color.credit_remaining)
            );
        }


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}