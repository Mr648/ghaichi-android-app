package com.sorinaidea.ghaichi.util;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.sorinaidea.ghaichi.models.BaseAdvertise;

import java.util.Locale;
import java.util.Objects;

/**
 * Created by mr-code on 1/30/2018.
 */

public class Util {

    public static final class COMMUNICATION_KEYS {
        public static final String ADVERTISE_ID = "ADVERTISE_ID";
        public static final String BARBERSHOP_ID = "BARBERSHOP_ID";
        public static final String SERVICE_ID = "SERVICE_ID";
    }

    public static final class KEYS {
        public static final int BASE_64_ENCODE_DECODE_COUNT = 5;
        public static final int BASE_64_ENCODE_DECODE_KEYS_COUNT = 0x2;
        public static final String USER_ROLE = "user_role";
        public static final String USER_ACCESS_KEY = "access_key";
        public static final String KEY_EXPIRATION = "expiration";
    }


    public static final class CONSTANTS {


        public static final String SMS_SENDER = "+983000572070";
        public static final String TAG = "GHAICHI_APPLICATION";
        public static final String NO_INTERNET_CONNECTION = "خطا در اتصال به اینترنت!";
        public static final String BASE_URL = "http://ghaichi.com";
        public static final String ROLE_BARBERSHOP = "barbershop";
        public static final String ROLE_USER = "user";
        public static final String REGEX_JALALI_DATE = "^[0-9]{4}\\/[0-9]{2}\\/[0-9]{2}$";
        public static final String REGEX_TIME = "^[0-9]{2}\\:[0-9]{2}$";
        public static final String REGEX_PHONE
                = "^(0|\\+98)?([ ]|,|-|[()]){0,2}9[0|1|2|3|4|5]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}$";

        public static final String REGEX_VERIFICATION_CODE
                = "[0-9]{5}";


        public static String SHARE_GIFT_CODE_MESSAGE = "" +
                "کد زیر را برای دوستان خود بفرستید تا امتیاز بگیرید." +
                "\n\n" +
                "";
    }

    public static BitmapDescriptor getBitmapDescriptor(int id, Context context) {

        // TODO change icon here
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            VectorDrawable vectorDrawable = (VectorDrawable) context.getDrawable(id);
//
//            int h = vectorDrawable.getIntrinsicHeight();
//            int w = vectorDrawable.getIntrinsicWidth();
//
//            vectorDrawable.setBounds(0, 0, w, h);
//            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//
//            Canvas canvas = new Canvas(bm);
//            vectorDrawable.draw(canvas);

//            return BitmapDescriptorFactory.fromBitmap(bm);
            return BitmapDescriptorFactory.fromResource(id);
        } else {

            return BitmapDescriptorFactory.fromResource(id);
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            return Objects.requireNonNull(connectivityManager)
                    .getActiveNetworkInfo()
                    .isConnectedOrConnecting();
        } catch (NullPointerException ignore) {
            return false;
        }
    }


    public static String imageUrl(String path, ImageView imageView) {
        return imageUrl(
                path,
                imageView.getMeasuredWidth(),
                imageView.getMeasuredHeight()
        );
    }

    public static String imageUrl(String path, int width, int height) {
        return String.format(
                Locale.ENGLISH,
                "https://ghaichi.com/api/download?width=%d&height=%d&path=%s",
                width,
                height,
                Security.base64encode(path)
        );
    }


    public static int dp(float value, Context context) {
        if (value == 0) {
            return 0;
        }
        float density = context.getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * value);
    }


    private static int colors[] = {
            /* This Colors are comming from Semantic UI CSS Frameword!*/
            Color.argb(255, 219, 40, 40), // RED
            Color.argb(255, 242, 113, 28), // ORANGE
            Color.argb(255, 251, 189, 8), // YELLOW
            Color.argb(255, 181, 204, 24), // OLIVE
            Color.argb(255, 33, 186, 69), // GREEN
            Color.argb(255, 0, 181, 173) // TEAl
    };

    private enum Colors {
        RED,
        ORANGE,
        YELLOW,
        OLIVE,
        GREEN,
        TEAL
    }

    public static int getSuitableColor(BaseAdvertise ad) {
        int percentage = (int) ((1.0f * ad.getViews() / ad.getTotal()) * 100);
        if (percentage <= 20) {
            return colors[Colors.RED.ordinal()];
        } else if (percentage > 20 && percentage <= 50) {
            return colors[Colors.ORANGE.ordinal()];
        } else if (percentage > 50 && percentage <= 75) {
            return colors[Colors.YELLOW.ordinal()];
        } else if (percentage > 75 && percentage <= 99) {
            return colors[Colors.OLIVE.ordinal()];
        } else if (percentage == 100) {
            return colors[Colors.GREEN.ordinal()];
        } else {
            return colors[Colors.TEAL.ordinal()];
        }
    }
}
