package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.FAQ;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.List;

/**
 * Created by mr-code on 3/10/2018.
 */

public class FAQAdabper extends RecyclerView.Adapter<FAQAdabper.ViewHolder> {
    private static final String TAG = "FAQAdabper";

    private List<FAQ> mDataSet;
    private Context mContext;
    private Typeface fontIransans;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout lnrQA;
        private final LinearLayout lnrFAQ;
        private final AppCompatImageView imgDrop;
        private final AppCompatTextView txtTitle;
        private final AppCompatTextView txtQuestion;
        private final AppCompatTextView txtAnswer;

        public ViewHolder(View v) {
            super(v);
            lnrQA = v.findViewById(R.id.lnrQA);
            lnrFAQ = v.findViewById(R.id.lnrFAQ);
            imgDrop = v.findViewById(R.id.imgDrop);
            txtTitle = v.findViewById(R.id.txtTitle);
            txtQuestion = v.findViewById(R.id.txtQuestion);
            txtAnswer = v.findViewById(R.id.txtAnswer);
        }


        public TextView getTxtTitle() {
            return txtTitle;
        }

        public TextView getTxtAnswer() {
            return txtAnswer;
        }

        public TextView getTxtQuestion() {
            return txtQuestion;
        }

        public AppCompatImageView getImgDrop() {
            return imgDrop;
        }

        public LinearLayout getLnrQA() {
            return lnrQA;
        }

        public LinearLayout getLnrFAQ() {
            return lnrFAQ;
        }
    }


    public FAQAdabper(List<FAQ> faqs, Context context) {


        mDataSet =faqs;

        mContext = context;
        fontIransans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.faq_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.getTxtQuestion().setText(mDataSet.get(position).getQuestion());
        viewHolder.getTxtAnswer().setText(mDataSet.get(position).getAnswer());
        viewHolder.getTxtTitle().setText(mDataSet.get(position).getTitle());

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDataSet.get(position).isHide()) {
                    viewHolder.getImgDrop().animate().setDuration(150).alpha(0.0f).start();
                    viewHolder.getImgDrop().setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                    viewHolder.getImgDrop().animate().setDuration(150).alpha(1.0f).start();
                    viewHolder.getLnrQA().setVisibility(View.VISIBLE);
                    viewHolder.getLnrQA().animate().setDuration(1000).alpha(1.0f).start();
                    mDataSet.get(position).setHide(false);
                } else {
                    viewHolder.getImgDrop().animate().setDuration(150).alpha(0.0f).start();
                    viewHolder.getImgDrop().setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
                    viewHolder.getImgDrop().animate().setDuration(150).alpha(1.0f).start();
                    viewHolder.getLnrQA().setVisibility(View.GONE);
                    viewHolder.getLnrQA().animate().alpha(0.0f).setDuration(0).start();
                    mDataSet.get(position).setHide(true);
                }

            }
        };
        viewHolder.getImgDrop().setOnClickListener(listener);
        viewHolder.getLnrFAQ().setOnClickListener(listener);

        FontManager.setFont(viewHolder.getTxtTitle(), fontIransans);
        FontManager.setFont(viewHolder.getTxtAnswer(), fontIransans);
        FontManager.setFont(viewHolder.getTxtQuestion(), fontIransans);

    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
