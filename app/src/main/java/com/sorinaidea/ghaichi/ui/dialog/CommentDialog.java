package com.sorinaidea.ghaichi.ui.dialog;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.Toast;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.EmptyAdabper;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.UserProfileService;
import com.sorinaidea.ghaichi.webservice.model.requests.AddCommentRequest;
import com.sorinaidea.ghaichi.webservice.model.responses.Response;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by mr-code on 6/14/2018.
 */

public class CommentDialog extends Dialog implements View.OnClickListener {

    private AppCompatActivity activity;
    private String TAG = "CommentDialog";
    private Typeface fontIranSans;
    private int barbershopId;
    private CommentCallback callback;

    public CommentDialog(AppCompatActivity activity, int barbershopId, CommentCallback callback) {
        super(activity);
        this.activity = activity;
        this.fontIranSans = FontManager.getTypeface(activity, FontManager.IRANSANS_TEXTS);
        this.barbershopId = barbershopId;
        this.callback = callback;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnComment:
                doComment();
                break;
        }
    }

    private AppCompatTextView txtTitle;

    private AppCompatButton btnCancel;
    private AppCompatButton btnComment;

    private TextInputEditText txtComment;
    private TextInputLayout inputLayoutComment;
    private AppCompatRatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_comment);

        btnCancel = (AppCompatButton) findViewById(R.id.btnCancel);
        btnComment = (AppCompatButton) findViewById(R.id.btnComment);
        txtComment = (TextInputEditText) findViewById(R.id.txtComment);
        inputLayoutComment = (TextInputLayout) findViewById(R.id.inputLayoutComment);
        ratingBar = (AppCompatRatingBar) findViewById(R.id.ratingBar);
        txtTitle = (AppCompatTextView) findViewById(R.id.txtTitle);

        btnCancel.setOnClickListener(this);
        btnComment.setOnClickListener(this);

        FontManager.setFont(btnCancel, fontIranSans);
        FontManager.setFont(btnComment, fontIranSans);
        FontManager.setFont(txtComment, fontIranSans);
        FontManager.setFont(inputLayoutComment, fontIranSans);
        FontManager.setFont(txtTitle, fontIranSans);

    }

    private boolean isValidComment() {
        if (txtComment.getText().toString().length() > 400) {
            inputLayoutComment.setErrorEnabled(true);
            inputLayoutComment.setError(activity.getResources().getString(R.string.err__invalid__comment));
            return false;
        } else {
            inputLayoutComment.setErrorEnabled(false);
        }

        return true;
    }

    private void doComment() {
        if (!isValidComment()) {
            return;
        }
        String rating = String.valueOf(ratingBar.getRating());
        String comment = txtComment.getText().toString();
        comment(rating, comment);
    }

    private void comment(String rating, String comment) {
        // TODO write payment request here!

        AddCommentRequest commentRequest = new AddCommentRequest(
                Auth.getAccessKey(activity),
                barbershopId,
                comment,
                rating
        );

        Call<Response> call = API.getRetrofit().create(UserProfileService.class).addComment(commentRequest);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body() != null) {
                    if (!response.body().hasError()) {
                        Toast.makeText(activity, "نظر با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                        callback.callback();
                        dismiss();
                    } else {
                        Toast.makeText(activity, "خطا در ثبت نظر", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                } else {
                    Toast.makeText(activity, "خطا در ثبت نظر", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(activity, "خطا در ثبت نظر", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

//        Log.i(TAG, "Rating: " + rating + ", Comment: " + comment);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                dismiss();
//            }
//        }, 1000);
    }


    public interface CommentCallback {
        void callback();
    }
}
