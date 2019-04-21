package com.sorinaidea.ghaichi.webservice.image;

import android.os.AsyncTask;

import java.util.Objects;

/**
 * Created by mr-code on 4/28/2018.
 */

public class ImageUploadTask extends AsyncTask<UploadTask, Void, Boolean> {

    /**
     * Callback
     */
    private UploadCallback callback;

    /**
     *
     * @param callback callback that will be called when upload completed.
     *                 see {@link UploadCallback#uploadCompleted}
     */
    public ImageUploadTask(UploadCallback callback) {
        this.callback = callback;
    }

    /**
     *
     */
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Boolean doInBackground(UploadTask... tasks) {

        boolean result = true;

        try {
            // TODO check if tasks are not null.
            Objects.requireNonNull(tasks);
        } catch (NullPointerException ignored) {
            return false;
        }


        for (UploadTask task : tasks) {
            // call upload on each task.
            boolean re = task.upload();
            result &= re;
        }

        return result;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        callback.uploadCompleted();
    }
}
