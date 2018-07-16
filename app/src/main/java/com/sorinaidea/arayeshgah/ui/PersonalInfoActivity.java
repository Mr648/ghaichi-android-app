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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.datahelper.Gender;
import com.sorinaidea.arayeshgah.ui.dialog.GenderDialog;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.Util;

/**
 * Created by mr-code on 6/17/2018.
 */

public class PersonalInfoActivity extends AppCompatActivity {

    private Button btnNextStep;
    private AppCompatButton btnSelectGender;
    private TextView txtGender;
    //    private Spinner spnGender;
    private TextInputEditText edtName;
    private TextInputEditText edtFamily;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutFamily;
    private Gender selectedGender;
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
//        spnGender = (Spinner) findViewById(R.id.spnGender);


        edtName = (TextInputEditText) findViewById(R.id.edtName);
        edtFamily = (TextInputEditText) findViewById(R.id.edtFamily);
        inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        inputLayoutFamily = (TextInputLayout) findViewById(R.id.inputLayoutFamily);


        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalInfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnSelectGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                spnGender.performClick();
                GenderDialog genderDialog = new GenderDialog(PersonalInfoActivity.this, btnSelectGender);
                genderDialog.show();

            }
        });
//        spnGender.setSelection(0);

//        ArrayList<GenderItem> genders = new ArrayList<>();
//        String[] genderTitles = getResources().getStringArray(R.array.genders);
//        genders.add(new GenderItem(R.mipmap.ic_gender_male, genderTitles[0]));
//        genders.add(new GenderItem(R.mipmap.ic_gender_female, genderTitles[1]));
//
//        GenderSpinnerAdapter adapter = new GenderSpinnerAdapter(this, R.layout.gender_spinner_item, R.id.txtTitle, genders);
//        spnGender.setAdapter(adapter);
//        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedGender = Gender.values()[position];
//                btnSelectGender.setText((selectedGender == Gender.FEMALE) ? "خانم" : "آقا");
////                    Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.mipmap.ic_launcher);
//                btnSelectGender.setCompoundDrawablesWithIntrinsicBounds(
//                        0,
//                        0,
//                        ((selectedGender == Gender.FEMALE) ? R.mipmap.ic_gender_female : R.mipmap.ic_gender_male),
//                        0
//                );
//                btnSelectGender.setCompoundDrawablePadding(Util.dp(8, PersonalInfoActivity.this));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                selectedGender = Gender.values()[0];
//                btnSelectGender.setText("لطفا جنسیت خود را انتخاب نمایید");
//            }
//        });

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

}
