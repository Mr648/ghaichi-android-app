package com.sorinaidea.arayeshgah.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.SorinaApplication;

/**
 * Created by mr-code on 2/17/2018.
 */

public class SorinaActivity extends AppCompatActivity {
    @Override
    protected void onResume() {

        super.onResume();
        SorinaApplication.currentActivity = this;
    }

    public void showDoneMessage(String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.SorinaDialogTheme);
        final AlertDialog dialog;


        View dialogView = SorinaApplication.inflater.inflate(R.layout.alert_dialog, null);
        builder.setView(dialogView);

        dialog = builder.create();

        Button btnDialogOk = (Button) dialogView.findViewById(R.id.btnDialogOk);
        Button btnDialogCancel = (Button) dialogView.findViewById(R.id.btnDialogCancel);
        TextView txtDialogIcon = (TextView) dialogView.findViewById(R.id.txtDialogIcon);
        TextView txtDialogMessage = (TextView) dialogView.findViewById(R.id.txtDialogMessage);

        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtDialogMessage.setText("شسیسشیشی");
//        txtDialogMessage.setText(getResources().getString(R.string.error_no_internet_connection));

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.MATERIAL_ICONS);
        FontManager.setFont(txtDialogIcon, iconFont);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();
    }
}
