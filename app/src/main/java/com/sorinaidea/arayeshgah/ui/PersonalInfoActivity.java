package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.datahelper.Gender;
import com.sorinaidea.arayeshgah.layout.PersonalInfoFragment;
import com.sorinaidea.arayeshgah.util.FontManager;

/**
 * Created by mr-code on 6/17/2018.
 */

public class PersonalInfoActivity extends AppCompatActivity {

    private Button btnNextStep;
    private Button btnSelectGender;
    private TextView txtGender;
    private Spinner spnGender;
    private TextInputEditText edtName;
    private TextInputEditText edtFamily;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutFamily;
    private Gender selectedGender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_personal_info);
        btnNextStep = (Button) findViewById(R.id.btnNextStep);
        btnSelectGender = (Button) findViewById(R.id.btnSelectGender);
        spnGender = (Spinner) findViewById(R.id.spnGender);


        edtName = (TextInputEditText) findViewById(R.id.edtName);
        edtFamily = (TextInputEditText) findViewById(R.id.edtFamily);
        inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutFamily);


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
                spnGender.performClick();

            }
        });


        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (!lock) {
                   selectedGender = Gender.values()[position];
                   btnSelectGender.setText((selectedGender == Gender.FEMALE) ? "خانم" : "آقا");
               }                lock = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (!lock) {
                    selectedGender = Gender.values()[0];
                    btnSelectGender.setText("لطفا جنسیت خود را انتخاب نمایید");
                }                lock = false;

            }
        });

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.MATERIAL_ICONS);
        FontManager.setFont(btnNextStep, iconFont);

    }

    boolean lock = true;
}
