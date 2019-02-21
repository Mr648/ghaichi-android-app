package com.sorinaidea.ghaichi.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.fast.UserInfo;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.image.ImageUploadTask;
import com.sorinaidea.ghaichi.webservice.UserProfileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by mr-code on 6/17/2018.
 */

public class UserProfileActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    private TextView txtLb1;
    private TextView txtName;
    private TextView txtLb2;
    private TextView txtFamily;
    private TextView txtLb3;
    private TextView txtGender;
    private TextView txtLb4;
    private TextView txtPhone;
    private TextView txtLb5;
    private TextView txtTitle;
    private TextView txtHeaderName;
    private TextView txtHeaderNumber;
    private de.hdodenhof.circleimageview.CircleImageView imgUserImage;


    public void update() {
        Retrofit retrofit = API.getRetrofit();

        UserProfileService service = retrofit.create(UserProfileService.class);

        Call<UserInfo> info = service.info(Auth.getAccessKey(getApplicationContext()));

        info.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {

                if (response.body() != null) {

                    UserInfo info = response.body();

                    Log.d("NAME", info.getName() + " " + info.getFamily());
                    txtName.setText(info.getName());
                    txtFamily.setText(info.getFamily());
                    txtGender.setText(info.getGender().equals("men") ? "آقا" : "خانم");
                    txtPhone.setText(info.getPhone());
                    txtTitle.setText(info.getName() + " " + info.getFamily());
                    txtHeaderName.setText(info.getName() + " " + info.getFamily());
                    txtHeaderNumber.setText(info.getPhone());
                    if (info.getImage() != null) {
                        try {
                            API.getPicasso(getApplicationContext())
                                    .load(API.BASE_URL
                                            + URLDecoder.decode(info.getImage(), "UTF-8")).into(imgUserImage);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {

                    Log.d("NAME", "NULL");
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.d("NAME", t.toString());

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        bindActivity();

        mAppBarLayout.addOnOffsetChangedListener(this);

        mToolbar.inflateMenu(R.menu.activity_user_profile);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    // Start the Intent
                    startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            }
        });
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);


        txtLb1 = (TextView) findViewById(R.id.txtLb1);
        txtName = (TextView) findViewById(R.id.txtTime);
        txtLb2 = (TextView) findViewById(R.id.txtLb2);
        txtFamily = (TextView) findViewById(R.id.txtFamily);
        txtLb3 = (TextView) findViewById(R.id.txtLb3);
        txtGender = (TextView) findViewById(R.id.txtGender);
        txtLb4 = (TextView) findViewById(R.id.txtLb4);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtLb5 = (TextView) findViewById(R.id.txtLb5);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtHeaderName = (TextView) findViewById(R.id.txtHeaderName);
        txtHeaderNumber = (TextView) findViewById(R.id.txtHeaderNumber);
        imgUserImage = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imgUserImage);
        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        update();


        FontManager.setFont(txtLb1, iranSans);
        FontManager.setFont(txtName, iranSans);
        FontManager.setFont(txtLb2, iranSans);
        FontManager.setFont(txtFamily, iranSans);
        FontManager.setFont(txtLb3, iranSans);
        FontManager.setFont(txtGender, iranSans);
        FontManager.setFont(txtLb4, iranSans);
        FontManager.setFont(txtPhone, iranSans);
        FontManager.setFont(txtLb5, iranSans);
        FontManager.setFont(txtTitle, iranSans);
        FontManager.setFont(txtHeaderName, iranSans);
        FontManager.setFont(txtHeaderNumber, iranSans);

    }

    private void bindActivity() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle = (TextView) findViewById(R.id.txtTitle);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_user_profile, menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    //keep track of cropping intent
    final int PIC_CROP = 3;
    //keep track of gallery intent
    final int PICK_IMAGE_REQUEST = 2;
    //captured picture uri
    private Uri picUri;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_pick_photo) {
            try {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //user is returning from capturing an image using the camera
            if (requestCode == PICK_IMAGE_REQUEST) {
                picUri = data.getData();
                Log.d("uriGallery", picUri.toString());
                performCrop();
            }
            //user is returning from cropping the image
            else if (requestCode == PIC_CROP) {
                //get the returned data
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                Bitmap thePic = (Bitmap) extras.get("data");
                //display the returned cropped image

                File f = saveImage(thePic, UserProfileActivity.this);
                try {
//                    new ImageUploadTask(() -> {
//                        Toast.makeText(UserProfileActivity.this, "عملیات موفق آمیز.", Toast.LENGTH_SHORT).show();
//                        update();
//                    }, UserProfileActivity.this).execute(f);

                } catch (Exception ex) {
                }

            }
        }
    }

    private void performCrop() {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
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

    public static File saveImage(Bitmap finalBitmap, Context context) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(Environment.getExternalStorageDirectory() + "/.ghaichi-application/images");
        myDir.mkdirs();

        String fname = new String("USER_SAVED_IMAGE".getBytes());

        File file = new File(myDir, "user_profile_image.png");

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
