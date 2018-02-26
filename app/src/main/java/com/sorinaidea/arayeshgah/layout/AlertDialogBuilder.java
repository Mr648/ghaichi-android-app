package com.sorinaidea.arayeshgah.layout;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.util.SorinaApplication;

/**
 * Created by mr-code on 2/17/2018.
 */

public class AlertDialogBuilder {
    private LinearLayout btnEdit;
    private LinearLayout btnDelete;
    private LinearLayout btnArchive;

    private View.OnClickListener btnEditClickListener;
    private View.OnClickListener btnDeleteClickListener;
    private View.OnClickListener btnArchiveClickListener;


    private  AlertDialog alertDialog;
    public AlertDialogBuilder(View.OnClickListener btnEditClickListener,
                              View.OnClickListener btnDeleteClickListener,
                              View.OnClickListener btnArchiveClickListener) {
        this.btnEditClickListener = btnEditClickListener;
        this.btnDeleteClickListener = btnDeleteClickListener;
        this.btnArchiveClickListener = btnArchiveClickListener;
    }

    public void show(final View parent) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SorinaApplication.currentActivity);

        View dialogView = SorinaApplication.inflater.inflate(R.layout.marker, null);
        dialogBuilder.setView(dialogView);
//
//        btnEdit = (LinearLayout) dialogView.findViewById(R.id.lnrEdit);
//        btnDelete = (LinearLayout) dialogView.findViewById(R.id.lnrDelete);
//        btnArchive = (LinearLayout) dialogView.findViewById(R.id.lnrArchive);
//
//        alertDialog = dialogBuilder.create();
//
//        btnEdit.setOnClickListener(btnEditClickListener);
//        btnDelete.setOnClickListener(btnDeleteClickListener);
//        btnArchive.setOnClickListener(btnArchiveClickListener);
//        VanetApplication.handler.post(new Runnable() {
//            @Override
//            public void run() {
//                alertDialog.show();
//            }
//        });

    }

    public void dismiss(){
        alertDialog.dismiss();
    }
}
