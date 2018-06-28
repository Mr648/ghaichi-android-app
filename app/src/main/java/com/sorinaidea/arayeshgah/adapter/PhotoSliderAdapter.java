package com.sorinaidea.arayeshgah.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sorinaidea.arayeshgah.R;

import java.util.ArrayList;

/**
 * Created by mr-code on 5/28/2018.
 */

public class PhotoSliderAdapter extends PagerAdapter {
    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;


    public PhotoSliderAdapter(Context context, ArrayList<String> images ) {
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
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        myImage.setImageResource(R.drawable.preview_xlarge);
        if (imageOnCLickListener != null) {
            myImage.setOnClickListener(imageOnCLickListener);
        }


//        try {
//            Picasso.with(context).load(API.BASE_URL
//                    + URLDecoder.decode(images.get(position), "UTF-8")).into(myImage);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

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
