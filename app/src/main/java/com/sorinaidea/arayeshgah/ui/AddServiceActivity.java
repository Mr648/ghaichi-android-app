package com.sorinaidea.arayeshgah.ui;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.ImagePreviewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr-code on 5/15/2018.
 */

public class AddServiceActivity extends AppCompatActivity {

    Button btnAddService;
    RecyclerView recServiceCategories;
    ImageView imgSelectImages;
    private static final int RESULT_CODE_PICKER_IMAGES = 9000;


    Toolbar toolbar ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
//
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//        userSelectedImageUriList= new ArrayList<>();
//        btnAddService = (Button) findViewById(R.id.btnAddService);
//        btnAddService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddServiceActivity.this, "Service Added.", Toast.LENGTH_SHORT).show();
//            }
//
//        });
//
//        imgSelectImages = (ImageView) findViewById(R.id.imgSelectImages);
//        imgSelectImages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_CODE_PICKER_IMAGES);
//            }
//
//        });
//
//        recImages = (RecyclerView) findViewById(R.id.recImages);
//        recImages.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
//        recImages.setAdapter(new ImagePreviewAdapter(userSelectedImageUriList, getApplicationContext()));
//        recImages.setNestedScrollingEnabled(false);

    }

    // Log tag that is used to distinguish log info.
    private final static String TAG_BROWSE_PICTURE = "BROWSE_PICTURE";

    // Used when request action Intent.ACTION_GET_CONTENT
    private final static int REQUEST_CODE_BROWSE_PICTURE = 1;

    private List<Uri> userSelectedImageUriList = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_BROWSE_PICTURE) {
            if (resultCode == RESULT_OK) {
                // Get return image uri. If select the image from camera the uri like file:///storage/41B7-12F1/DCIM/Camera/IMG_20180211_095139.jpg
                // If select the image from gallery the uri like content://media/external/images/media/1316970.
                Uri fileUri = data.getData();
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();
                    // Save user choose image file uri in list.
                    userSelectedImageUriList = new ArrayList<Uri>();
                    userSelectedImageUriList.add(fileUri);
                    recServiceCategories.setAdapter(new ImagePreviewAdapter(userSelectedImageUriList, getApplicationContext()));
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        userSelectedImageUriList = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            userSelectedImageUriList.add(uri);

                        }
                        recServiceCategories.setAdapter(new ImagePreviewAdapter(userSelectedImageUriList, getApplicationContext()));
                        Log.v("LOG_TAG", "Selected Images" + userSelectedImageUriList.size());
                    }
                }
            }
        }
    }
}
