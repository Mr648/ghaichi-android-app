package com.sorinaidea.ghaichi.ui;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.sorinaidea.ghaichi.webservice.image.ImageUploadTask;
import com.sorinaidea.ghaichi.webservice.image.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public abstract class ImageUploaderActivity extends ToolbarActivity {


    //keep track of cropping intent
    protected final static int PIC_CROP = 0xCCC;

    //keep track of gallery intent
    protected final static int PICK_IMAGE_SINGLE = 0x1000;
    protected final static int PICK_IMAGE_MULTIPLE = 0x2000;


    protected ImageUploadTask uploaderTask;

    protected abstract UploadTask generateTask(File... files) throws NullPointerException;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            //user is returning from capturing an image using the camera
            if (requestCode == PICK_IMAGE_SINGLE) {
                Uri imageUri = data.getData();
                performCrop(imageUri);
            } else if (requestCode == PIC_CROP) {

                //get the returned data
                Bundle extras = data.getExtras();

                //get the cropped bitmap
                Bitmap thePic = (Bitmap) extras.get("data");

                //display the returned cropped image
                File file = saveImage(thePic);

                try {
//                   new ImageUploadTask(() -> {
//                        logInfo("آپلود موفقیت آمیز!");
//                    }).execute();

                    generateTask(file).upload();
                } catch (Exception ex) {
                    toast("خطا در آپلود تصویر");
                    logDebug("خطا در آپلود تصویر", ex);
                }
            }
        }

        try {
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {
                    Uri imageUri = data.getData();
                    performCrop(imageUri);
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        File[] files = new File[mClipData.getItemCount()];
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();


                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            files[i] = new File(imageEncoded);
                        }
                        try {
//                            new ImageUploadTask(() -> {
//                                logInfo("آپلود موفقیت آمیز!");
//                            }).execute();

                            generateTask(files).upload();
                        } catch (Exception ex) {
                            toast("خطا در آپلود تصاویر");
                            logDebug("خطا در آپلود تصاویر", ex);
                        }
                    }
                }
            } else {
                toast("هیچ تصویری انتخاب نکردید!");
            }
        } catch (Exception e) {
            toast("خطا در انتخاب تصاویر");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    String imageEncoded;
    List<String> imagesEncodedList;
    private void performCrop(Uri imageUri) {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(imageUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static File saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/.ghaichi-application/images");
        myDir.mkdirs();

        File file = new File(myDir, "ghaichi_image" + System.currentTimeMillis() + ".png");

        if (file.exists()) file.delete();

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

}
