package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.webservice.API;
import com.squareup.picasso.Callback;

import java.util.List;

/**
 * Created by mr-code on 5/28/2018.
 */

public class BannerAdapter extends PagerAdapter {

    private List<String> images;
    private LayoutInflater inflater;
    private Context context;


    public BannerAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }


    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.image_slide, view, false);

        ImageView myImage = myImageLayout
                .findViewById(R.id.image);

        ProgressBar prgLoad = myImageLayout
                .findViewById(R.id.prgLoad);

        TextView txtMessage = myImageLayout
                .findViewById(R.id.txtMessage);

        if (imageOnCLickListener != null) {
            myImage.setOnClickListener(imageOnCLickListener);
        }


        API.getPicasso(context).load(images.get(position)).centerCrop().fit().into(myImage, new Callback() {
            @Override
            public void onSuccess() {
                prgLoad.setVisibility(View.GONE);
                txtMessage.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                txtMessage.setVisibility(View.VISIBLE);
                txtMessage.setText(context.getResources().getString(R.string._hint_date));
            }
        });


        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    private View.OnClickListener imageOnCLickListener = null;

    public void setImageOnCLickListener(View.OnClickListener imageOnCLickListener) {
        this.imageOnCLickListener = imageOnCLickListener;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
