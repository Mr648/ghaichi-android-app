package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.EditText;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.MessageAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Message;
import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.ChatServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by mr-code on 6/17/2018.
 */

public class SupportActivity extends ToolbarActivity {

    RecyclerView recChat;
    FloatingActionButton fabSend;
    EditText txtMessage;

    List<Message> messages;
    MessageAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        messages = new ArrayList<>();

        initToolbar(R.string.toolbar_support, true, false);

        txtMessage = findViewById(R.id.txtMessage);
        fabSend = findViewById(R.id.fabSend);
        recChat = findViewById(R.id.recChat);
        recChat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        fabSend.setOnClickListener(view -> sendMessage());
        applyTextFont(txtMessage);
        fetchMessages();
    }

    private void sendMessage() {

        String message = txtMessage.getText().toString();
        if (message.isEmpty()) {
            toast("ابتدا پیغام خود را بنویسید.");
            return;
        }

        showProgress();
        ChatServices messenger = API.getRetrofit().create(ChatServices.class);
        messenger.send(Auth.getAccessKey(this), message).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    fetchMessages();
                    txtMessage.setText("");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                hideProgress();
                if (t instanceof IOException) {
                    toast("خطا در ابتباط با سرور");
                }
            }
        });
    }

    private void fetchMessages() {
        showProgress();
        ChatServices messenger = API.getRetrofit().create(ChatServices.class);
        messenger.index(Auth.getAccessKey(this)).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call, @NonNull retrofit2.Response<List<Message>> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    try {
                        messages.clear();
                        messages.addAll(Objects.requireNonNull(response.body()));
                        if (adapter == null) {
                            adapter = new MessageAdapter(messages, SupportActivity.this);
                            recChat.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    } catch (NullPointerException ignore) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                hideProgress();
                if (t instanceof IOException) {
                    toast("خطا در برقراری ارتباط با سرور");
                }
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
