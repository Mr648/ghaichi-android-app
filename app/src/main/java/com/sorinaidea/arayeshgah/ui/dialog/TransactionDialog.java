package com.sorinaidea.arayeshgah.ui.dialog;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.util.FontManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mr-code on 6/14/2018.
 */

public class TransactionDialog extends Dialog {

    private AppCompatActivity activity;
    private String TAG = "TransactionDialog";
    private Typeface fontIranSans;

    public TransactionDialog(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
        this.fontIranSans = FontManager.getTypeface(activity, FontManager.IRANSANS_TEXTS);

    }

    private AppCompatButton btnCancel;
    private AppCompatButton btnOk;
    private AppCompatTextView txtMessage;
    private AppCompatImageView imgIconLeft;
    private AppCompatImageView imgIconRight;

    private String textMessage;
    private int imgIconResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.transaction_dialog);

        btnCancel = (AppCompatButton) findViewById(R.id.btnCancel);
        btnOk = (AppCompatButton) findViewById(R.id.btnOk);

        txtMessage = (AppCompatTextView) findViewById(R.id.txtMessage);
        imgIconLeft = (AppCompatImageView) findViewById(R.id.imgIconLeft);
        imgIconRight = (AppCompatImageView) findViewById(R.id.imgIconRight);

//
//        Animation rotateR = AnimationUtils.loadAnimation(activity, R.anim.rotate_scissor_right);
//
//        Animation rotateL = AnimationUtils.loadAnimation(activity, R.anim.rotate_scissor_left);
//
//
//        Animation.AnimationListener listener = new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                animation.start();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        };
//
//        rotateR.setAnimationListener(listener);
//        rotateL.setAnimationListener(listener);
//
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                activity.runOnUiThread(() -> {
//                    imgIconLeft.startAnimation(rotateL);
//                    imgIconRight.startAnimation(rotateR);
//                });
//            }
//        }, 1500, 150);


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                imgIconLeft.animate().rotation(35).withEndAction(this).setDuration(1000).setInterpolator(new LinearInterpolator()).start();
            }
        };

        imgIconLeft.animate().rotation(35).setDuration(1000).withEndAction(runnable).setInterpolator(new LinearInterpolator()).start();


        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                imgIconRight.animate().rotation(35).withEndAction(this).setDuration(1000).setInterpolator(new LinearInterpolator()).start();
            }
        };

        imgIconRight.animate().rotation(35).setDuration(1000).withEndAction(runnable2).setInterpolator(new LinearInterpolator()).start();

        FontManager.setFont(btnCancel, fontIranSans);
        FontManager.setFont(btnOk, fontIranSans);
        FontManager.setFont(txtMessage, fontIranSans);

    }


}
