package com.sorinaidea.arayeshgah.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.model.FAQ;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.ArrayList;

/**
 * Created by mr-code on 3/10/2018.
 */

public class FAQAdabper extends RecyclerView.Adapter<FAQAdabper.ViewHolder> {
    private static final String TAG = "FAQAdabper";

    private ArrayList<FAQ> mDataSet;
    private Context mContext;
    private Typeface fontMaterialIcons;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtIcon;
        private final TextView txtQuestion;
        private final TextView txtAnswer;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            txtIcon = (TextView) v.findViewById(R.id.txtIcon);
            txtQuestion = (TextView) v.findViewById(R.id.txtQuestion);
            txtAnswer = (TextView) v.findViewById(R.id.txtAnswer);
        }

        public TextView getTxtIcon() {
            return txtIcon;
        }

        public TextView getTxtAnswer() {
            return txtAnswer;
        }

        public TextView getTxtQuestion() {
            return txtQuestion;
        }
    }


    public FAQAdabper(ArrayList<FAQ> faqs, Context context) {
        mDataSet = faqs;
        mContext = context;
        fontMaterialIcons = FontManager.getTypeface(mContext, FontManager.MATERIAL_ICONS);

        // setting fonts for icons
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.faq_item, viewGroup, false);

        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        FontManager.setFont( viewHolder.getTxtIcon(), fontMaterialIcons);

        viewHolder.getTxtAnswer().setText(mDataSet.get(position).getQuestion());
        viewHolder.getTxtQuestion().setText(mDataSet.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
