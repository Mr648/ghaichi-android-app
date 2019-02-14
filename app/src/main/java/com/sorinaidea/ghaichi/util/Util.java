package com.sorinaidea.ghaichi.util;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.sorinaidea.ghaichi.model.Advertise;
import com.sorinaidea.ghaichi.ui.CategoriesActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mr-code on 1/30/2018.
 */

public class Util {

    public static final class COMMUNICATION_KEYS {

        public static final String BARBERSHOP_ID = "BARBERSHOP_ID";
        public static final String SERVICE_ID = "SERVICE_ID";
    }

    public static final class PREFRENCES_KEYS {
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
        public static final String ROLE_BARBERSHOP = "application_barbershop";
        public static final String ROLE_NORMAL_USER = "application_user";


//                        = "^(0|\\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}$";

        public static final String REGEX_PHONE
                = "^(0|\\+98)?([ ]|,|-|[()]){0,2}9[0|1|2|3|4|5]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}$";
        public static final String REGEX_VERIFICATIONCODE
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
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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

    public static int getSuitableColor(Advertise ad) {
        int percentage = (int) ((1.0f * ad.getViews() / ad.getAmount()) * 100);
        Log.i("PERCENTAGE", "" + percentage);
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


    public static String base64encode(String str, int count) {
        String output = "";
        for (int i = 0; i < count; i++) {
            output = new String(Base64.encode(str.getBytes(), Base64.URL_SAFE));
        }
        return output;
    }

    public static String base64decode(String str, int count) {
        String output = "";

        for (int i = 0; i < count; i++) {
            output = new String(Base64.decode(str.getBytes(), Base64.URL_SAFE));
        }

        return output;
    }

    public static String md5(String input) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            return bytesToHex(md5.digest(input.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            return input;
        }
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getAccessKey(Context context) {
        String authToken = GhaichiPrefrenceManager.getDecryptedString(context, Util.PREFRENCES_KEYS.USER_ACCESS_KEY,null);
        return authToken;
    }

}
