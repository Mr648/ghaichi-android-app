package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.webservice.API;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by mr-code on 5/28/2018.
 */

public class ImageSliderAdapter extends PagerAdapter {

    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;
    private static final String TAG = ImageSliderAdapter.class.getSimpleName();


    public ImageSliderAdapter(Context context, ArrayList<String> images) {
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

    private String getUrl(int position) {
        try {
            return API.BASE_URL + URLDecoder.decode(images.get(position), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "Unsupported Encoding Exception");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            Log.d(TAG, "Index Out Of Bounds Exception");
            e.printStackTrace();
        }
        return null;
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


        API.getPicasso(context).load(getUrl(position)).centerCrop().fit().into(myImage, new Callback() {
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
