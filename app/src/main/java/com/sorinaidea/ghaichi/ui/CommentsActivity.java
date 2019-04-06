package com.sorinaidea.ghaichi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.CommentsAdapter;
import com.sorinaidea.ghaichi.adapter.EmptyAdabper;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Comment;
import com.sorinaidea.ghaichi.models.CreateCommentPermission;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mr-code on 6/17/2018.
 */

public class CommentsActivity extends ToolbarActivity {

    private FloatingActionButton fabComment;
    private int barbershopId;
    private RecyclerView recComments;
    private View.OnClickListener fabListener;
    private boolean hasPermissionToAddComment = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initToolbar("نظرات", true, false);

        recComments = findViewById(R.id.recComments);
        fabComment = findViewById(R.id.fabComment);


        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            barbershopId = Integer.parseInt(extras.getString("BARBERSHOP_ID"));
            initComments(barbershopId);
            checkPermission(barbershopId);
        } else {
            finish();
        }


    }


    private void addComment(String comment, String rating, LovelyCustomDialog dialog) {
        API.getRetrofit()
                .create(BarbershopServices.class)
                .comment(Auth.getAccessKey(this), barbershopId, comment, rating)
                .enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
                    @Override
                    public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, Response<com.sorinaidea.ghaichi.models.Response> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            try {
                                longToast(Objects.requireNonNull(response.body()).getMessage());
                            } catch (NullPointerException ex) {
                                toast("خطا در افزودن نظر");
                            }
                        } else {
                            toast(R.string.err_unable_to_connect_to_server);
                        }
                    }

                    @Override
                    public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                        dialog.dismiss();
                        if (t instanceof IOException) {
                            toast(R.string.err_unable_to_connect_to_server);
                        }
                    }
                });
    }

    void initCommentListener() {
        fabListener = new View.OnClickListener() {


            TextInputLayout inputComment;
            TextInputEditText txtComment;
            RatingBar ratingBar;

            private void validateForm(LovelyCustomDialog dialog) {

                String comment = txtComment.getText().toString();

                if (comment.isEmpty()) {
                    inputComment.setErrorEnabled(true);
                    inputComment.setError(getString(R.string.err__empty__comment));
                    return;
                } else {
                    inputComment.setErrorEnabled(false);
                }

                addComment(comment, String.format(Locale.ENGLISH, "%.2f", ratingBar.getRating()), dialog);
            }


            @Override
            public void onClick(View v) {

                View dialogView = getLayoutInflater().inflate(R.layout.dialog_comment, null);

                inputComment = dialogView.findViewById(R.id.inputLayoutComment);
                txtComment = dialogView.findViewById(R.id.txtComment);
                ratingBar = dialogView.findViewById(R.id.ratingBar);

                final LovelyCustomDialog addCommentDialog = new LovelyCustomDialog(CommentsActivity.this)
                        .setView(dialogView)
                        .setTopColorRes(R.color.colorPrimary)
                        .setTitle("نظرسنجی")
                        .setMessage("نظر خود را درباره آرایشگاه اعلام کنید.")
                        .setIcon(R.drawable.ic_star_white_24dp)
                        .configureTitleView(textView -> applyTextFont(textView))
                        .configureMessageView(textView -> applyTextFont(textView))
                        .configureView(view -> applyTextFont(
                                view.findViewById(R.id.inputLayoutComment),
                                view.findViewById(R.id.txtComment),
                                view.findViewById(R.id.btnCancel),
                                view.findViewById(R.id.btnOk)
                        ));

                addCommentDialog.setListener(R.id.btnCancel, view -> addCommentDialog.dismiss());
                addCommentDialog.setListener(R.id.btnOk, view -> validateForm(addCommentDialog));
                addCommentDialog.show();

            }
        };
        fabComment.setOnClickListener(fabListener);
    }

    private void initComments(int barbershopId) {

        API.getRetrofit()
                .create(BarbershopServices.class)
                .comments(Auth.getAccessKey(this), barbershopId)
                .enqueue(new Callback<List<Comment>>() {
                    @Override
                    public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                        if (response.isSuccessful()) {
                            try {
                                if (Objects.requireNonNull(response.body()).isEmpty()) {
                                    recComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recComments.setAdapter(new EmptyAdabper(getApplicationContext()));
                                } else {
                                    recComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recComments.setAdapter(new CommentsAdapter(response.body(), getApplicationContext()));
                                    recComments.setNestedScrollingEnabled(false);
                                }
                            } catch (NullPointerException ex) {
                                recComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                recComments.setAdapter(new EmptyAdabper(getApplicationContext()));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Comment>> call, Throwable t) {

                    }
                });
    }

    private void checkPermission(int barbershopId) {

        API.getRetrofit()
                .create(BarbershopServices.class)
                .checkAccessForComment(Auth.getAccessKey(getApplicationContext()), barbershopId)
                .enqueue(new Callback<CreateCommentPermission>() {
                    @Override
                    public void onResponse(Call<CreateCommentPermission> call, Response<CreateCommentPermission> response) {
                        if (response.isSuccessful()) {
                            try {
                                hasPermissionToAddComment = Objects.requireNonNull(response.body()).isGranted();
                                if (!hasPermissionToAddComment) {
                                    fabComment.setEnabled(false);
                                    fabComment.setVisibility(View.GONE);
                                } else {
                                    fabComment.setEnabled(true);
                                    initCommentListener();
                                    fabComment.setVisibility(View.VISIBLE);
                                }

                            } catch (NullPointerException ex) {
                                hasPermissionToAddComment = false;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateCommentPermission> call, Throwable t) {
                        hasPermissionToAddComment = false;
                        if (t instanceof IOException) {
                            call.clone();
                        }
                    }
                });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_comment, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
