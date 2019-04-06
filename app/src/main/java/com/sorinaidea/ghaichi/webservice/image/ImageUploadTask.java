package com.sorinaidea.ghaichi.webservice.image;

import android.os.AsyncTask;

import java.util.Objects;

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

        try {
            Objects.requireNonNull(tasks);
//            for (UploadTask task : tasks) {
//                Objects.requireNonNull(task);
//            }
        } catch (NullPointerException ex) {
            return false;
        }

        for (UploadTask task : tasks) {
            boolean re = task.upload();
            result &= re;
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
