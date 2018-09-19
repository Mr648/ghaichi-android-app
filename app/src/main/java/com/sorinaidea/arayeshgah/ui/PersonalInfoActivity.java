package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.datahelper.Gender;
import com.sorinaidea.arayeshgah.ui.dialog.GenderDialog;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.GhaichiPrefrenceManager;
import com.sorinaidea.arayeshgah.util.Util;
import com.sorinaidea.arayeshgah.webservice.API;
import com.sorinaidea.arayeshgah.webservice.UserProfileService;
import com.sorinaidea.arayeshgah.webservice.model.requests.EditProfileRequest;
import com.sorinaidea.arayeshgah.webservice.model.responses.Response;
import com.sorinaidea.arayeshgah.webservice.model.responses.VerificationResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by mr-code on 6/17/2018.
 */

public class PersonalInfoActivity extends AppCompatActivity {


    private Button btnNextStep;
    private AppCompatButton btnSelectGender;
    private TextInputEditText edtName;
    private TextInputEditText edtFamily;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutFamily;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_personal_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewCompat.setElevation(toolbar, Util.dp(5, PersonalInfoActivity.this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("اطلاعات شخصی");


        btnNextStep = (Button) findViewById(R.id.btnNextStep);
        btnSelectGender = (AppCompatButton) findViewById(R.id.btnSelectGender);


        edtName = (TextInputEditText) findViewById(R.id.edtName);
        edtFamily = (TextInputEditText) findViewById(R.id.edtFamily);
        inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        inputLayoutFamily = (TextInputLayout) findViewById(R.id.inputLayoutFamily);


        btnNextStep.setOnClickListener((view) -> {
            completeUserProfile(edtName.getText().toString(), edtFamily.getText().toString(), getSelectedGender());
//            Intent intent = new Intent(PersonalInfoActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
        });

        btnSelectGender.setOnClickListener((view) -> {
            GenderDialog genderDialog = new GenderDialog(PersonalInfoActivity.this, btnSelectGender);
            genderDialog.show();
        });

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.MATERIAL_ICONS);
        Typeface iranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        FontManager.setFont(btnNextStep, iconFont);
        FontManager.setFont(mTitle, iranSans);
        FontManager.setFont(btnSelectGender, iranSans);
        FontManager.setFont(edtName, iranSans);
        FontManager.setFont(edtFamily, iranSans);
        FontManager.setFont(inputLayoutName, iranSans);
        FontManager.setFont(inputLayoutFamily, iranSans);

    }

    private Boolean getSelectedGender() {
        return (btnSelectGender.getText().toString().isEmpty() || btnSelectGender.getText().toString().length() > 4)
                ? null : btnSelectGender.getText().toString().equals(Gender.MALE.getType());
    }

    private boolean validateForm(final String name, final String family, final boolean gender) {
        if (edtName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err__empty__name));
            requestFocus(edtName);
            return false;

        } else if (edtFamily.getText().toString().trim().isEmpty()) {
            inputLayoutFamily.setError(getString((R.string.err__empty__family)));
            requestFocus(edtFamily);
            return false;

        } else if (getSelectedGender() == null) {
            btnSelectGender.setText("لطفا جنسیت خود را انتخاب کنید!");
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
            inputLayoutFamily.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {

        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

    }

    private void completeUserProfile(final String name, final String family, final boolean gender) {

        if (!validateForm(name, family, gender)) return;

        String accessKey = GhaichiPrefrenceManager.getString(getApplicationContext(), "access_key", null);
        if (accessKey == null || accessKey.isEmpty()) return;

        Retrofit retrofit = API.getRetrofit();

        UserProfileService webService = retrofit.create(UserProfileService.class);


        Call<com.sorinaidea.arayeshgah.webservice.model.responses.Response> callWebservice =

                webService.update(new EditProfileRequest(accessKey, name, family, gender));

        callWebservice.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body() != null) {

                    if (!response.body().hasError()) {
                        Intent intent = new Intent(PersonalInfoActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Log.i("", "onResponse.SUCCESS: " + response.body().getMessage());

                    } else {
                        Log.i("", "onResponse.FAILURE: " + response.body().getMessage());
                    }
                } else {
                    Log.i("", "onResponse.NULL: " + null);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.i("", "onFailure: " + t.getMessage());

            }
        });
    }

}
