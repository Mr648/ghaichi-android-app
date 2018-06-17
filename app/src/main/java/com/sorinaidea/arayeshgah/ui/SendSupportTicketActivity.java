package com.sorinaidea.arayeshgah.ui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.ImageSliderAdapter;
import com.sorinaidea.arayeshgah.adapter.ServiceCategoryAdapter;
import com.sorinaidea.arayeshgah.adapter.SupportChatAdapter;
import com.sorinaidea.arayeshgah.model.ChatItem;
import com.sorinaidea.arayeshgah.ui.dialog.MessageDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by mr-code on 6/17/2018.
 */

public class SendSupportTicketActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatImageView imgSend;
    private TextInputLayout inputLayoutMessage;
    private TextInputEditText txtMessage;
    private RecyclerView recSupportChat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_support_ticket);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputLayoutMessage = (TextInputLayout) findViewById(R.id.inputLayoutMessage);
        txtMessage = (TextInputEditText) findViewById(R.id.txtMessage);
        imgSend = (AppCompatImageView) findViewById(R.id.imgSend);
        recSupportChat = (RecyclerView) findViewById(R.id.recSupportChat);

        recSupportChat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recSupportChat.setAdapter(new SupportChatAdapter(chatItems(), getApplicationContext()));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("پشتیبانی");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Send Here

                Drawable icon = getResources().getDrawable(R.drawable.ic_signal_wifi_off_white_24dp);
                icon.setColorFilter(new ColorMatrixColorFilter(
                        new float[] {
                                -1, 0, 0, 0, 254, // red = 255 - red
                                0, -1, 0, 0, 75,  // green = 255 - green
                                0, 0, -1, 0, 94,  // blue = 255 - blue
                                0, 0, 0, 1, 0     // alpha = alpha
                        })
                );
                final AlertDialog dialog = new AlertDialog.Builder(SendSupportTicketActivity.this, android.support.design.R.style.Theme_AppCompat_Light_Dialog_Alert)
                        .setTitle("Error")
                        .setIcon(icon)
                        .setMessage("adsasd")
                        .setNeutralButton("Are", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();


            }
        });
    }


    ArrayList<ChatItem> chatItems() {
        ArrayList<ChatItem> chatItem = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ChatItem item = new ChatItem();

            item.setDate("5 minutes ago");
            item.setMessage("How To wash our faces?");
            item.setImageUrl("12313://aadsadd");
            item.setViewType(i % 2 + 1);
            chatItem.add(item);
        }
        return chatItem;
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
