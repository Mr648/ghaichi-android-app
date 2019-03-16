package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Barber;
import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.ui.ImageUploaderActivity;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.BarberServices;
import com.sorinaidea.ghaichi.webservice.image.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AddBarberActivity extends ImageUploaderActivity {

    public static final int ADD_BARBER_REQUEST = 0x648;
    public static final int UPDATE_BARBER_REQUEST = 0x649;

    private int flag = 0;
    private FloatingActionButton fab;

    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutFamily;
    private TextInputLayout inputLayoutMobile;

    private TextInputEditText txtName;
    private TextInputEditText txtFamily;
    private TextInputEditText txtMobile;

    private de.hdodenhof.circleimageview.CircleImageView imgBarber;

    private File selectedImage;
    private Barber barber;

    private void setupInputs() {
        fab = findViewById(R.id.fab);
        inputLayoutName = findViewById(R.id.inputLayoutName);
        inputLayoutFamily = findViewById(R.id.inputLayoutFamily);
        inputLayoutMobile = findViewById(R.id.inputLayoutMobile);

        txtName = findViewById(R.id.txtName);
        txtFamily = findViewById(R.id.txtFamily);
        txtMobile = findViewById(R.id.txtMobile);

        imgBarber = findViewById(R.id.imgBarber);
    }


    private void setFonts() {
        applyTextFont(
                inputLayoutName,
                inputLayoutFamily,
                inputLayoutMobile,
                txtName,
                txtFamily,
                txtMobile
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_barber);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initToolbar(R.string.toolbar_add_barber, true, false);
        setupInputs();
        setFonts();

        Bundle extras = getIntent().getExtras();
        flag = 0;
        if (extras != null && !extras.isEmpty()) {
            flag = 1;
            barber = (Barber) extras.get("BARBER");
            txtName.setText(barber.getName());
            txtFamily.setText(barber.getFamily());
            txtMobile.setText(barber.getMobile());
            API.getPicasso(this)
                    .load(barber.getAvatar())
                    .fit()
                    .centerInside()
                    .error(R.drawable.ic_account_circle_white_24dp)
                    .into(imgBarber);
        }


        fab.setOnClickListener(view -> pickSingleImage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_save, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_save:
                submitForm();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateFamily()) {
            return;
        }

        if (!validateMobile()) {
            return;
        }

        if (flag == 0 && !validateImage()) {
            return;
        }


        if (flag == 1) {
            barber.setName(txtName.getText().toString());
            barber.setFamily(txtFamily.getText().toString());
            barber.setMobile(txtMobile.getText().toString());
            updateBarber(barber);
        } else {
            addBarber(barber);
            barber = new Barber();
            barber.setName(txtName.getText().toString());
            barber.setFamily(txtFamily.getText().toString());
            barber.setMobile(txtMobile.getText().toString());
        }

    }


    private void updateBarber(Barber barber) {
        BarberServices service = API.getRetrofit().create(BarberServices.class);
        MultipartBody.Part image = null;

        if (selectedImage != null) {
            image = MultipartBody.Part.createFormData(
                    "avatar"
                    , selectedImage.getName()
                    , RequestBody.create(MediaType.parse("image/*"), selectedImage)
            );
        }

        showProgressDialog(R.string.update_barber_title, R.string.update_barber_progress, R.mipmap.ic_file_upload_white_24dp, R.color.colorAmberAccent900, false);
        logDebug(barber.getName() + " " + barber.getId());
        service.update(Auth.getAccessKey(this), barber.getId(), barber, image).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                hideProgressDialog();
                toast(response.toString());

                if (response.isSuccessful()) {
                    try {
                        Response res = response.body();
                        Objects.requireNonNull(res);
                        confirmAlert("عملیات موفق", res.getMessage(), R.drawable.ic_done_white_24dp, R.color.colorGreenAccent200, view -> {
                            Intent returnIntent = new Intent();
                            setResult(RESULT_OK, returnIntent);
                            finish();
                        });
                    } catch (NullPointerException ex) {
                        toast("پاسخی از سمت سرور دریافت نشد.");
                    }
                } else {
                    toast("خطا در ویرایش آرایشگرز");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                hideProgressDialog();
                if (t instanceof IOException) {
                    toast("خطا در اتصال به سرور");
                    return;
                }
                toast("خطا در ویرایش آرایشگر");
            }
        });

    }


    private void addBarber(Barber barber) {
        BarberServices service = API.getRetrofit().create(BarberServices.class);

        MultipartBody.Part image =
                MultipartBody.Part.createFormData(
                        "avatar"
                        , selectedImage.getName()
                        , RequestBody.create(MediaType.parse("image/*"), selectedImage)
                );

        showProgressDialog(R.string.create_barber_title, R.string.create_barber_progress, R.mipmap.ic_file_upload_white_24dp, R.color.colorAmberAccent900, false);
        service.create(Auth.getAccessKey(this), barber, image).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    try {
                        Response res = response.body();
                        Objects.requireNonNull(res);
                        confirmAlert("عملیات موفق", res.getMessage(), R.drawable.ic_done_white_24dp, R.color.colorGreenAccent200, view -> {
                            Intent returnIntent = new Intent();
                            setResult(RESULT_OK, returnIntent);
                            finish();
                        });
                    } catch (NullPointerException ex) {
                        toast("پاسخی از سمت سرور دریافت نشد.");
                    }
                } else {
                    toast("خطا در افزودن آرایشگر");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                hideProgressDialog();
                if (t instanceof IOException) {
                    toast("خطا در اتصال به سرور");
                    return;
                }
                toast("خطا در افزودن آرایشگر");
            }
        });
    }

    @Override
    protected UploadTask generateTask(File... files) throws NullPointerException {
        if (null == files) throw new NullPointerException("هیچ فایلی انتخاب نشده است.");

        selectedImage = files[0];
        API.getPicasso(this).load(selectedImage).centerCrop().fit().into(imgBarber);

        return new UploadTask();
    }


    private boolean validateName() {
        if (txtName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err__empty__name));
            requestFocus(txtName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateFamily() {
        if (txtFamily.getText().toString().trim().isEmpty()) {
            inputLayoutFamily.setError(getString(R.string.err__empty__family));
            requestFocus(txtFamily);
            return false;
        } else {
            inputLayoutFamily.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateImage() {
        boolean validate = selectedImage != null;
        if (!validate) toast("ابتدا باید تصویر آرایشگر را تعیین کنید.");
        return validate;
    }

    private boolean validateMobile() {
        if (txtMobile.getText().toString().trim().isEmpty()) {
            inputLayoutMobile.setError(getString(R.string.err__empty__phone));
            requestFocus(txtMobile);
            return false;
        } else if (!Pattern.matches(Util.CONSTANTS.REGEX_PHONE, txtMobile.getText().toString())) {
            inputLayoutMobile.setError(getString((R.string.err__invalid__phone)));
            requestFocus(txtMobile);
            return false;
        } else {
            inputLayoutMobile.setErrorEnabled(false);
        }
        return true;
    }
}


/*
confirmAlert("انتخاب تصویر"
                    , "آیا مایلید برای آرایشگر تصویر انتخاب کنید؟"
                    , R.drawable.ic_photo_white_18dp
                    , R.color.colorPrimary
                    , "بله"
                    , v -> pickSingleImage()
                    , "خیر"
                    , null
            );
*/