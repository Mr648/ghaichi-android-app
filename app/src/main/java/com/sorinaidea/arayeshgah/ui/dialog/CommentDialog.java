package com.sorinaidea.arayeshgah.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;

import com.sorinaidea.arayeshgah.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/**
 * Created by mr-code on 6/14/2018.
 */

public class CommentDialog extends Dialog implements View.OnClickListener {

    private AppCompatActivity activity;
    private String TAG = "CommentDialog";

    public CommentDialog(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnComment:
                doComment();
                break;
        }
    }

    private AppCompatButton btnCancel;
    private AppCompatButton btnComment;

    private TextInputEditText txtComment;
    private TextInputLayout inputLayoutComment;
    private AppCompatRatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_comment);

        btnCancel = (AppCompatButton) findViewById(R.id.btnCancel);
        btnComment = (AppCompatButton) findViewById(R.id.btnComment);
        txtComment = (TextInputEditText) findViewById(R.id.txtComment);
        inputLayoutComment = (TextInputLayout) findViewById(R.id.inputLayoutComment);
        ratingBar = (AppCompatRatingBar) findViewById(R.id.ratingBar);

        btnCancel.setOnClickListener(this);
        btnComment.setOnClickListener(this);
    }

    private boolean isValidComment() {
        if (txtComment.getText().toString().length() > 400) {
            inputLayoutComment.setErrorEnabled(true);
            inputLayoutComment.setError(activity.getResources().getString(R.string.err__invalid__comment));
            return false;
        } else {
            inputLayoutComment.setErrorEnabled(false);
        }

        return true;
    }

    private void doComment() {
        if (!isValidComment()) {
            return;
        }
        float rating = ratingBar.getRating();
        String comment = txtComment.getText().toString();
        comment(rating, comment);
    }

    private void comment(float rating, String comment) {
        // TODO write payment request here!
        Log.i(TAG, "Rating: " + rating + ", Comment: " + comment);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                dismiss();
            }
        }, 1000);
    }
}
