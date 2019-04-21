package com.sorinaidea.ghaichi.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import com.sorinaidea.ghaichi.ui.LoginActivity;
import com.sorinaidea.ghaichi.util.GhaichiPreferenceManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.model.responses.VerificationResponse;

import java.util.Locale;
import java.util.Objects;

public class Auth {

    public static String getAccessKey(Context context) {
        String accessKey = GhaichiPreferenceManager.getDecryptedString(context,
                Util.KEYS.USER_ACCESS_KEY, null);
        try {
            return String.format(Locale.getDefault(), "%s %s", "Bearer", Objects.requireNonNull(accessKey));
        } catch (NullPointerException ignored) {
            return null;
        }
    }

    public static void loggedIn(Context context, VerificationResponse response) {
        GhaichiPreferenceManager.putEncryptedString(context,
                Util.KEYS.USER_ACCESS_KEY,
                response.getAccessKey()
        );

        GhaichiPreferenceManager.putEncryptedString(context,
                Util.KEYS.USER_ROLE,
                new String(Base64.decode(response.getUserRole().getBytes(), Base64.DEFAULT))
        );

        GhaichiPreferenceManager.putEncryptedString(context,
                Util.KEYS.KEY_EXPIRATION,
                response.getExpiration()
        );
    }

    private static boolean hasRole(Context context, @NonNull final String role) {

        String userRole = GhaichiPreferenceManager.getDecryptedString(context,
                Util.KEYS.USER_ROLE, null);

        try {
            return Objects.requireNonNull(userRole).equals(role);
        } catch (NullPointerException ignored) {
            logout(context);
            return false;
        }
    }

    public static boolean isUser(Context context) {
        return hasRole(context, Util.CONSTANTS.ROLE_USER);
    }

    public static boolean isBarbershop(Context context) {
        return hasRole(context, Util.CONSTANTS.ROLE_BARBERSHOP);
    }

    public static void logout(Context context) {
        GhaichiPreferenceManager.clear(context);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).finish();
        } else if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
}
