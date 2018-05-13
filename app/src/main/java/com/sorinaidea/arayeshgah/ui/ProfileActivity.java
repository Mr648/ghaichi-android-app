package com.sorinaidea.arayeshgah.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.util.FontManager;

/**
 * Created by mr-code on 4/8/2018.
 */

public class ProfileActivity extends SorinaActivity {

    private TextView txtName;
    private TextView txtNumber;
    private TextView txtGender;

    private TextView txtIconName;
    private TextView txtIconNumber;
    private TextView txtIconGender;

    private Button btnEditInformation;
    private Typeface fontMaterialIcon;
    private Typeface fontIranSans;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = (TextView) findViewById(R.id.txtName);
        txtNumber = (TextView) findViewById(R.id.txtNumber);
        txtGender = (TextView) findViewById(R.id.txtGender);

        txtIconName = (TextView) findViewById(R.id.txtIconName);
        txtIconNumber = (TextView) findViewById(R.id.txtIconNumber);
        txtIconGender = (TextView) findViewById(R.id.txtIconGender);

        btnEditInformation = (Button) findViewById(R.id.btnEditInformation);

        fontMaterialIcon = FontManager.getTypeface(getApplicationContext(), FontManager.MATERIAL_ICONS);
        fontIranSans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);


        FontManager.setFont(txtName, fontIranSans);
        FontManager.setFont(txtNumber, fontIranSans);
        FontManager.setFont(txtGender, fontIranSans);

        FontManager.setFont(txtIconName, fontMaterialIcon);
        FontManager.setFont(txtIconNumber, fontMaterialIcon);
        FontManager.setFont(txtIconGender, fontMaterialIcon);

        FontManager.setFont(btnEditInformation, fontMaterialIcon);
    }
}
