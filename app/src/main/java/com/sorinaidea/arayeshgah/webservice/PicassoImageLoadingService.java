//package com.sorinaidea.arayeshgah.webservice;
//
//import android.content.Context;
//import android.widget.ImageView;
//
//import com.squareup.picasso.Picasso;
//
///**
// * Created by mr-code on 4/10/2018.
// */
//
//public class PicassoImageLoadingService implements ImageLoadingService {
//    public Context context;
//
//    public PicassoImageLoadingService(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public void loadImage(String url, ImageView imageView) {
    //        Picasso.with(context).load(url).into(imageView);
//    }
//
//    @Override
//    public void loadImage(int resource, ImageView imageView) {
//        Picasso.with(context).load(resource).into(imageView);
//    }
//
//    @Override
//    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
//        Picasso.with(context).load(url).placeholder(placeHolder).error(errorDrawable).into(imageView);
//    }
//}