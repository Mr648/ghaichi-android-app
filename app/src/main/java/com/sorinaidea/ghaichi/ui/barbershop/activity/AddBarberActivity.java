package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddBarberActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutFamily;
    private TextInputLayout inputLayoutMobile;

    private TextInputEditText txtName;
    private TextInputEditText txtFamily;
    private TextInputEditText txtMobile;

    private TextView mTitle;
    private Typeface fontIranSans;

    private de.hdodenhof.circleimageview.CircleImageView imgBarber;
    private FloatingActionButton fabSelectBarberImageProfile;

    private void setupInputs() {
        inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        inputLayoutFamily = (TextInputLayout) findViewById(R.id.inputLayoutFamily);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.inputLayoutMobile);

        txtName = (TextInputEditText) findViewById(R.id.txtName);
        txtFamily = (TextInputEditText) findViewById(R.id.txtFamily);
        txtMobile = (TextInputEditText) findViewById(R.id.txtMobile);


        imgBarber = (CircleImageView) findViewById(R.id.imgBarber);
        fabSelectBarberImageProfile = (FloatingActionButton) findViewById(R.id.fabSelectBarberImageProfile);
    }


    private void setFonts() {
        fontIranSans = FontManager.getTypeface(this, FontManager.IRANSANS_TEXTS);

        FontManager.setFont(inputLayoutName, fontIranSans);
        FontManager.setFont(inputLayoutFamily, fontIranSans);
        FontManager.setFont(inputLayoutMobile, fontIranSans);

        FontManager.setFont(txtName, fontIranSans);
        FontManager.setFont(txtFamily, fontIranSans);
        FontManager.setFont(txtMobile, fontIranSans);

        FontManager.setFont(mTitle, fontIranSans);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_barber);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.toolbar_add_barber);

        setupInputs();
        setFonts();
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
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
