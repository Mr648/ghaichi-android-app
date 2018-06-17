package com.sorinaidea.arayeshgah.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.Util;

public class GetGiftActivity extends AppCompatActivity {

    private TextView txtGetGiftMsg;
    private TextView txtGiftCode;
    private TextView txtIconShare;
    private Toolbar toolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tell_friends);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("معرفی به دوستان");


        txtGetGiftMsg = (TextView) findViewById(R.id.txtGetGiftMsg);
        txtGiftCode = (TextView) findViewById(R.id.txtGiftCode);
        txtIconShare = (TextView) findViewById(R.id.txtIconShare);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.MATERIAL_ICONS);
        Typeface irsansFont = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        FontManager.setFont(txtIconShare, iconFont);
        FontManager.setFont(txtGetGiftMsg, irsansFont);

        txtIconShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                String message = Util.CONSTANTS.SHARE_GIFT_CODE_MESSAGE + txtGiftCode.getText();
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getString(R.string.share)));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
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
