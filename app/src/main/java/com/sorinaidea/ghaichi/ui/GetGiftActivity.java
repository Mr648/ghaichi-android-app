package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.view.MenuItem;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;

public class GetGiftActivity extends ToolbarActivity {

    TextView txtGetGiftMsg;
    TextView txtGetGift;
    TextView txtShare;
    TextView txtIconShare;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tell_friends);

        initToolbar("معرفی به دوستان", true, false);

        txtGetGiftMsg = findViewById(R.id.txtGetGiftMsg);
        txtIconShare = findViewById(R.id.txtIconShare);
        txtGetGift = findViewById(R.id.txtGetGift);
        txtShare = findViewById(R.id.txtShare);


        applyIconsFont(txtIconShare);
        applyTextFont(
                txtGetGiftMsg,
                txtShare,
                txtGetGift
        );

        txtIconShare.setOnClickListener(view -> {
            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle("معرفی به دوستان")
                    .setText("https://ghaichi.com/install")
                    .startChooser();
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
