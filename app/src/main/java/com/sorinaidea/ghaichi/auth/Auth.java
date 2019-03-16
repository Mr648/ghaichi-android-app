package com.sorinaidea.ghaichi.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.sorinaidea.ghaichi.ui.LoginActivity;
import com.sorinaidea.ghaichi.util.GhaichiPrefrenceManager;
import com.sorinaidea.ghaichi.util.Security;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.model.responses.VerificationResponse;

public class Auth {

    public static String getAccessKey(Context context) {

        String accessKey = GhaichiPrefrenceManager.getDecryptedString(context,
                Util.PREFRENCES_KEYS.USER_ACCESS_KEY, null);

        if (accessKey == null) {


            // Remove Keys

            GhaichiPrefrenceManager.removeKeys(
                    context
                    , Security.encrypt(Util.PREFRENCES_KEYS.USER_ACCESS_KEY, context)
                    , Security.encrypt(Util.PREFRENCES_KEYS.USER_ROLE, context)
            );


            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            if (context instanceof AppCompatActivity) {
                ((AppCompatActivity) context).finish();
            } else if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        }

        return accessKey;
    }

    public static void loggedIn(Context context,VerificationResponse response){
        GhaichiPrefrenceManager.putEncryptedString(context,
                Util.PREFRENCES_KEYS.USER_ACCESS_KEY,
                response.getAccessKey()
        );

        GhaichiPrefrenceManager.putEncryptedString(context,
                Util.PREFRENCES_KEYS.USER_ROLE,
                response.getUserRole()
        );

        GhaichiPrefrenceManager.putEncryptedString(context,
                Util.PREFRENCES_KEYS.KEY_EXPIRATION,
                response.getExpiration()
        );
    }

}
