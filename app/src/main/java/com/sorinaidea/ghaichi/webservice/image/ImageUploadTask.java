package com.sorinaidea.ghaichi.webservice.image;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.sorinaidea.ghaichi.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

/**
 * Created by mr-code on 4/28/2018.
 */

public class ImageUploadTask extends AsyncTask<UploadTask, Void, Boolean> {


    private UploadCallback callback;


    public ImageUploadTask(UploadCallback callback) {
        this.callback = callback;

    }


    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Boolean doInBackground(UploadTask... tasks) {

        boolean result = true;

        if (tasks == null) {
            return false;
        }

        for (UploadTask task : tasks) {
            boolean re = task.upload();
            result &=re;
            System.out.println("FUCK" + re);

        }

        return result;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        callback.uploadCompleted();
    }
}
