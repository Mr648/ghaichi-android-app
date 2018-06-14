package com.sorinaidea.arayeshgah.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.sorinaidea.arayeshgah.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/**
 * Created by mr-code on 6/14/2018.
 */

public class AddCreditDialog extends Dialog implements View.OnClickListener {

    private AppCompatActivity activity;
    private String TAG = "AddCreditDialog";

    public AddCreditDialog(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnPay:
                doPayment();
                break;
        }
    }

    private AppCompatButton btnCancel;
    private AppCompatButton btnPay;

    private TextInputEditText txtCashValue;
    private TextInputLayout inputLayoutCashValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_credit_dialog);

        btnCancel = (AppCompatButton) findViewById(R.id.btnCancel);
        btnPay = (AppCompatButton) findViewById(R.id.btnPay);
        txtCashValue = (TextInputEditText) findViewById(R.id.txtCashValue);
        inputLayoutCashValue = (TextInputLayout) findViewById(R.id.inputLayoutCashValue);

        btnCancel.setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }

    private boolean isValidCashValue() {
        if (txtCashValue.getText().toString().isEmpty()) {
            inputLayoutCashValue.setErrorEnabled(true);
            inputLayoutCashValue.setError(activity.getResources().getString(R.string.err__empty__cash));
            return false;
        } else if (!Pattern.matches("", txtCashValue.getText().toString())) {
            inputLayoutCashValue.setErrorEnabled(true);
            inputLayoutCashValue.setError(activity.getResources().getString(R.string.err__invalid__cash));
            return false;
        } else {
            inputLayoutCashValue.setErrorEnabled(false);
        }

        return true;
    }

    private void doPayment() {
        if (!isValidCashValue()) {
            return;
        }
        int cash = Integer.parseInt(txtCashValue.getText().toString());
        pay(cash);
    }

    private void pay(int cash) {
        // TODO write payment request here!
        Log.i(TAG, "Payment Cash: " + cash);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                dismiss();
            }
        }, 1000);
    }
}
