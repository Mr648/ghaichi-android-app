package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.fast.ReagentCode;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.UserProfileService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetGiftActivity extends AppCompatActivity {

    private TextView txtGetGiftMsg;
    private TextView txtGiftCode;
    private TextView txtGetGift;
    private TextView txtShare;
    private TextView txtIconShare;
    private Toolbar toolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tell_friends);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("معرفی به دوستان");

        txtGetGiftMsg = (TextView) findViewById(R.id.txtGetGiftMsg);
        txtGiftCode = (TextView) findViewById(R.id.txtGiftCode);
        txtIconShare = (TextView) findViewById(R.id.txtIconShare);
        txtGetGift = (TextView) findViewById(R.id.txtGetGift);
        txtShare = (TextView) findViewById(R.id.txtShare);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.MATERIAL_ICONS);
        Typeface irsansFont = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

        FontManager.setFont(txtIconShare, iconFont);
        FontManager.setFont(txtGetGiftMsg, irsansFont);
        FontManager.setFont(txtShare, irsansFont);
        FontManager.setFont(txtGetGift, irsansFont);
        FontManager.setFont(mTitle, irsansFont);

        getCodes();
    }


    private void getCodes() {
        Retrofit retrofit = API.getRetrofit();

        UserProfileService service = retrofit.create(UserProfileService.class);

        Call<ReagentCode> call = service.codes(Util.getAccessKey(getApplicationContext()));

        call.enqueue(new Callback<ReagentCode>() {
            @Override
            public void onResponse(Call<ReagentCode> call, Response<ReagentCode> response) {
                if (response.body() != null) {
                    ReagentCode codes = response.body();
                    txtGiftCode.setText(codes.getToReagentCode());
                    txtIconShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            String message = Util.CONSTANTS.SHARE_GIFT_CODE_MESSAGE + codes.getReagentCode();
                            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                            sendIntent.setType("text/plain");
                            startActivity(Intent.createChooser(sendIntent, getString(R.string.share)));
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ReagentCode> call, Throwable t) {

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
