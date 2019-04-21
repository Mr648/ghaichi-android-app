package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by mr-code on 6/17/2018.
 */

public class BarbershopProfileActivity extends AppCompatActivity{
//        ImageUploaderActivity{
//        implements AppBarLayout.OnOffsetChangedListener {

//    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
//    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
//    private static final int ALPHA_ANIMATIONS_DURATION = 200;
//
//    private boolean mIsTheTitleVisible = false;
//    private boolean mIsTheTitleContainerVisible = true;
//
//    private LinearLayout mTitleContainer;
//    private AppBarLayout mAppBarLayout;
//    private TextView txtHeaderName;
//    private TextView txtHeaderNumber;
//    private RecyclerView recData;
//
//    private de.hdodenhof.circleimageview.CircleImageView imgUserImage;
//
//    DataAdapter adapter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profiles);
//        bindActivity();
//        update();
//    }
//
//    public void update() {
//        showProgressDialog(null, "در حال دریافت اطلاعات", false);
//
//        BarbershopProfileServices service = API.getRetrofit(this).create(BarbershopProfileServices.class);
//
//        Call<List<Data>> info = service.barbershop();
//
//        info.enqueue(new Callback<List<Data>>() {
//            @Override
//            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        Objects.requireNonNull(response.body());
//                        updateView(response.body());
//                    } catch (NullPointerException ignored) {
//                        alert("خطا", "مشکل در دریافت اطلاعات", R.drawable.ic_close, R.color.colorRedAccent900);
//                    }
//                }
//                hideProgressDialog();
//            }
//
//            @Override
//            public void onFailure(Call<List<Data>> call, Throwable t) {
//                if (t instanceof IOException)
//                    alert("خطا", "مشکل در ارتباط با سرور", R.drawable.ic_close, R.color.colorRedAccent900);
//
//                alert("خطا", "مشکل در دریافت اطلاعات", R.drawable.ic_close, R.color.colorRedAccent900);
//            }
//        });
//    }
//
//
//    public void updateProfileImage(Data image) {
//        if (image == null) return;
//        if (image.getValue().equals("default")) return;
//        // TODO how to use Picasso
//        API.getPicasso(this)
//                .load(image.getValue())
//                .fit()
//                .centerInside()
//                .into(imgUserImage);
//    }
//
//    public void updateView(List<Data> userData) {
//        Data data = null;
//        for (Data d : userData) {
//            if (d.getKeyEn().equals("logo")) {
//                data = d;
//                userData.remove(d);
//                break;
//            }
//        }
//
//        updateProfileImage(data);
//        recData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        recData.setAdapter(new DataAdapter(userData, this, edit -> {
//            new LovelyTextInputDialog(this)
//                    .setTopColorRes(R.color.colorAmberAccent900)
//                    .setIcon(R.drawable.ic_edit_white_24dp)
//                    .setTitle("ویرایش")
//                    .setMessage(edit.getKeyFa() + " خود را ویرایش کنید.")
//                    .setInitialInput(edit.getValue())
//                    .setConfirmButton("تایید", text -> {
//                        //&& !text.equals(edit.getValue())
//                        if (!text.isEmpty()) {
//                            updateField(edit.getKeyEn(), text);
//                        }
//                    })
//                    .configureMessageView(this::applyTextFont)
//                    .configureTitleView(this::applyTextFont)
//                    .show();
//
//
//        }));
//    }
//
//    private void updateField(String key, String value) {
//        BarbershopProfileServices serviceServices = API.getRetrofit(this).create(BarbershopProfileServices.class);
//        serviceServices
//                .updateBarbershop(key, value)
//                .enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
//                    @Override
//                    public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, Response<com.sorinaidea.ghaichi.models.Response> response) {
//                        if (response.isSuccessful()) {
//                            toast(response.body().getMessage());
//                            logDebug(key + ":" + value);
//                            update();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
//                        toast("خطا در بروزرسانی داده‌");
//                    }
//                });
//    }
//
//    private void bindActivity() {
//        initToolbar(R.string.toolbar_barbershop_profile, false, false);
//        mTitleContainer = findViewById(R.id.main_linearlayout_title);
//        mAppBarLayout = findViewById(R.id.main_appbar);
//        mAppBarLayout.addOnOffsetChangedListener(this);
//        startAlphaAnimation(toolbarTitle, 0, View.INVISIBLE);
//        txtHeaderName = findViewById(R.id.txtHeaderName);
//        txtHeaderNumber = findViewById(R.id.txtHeaderNumber);
//        imgUserImage = findViewById(R.id.imgUserImage);
//        recData = findViewById(R.id.recData);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_profiles, menu);
//        return true;
//    }
//
//    @Override
//    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
//        int maxScroll = appBarLayout.getTotalScrollRange();
//        float percentage = (float) Math.abs(offset) / (float) maxScroll;
//        handleAlphaOnTitle(percentage);
//        handleToolbarTitleVisibility(percentage);
//    }
//
//    private void handleToolbarTitleVisibility(float percentage) {
//        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
//            if (!mIsTheTitleVisible) {
//                startAlphaAnimation(toolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
//                mIsTheTitleVisible = true;
//            }
//        } else {
//
//            if (mIsTheTitleVisible) {
//                startAlphaAnimation(toolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                mIsTheTitleVisible = false;
//            }
//        }
//    }
//
//    private void handleAlphaOnTitle(float percentage) {
//        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
//            if (mIsTheTitleContainerVisible) {
//                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                mIsTheTitleContainerVisible = false;
//            }
//
//        } else {
//
//            if (!mIsTheTitleContainerVisible) {
//                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
//                mIsTheTitleContainerVisible = true;
//            }
//        }
//    }
//
//    public static void startAlphaAnimation(View v, long duration, int visibility) {
//        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
//                ? new AlphaAnimation(0f, 1f)
//                : new AlphaAnimation(1f, 0f);
//
//        alphaAnimation.setDuration(duration);
//        alphaAnimation.setFillAfter(true);
//        v.startAnimation(alphaAnimation);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_pick_photo) {
//            pickSingleImage();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected UploadTask generateTask(File... files) throws NullPointerException {
//        if (null == files) throw new NullPointerException("هیچ فایلی انتخاب نشده است.");
//
//        UploadTask task;
//
//        MultipartBody.Part image = MultipartBody.Part.createFormData("logo", files[0].getName(),
//                RequestBody.create(MediaType.parse("image/*"), files[0]));
//
//        task = new UploadTask(new SingleImageUploader(image) {
//
//            boolean uploadResult = false;
//
//            @Override
//            public boolean isDone() {
//                return uploadResult;
//            }
//
//            @Override
//            public boolean upload(MultipartBody.Part image) {
//                showProgress();
//                BarbershopProfileServices serviceServices = API.getRetrofit(BarbershopProfileActivity.this).create(BarbershopProfileServices.class);
//                serviceServices.changeLogo(image).enqueue(new Callback<UploadImageResponse>() {
//                    @Override
//                    public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
//                        hideProgress();
//                        if (response.isSuccessful()) {
//                            UploadImageResponse result = response.body();
//                            result.getImages();
//                            alert("آپلود موفق", "تصویر با موفقیت افزوده شدند.", R.mipmap.ic_file_upload_white_24dp, R.color.colorTransaction);
//                            uploadResult = true;
//                        } else {
//                            alert("آپلود ناموفق", "خطا در آپلود تصویر.", R.drawable.ic_info, R.color.colorAmberAccent900);
//                            uploadResult = false;
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<UploadImageResponse> call, Throwable t) {
//                        hideProgress();
//                        uploadResult = false;
//                        if (t instanceof IOException) {
//                            alert("قطع ارتباط", "خطا در اتصال به سرور", R.drawable.ic_signal_wifi_off_white_24dp, R.color.colorGrayDark);
//                            return;
//                        }
//                        alert("آپلود ناموفق", "خطا در آپلود تصاویر.", R.drawable.ic_close, R.color.colorRedAccent900);
//                    }
//                });
//                return uploadResult;
//            }
//        });
//
//        return task;
//    }

}
