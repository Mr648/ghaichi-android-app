package com.sorinaidea.arayeshgah.ui.dialog;

import android.app.Dialog;
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

import com.sorinaidea.arayeshgah.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/**
 * Created by mr-code on 6/14/2018.
 */

public class MessageDialog extends Dialog {

    private AppCompatActivity activity;
    private String TAG = "AddCreditDialog";

    private View.OnClickListener btnOkClickListener;
    private View.OnClickListener btnCancelClickListener;

    public MessageDialog(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
    }

    public void setBtnCancelClickListener(View.OnClickListener btnCancelClickListener) {
        this.btnCancelClickListener = btnCancelClickListener;
    }

    public void setBtnOkClickListener(View.OnClickListener btnOkClickListener) {
        this.btnOkClickListener = btnOkClickListener;
    }


    public void setBtnOkVisible(int visiblity) {
        this.btnOk.setVisibility(visiblity);
    }

    public void setBtnCancelVisible(int visiblity) {
        this.btnCancel.setVisibility(visiblity);
    }

    public void setBtnOkText(String text) {
        this.btnOk.setText(text);
    }

    public void setBtnCancelText(String text) {
        this.btnCancel.setText(text);
    }

    public void setImgIconVisiblity(int visiblity) {
        this.imgIcon.setVisibility(visiblity);
    }

    public void setTextMessage(String txtMessage) {
        this.textMessage = txtMessage;
    }

    public void setImgIconResource(int imgIconResource) {
        this.imgIconResource = imgIconResource;
        imgIcon.setImageResource(imgIconResource);
    }

    private AppCompatButton btnCancel;
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

        btnCancel = (AppCompatButton) findViewById(R.id.btnCancel);
        btnOk = (AppCompatButton) findViewById(R.id.btnOk);
        txtMessage = (AppCompatTextView) findViewById(R.id.txtMessage);
        imgIcon = (AppCompatImageView) findViewById(R.id.imgIcon);

    }




}
