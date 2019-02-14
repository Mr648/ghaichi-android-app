package com.sorinaidea.ghaichi.auth;

import android.content.Context;

import com.sorinaidea.ghaichi.util.GhaichiPrefrenceManager;
import com.sorinaidea.ghaichi.util.Util;

public class Auth {

    public static String getAccessKey(Context context) {

        String accessKey = GhaichiPrefrenceManager.getDecryptedString(context,
                 Util.PREFRENCES_KEYS.USER_ACCESS_KEY , null);

        return accessKey;
    }
}
