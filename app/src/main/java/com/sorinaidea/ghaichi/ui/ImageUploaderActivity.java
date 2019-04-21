package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.graphics.Color;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.sorinaidea.ghaichi.webservice.image.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import id.zelory.compressor.Compressor;

public abstract class ImageUploaderActivity extends ToolbarActivity {

    protected abstract UploadTask generateTask(File... files) throws NullPointerException;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);

            File[] files = new File[images.size()];
            File[] compressedFiles = new File[images.size()];

            try {
                for (int i = 0; i < images.size(); i++) {
                    files[i] = new File(images.get(i).getPath());
                    compressedFiles[i] = new Compressor(this).compressToFile(files[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                 Objects.requireNonNull(generateTask(compressedFiles)).upload();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private ImagePicker imagePicker() {
        return ImagePicker.create(this).toolbarFolderTitle("پوشه") // folder selection title
                .toolbarImageTitle("برای انتخاب لمس کنید") // image selection title
                .toolbarArrowColor(Color.WHITE)
                .showCamera(true) // show camera or not (true by default)
                .folderMode(true) // folder mode (false by default)
                .enableLog(false);// disabling log

    }

    protected void pickMultipleImages() {
        try {
            imagePicker() // folder mode (false by default)
                    .multi() // multi mode (default mode)
                    .limit(10) // max images can be selected (99 by default)
                    .start(); // start image picker activity with request code
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void pickSingleImage() {
        try {
            imagePicker()
                    .single()// pick single image
                    .returnMode(ReturnMode.ALL)// return from gallery and camera
                    .start();// // start picker activity
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
