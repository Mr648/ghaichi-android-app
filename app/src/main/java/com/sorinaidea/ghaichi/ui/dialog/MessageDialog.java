package com.sorinaidea.ghaichi.ui.dialog;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/**
 * Created by mr-code on 6/14/2018.
 */

public class MessageDialog extends Dialog {

    private AppCompatActivity activity;
    private String TAG = "MessageDialog";
    private Typeface fontIranSans;
    private String message;

    private View.OnClickListener positiveButtonListener;

    public MessageDialog(AppCompatActivity activity) {
        this(activity, "", null);
    }

    public MessageDialog(AppCompatActivity activity, String message, View.OnClickListener listener) {
        super(activity);
        this.message = message;
        this.activity = activity;
        this.positiveButtonListener = listener;
        this.fontIranSans = FontManager.getTypeface(activity, FontManager.IRANSANS_TEXTS);
    }


    private AppCompatButton btnOk;
    private AppCompatTextView txtMessage;
    private AppCompatImageView imgIcon;

    private String textMessage;
    private int imgIconResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.message_dialog);
        btnOk = findViewById(R.id.btnOk);

        if (positiveButtonListener != null) {
            btnOk.setOnClickListener(positiveButtonListener);
        }

        txtMessage = findViewById(R.id.txtMessage);
        imgIcon = findViewById(R.id.imgIcon);
        txtMessage.setText(this.message);
        FontManager.setFont(btnOk, fontIranSans);
        FontManager.setFont(txtMessage, fontIranSans);
    }


}
