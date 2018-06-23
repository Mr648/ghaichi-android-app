package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.GenderSpinnerAdapter;
import com.sorinaidea.arayeshgah.datahelper.Gender;
import com.sorinaidea.arayeshgah.datahelper.GenderItem;
import com.sorinaidea.arayeshgah.util.Util;

import java.util.ArrayList;

/**
 * Created by mr-code on 6/17/2018.
 */

public class EditProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatImageView imgUserProfile;
    private LinearLayout lnrDoneEditing;
    private Button btnSelectGender;
    private Spinner spnGender;
    private TextInputEditText txtName;
    private TextInputEditText txtFamily;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutFamily;
    private Gender selectedGender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("ویرایش حساب کاربری");

        imgUserProfile = (AppCompatImageView) findViewById(R.id.imgUserProfile);
        btnSelectGender = (AppCompatButton) findViewById(R.id.btnSelectGender);
        spnGender = (Spinner) findViewById(R.id.spnGender);

        txtName = (TextInputEditText) findViewById(R.id.txtName);
        txtFamily = (TextInputEditText) findViewById(R.id.txtFamily);
        lnrDoneEditing = (LinearLayout) findViewById(R.id.lnrDoneEditing);


        imgUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO open gallery to select profile photo!
            }
        });

        lnrDoneEditing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSelectGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spnGender.performClick();
            }
        });
        spnGender.setSelection(0);

        ArrayList<GenderItem> genders = new ArrayList<>();
        String[] genderTitles = getResources().getStringArray(R.array.genders);
        genders.add(new GenderItem(R.mipmap.ic_gender_male, genderTitles[0]));
        genders.add(new GenderItem(R.mipmap.ic_gender_female, genderTitles[1]));

        GenderSpinnerAdapter adapter = new GenderSpinnerAdapter(this, R.layout.gender_spinner_item, R.id.txtTitle, genders);
        spnGender.setAdapter(adapter);
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = Gender.values()[position];
                btnSelectGender.setText((selectedGender == Gender.FEMALE) ? "خانم" : "آقا");
//                    Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.mipmap.ic_launcher);
                btnSelectGender.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        ((selectedGender == Gender.FEMALE) ? R.mipmap.ic_gender_female : R.mipmap.ic_gender_male),
                        0
                );
                btnSelectGender.setCompoundDrawablePadding(Util.dp(8, EditProfileActivity.this));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedGender = Gender.values()[0];
                btnSelectGender.setText("لطفا جنسیت خود را انتخاب نمایید");
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
