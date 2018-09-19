package com.sorinaidea.arayeshgah.ui;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.AddSampleWorkAdapter;
import com.sorinaidea.arayeshgah.adapter.ImagePreviewAdapter;
import com.sorinaidea.arayeshgah.adapter.ItemOffsetDecoration;
import com.sorinaidea.arayeshgah.ui.dialog.AddSampleDialog;
import com.sorinaidea.arayeshgah.util.ImagePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mr-code on 5/15/2018.
 */

public class ManageSampleActivity extends AppCompatActivity {
    private RecyclerView recSamples;

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private int NUM_COLUMNS = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sample_work);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recSamples = (RecyclerView) findViewById(R.id.recSamples);
        recSamples.setLayoutManager(new GridLayoutManager(getApplicationContext(), NUM_COLUMNS, LinearLayoutManager.VERTICAL, false));
        recSamples.setHasFixedSize(true);
        recSamples.addItemDecoration(new ItemOffsetDecoration(getApplicationContext(), R.dimen._4dp));
        recSamples.setNestedScrollingEnabled(false);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener((view) -> {
            Intent intent = new Intent(MediaStore.INTENT_ACTION_MEDIA_SEARCH);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);

//            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGE_MULTIPLE);

//            onPickImage(view);
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("مدیریت نمونه کارها");
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private static final int RESULT_CODE_PICKER_IMAGES = 9000;


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_service);
//
//
//    }

    // Log tag that is used to distinguish log info.
    private final static String TAG_BROWSE_PICTURE = "BROWSE_PICTURE";

    // Used when request action Intent.ACTION_GET_CONTENT
    private final static int REQUEST_CODE_BROWSE_PICTURE = 1;

    private List<Uri> userSelectedImageUriList = null;

    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE_BROWSE_PICTURE) {
//            if (resultCode == RESULT_OK) {
//                // Get return image uri. If select the image from camera the uri like file:///storage/41B7-12F1/DCIM/Camera/IMG_20180211_095139.jpg
//                // If select the image from gallery the uri like content://media/external/images/media/1316970.
//                Uri fileUri = data.getData();
//                if (data.getData() != null) {
//
//                    Uri mImageUri = data.getData();
//                    // Save user choose image file uri in list.
//                    userSelectedImageUriList = new ArrayList<Uri>();
//                    userSelectedImageUriList.add(fileUri);

//                } else {
//                    if (data.getClipData() != null) {
//                        ClipData mClipData = data.getClipData();
//                        userSelectedImageUriList = new ArrayList<Uri>();
//                        for (int i = 0; i < mClipData.getItemCount(); i++) {
//
//                            ClipData.Item item = mClipData.getItemAt(i);
//                            Uri uri = item.getUri();
//                            userSelectedImageUriList.add(uri);
//
//                        }
//                        recSamples.setAdapter(new ImagePreviewAdapter(userSelectedImageUriList, getApplicationContext()));
//                        Log.v("LOG_TAG", "Selected Images" + userSelectedImageUriList.size());
//                    }
//                }
//            }
//        }

        try {
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);

                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }
                        runOnUiThread(() -> {
                            recSamples.setAdapter(new AddSampleWorkAdapter(mArrayUri, getApplicationContext()));
                        });

                        AddSampleDialog dialog = new AddSampleDialog(ManageSampleActivity.this);
                        dialog.show();

                        Log.v("LOG_TAG", "Selected Images" + Arrays.toString(mArrayUri.toArray()));

                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);


    }


//    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter
//
//    public void onPickImage(View view) {
//        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
//        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch(requestCode) {
//            case PICK_IMAGE_ID:
//                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
//                recSamples.setAdapter(new AddSampleWorkAdapter(Arrays.asList(bitmap), getApplicationContext()));
////
//                        AddSampleDialog dialog = new AddSampleDialog(ManageSampleActivity.this);
//                        dialog.show();
//
//                break;
//            default:
//                super.onActivityResult(requestCode, resultCode, data);
//                break;
//        }
//    }
}
