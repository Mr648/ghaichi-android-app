package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.EmptyAdabper;
import com.sorinaidea.ghaichi.adapter.ServiceCategoryAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Category;
import com.sorinaidea.ghaichi.ui.ToolbarActivity;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.HttpCodes;
import com.sorinaidea.ghaichi.webservice.barbershop.CategoryServices;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mr-code on 5/15/2018.
 */

public class CategoriesActivity extends ToolbarActivity {


    RecyclerView recServiceCategories;
    CoordinatorLayout coordinatorLayout;

    ServiceCategoryAdapter adapter;
    ArrayList<Category> categories;

    FloatingActionButton fab;

    ServiceCategoryAdapter.OnItemClickListener onItemClickListener;

    void initEditListener() {
        onItemClickListener = new ServiceCategoryAdapter.OnItemClickListener() {

            TextInputEditText edtName;
            TextInputEditText edtDescription;
            TextInputLayout inputName;
            TextInputLayout inputDescription;

            private void validateForm(LovelyCustomDialog dialog, Category category) {

                String name = edtName.getText().toString();
                String desc = edtDescription.getText().toString();

                if (name.isEmpty()) {
                    inputName.setErrorEnabled(true);
                    inputName.setError(getString(R.string.err__empty__name));
                    return;
                } else {
                    inputName.setErrorEnabled(false);
                }

                if (desc.isEmpty()) {
                    inputDescription.setErrorEnabled(true);
                    inputDescription.setError(getString(R.string.err__empty__description));
                    return;
                } else {
                    inputDescription.setErrorEnabled(false);
                }

                category.setName(edtName.getText().toString());
                category.setDescription(edtDescription.getText().toString());

                updateCategory(category, dialog);
            }

            @Override
            public void deleteItem(Category category) {
                AlertDialog addCategoryDialog = new AlertDialog.Builder(CategoriesActivity.this)
                        .setIcon(R.drawable.ic_warning_amber_48pt)
                        .setTitle("حذف دسته‌بندی")
                        .setMessage(String.format("%s %s %s?\n%s", "آیا دسته بندی", category.getName(), "حذف شود", "این عمل قابل برگشت نیست!"))
                        .setPositiveButton("حذف شود", (dialog, which) -> {
                            deleteCategory(category, dialog);
                        }).setNegativeButton("انصراف", (dialog, which) -> {
                            dialog.dismiss();
                        }).create();

                addCategoryDialog.show();
            }

            @Override
            public void updateItem(Category category) {


                View dialogView = getLayoutInflater().inflate(R.layout.dialog_category, null);

                edtName = dialogView.findViewById(R.id.edtName);
                edtDescription = dialogView.findViewById(R.id.edtDescription);
                inputName = dialogView.findViewById(R.id.inputName);
                inputDescription = dialogView.findViewById(R.id.inputDescription);

                edtName.setText(category.getName());
                edtDescription.setText(category.getDescription());

                final LovelyCustomDialog updateCategoryDialog = new LovelyCustomDialog(CategoriesActivity.this)
                        .setView(dialogView)
                        .setTopColorRes(R.color.colorBlue)
                        .setTitle("ویرایش دسته‌بندی")
                        .setMessage("اطلاعات جدید دسته‌بندی را وارد کنید")
                        .setIcon(R.drawable.ic_create_white_18dp)
                        .configureTitleView(textView->applyTextFont(textView))
                        .configureMessageView(textView->applyTextFont(textView))
                        .configureView(view -> applyTextFont(
                                view.findViewById(R.id.inputName),
                                view.findViewById(R.id.edtName),
                                view.findViewById(R.id.inputDescription),
                                view.findViewById(R.id.edtDescription),
                                view.findViewById(R.id.btnCancel),
                                view.findViewById(R.id.btnOk)
                        ));

                updateCategoryDialog.setListener(R.id.btnCancel, view -> updateCategoryDialog.dismiss());
                updateCategoryDialog.setListener(R.id.btnOk, view -> validateForm(updateCategoryDialog, category));
                updateCategoryDialog.show();

                /*TextInputEditText input = new TextInputEditText(CategoriesActivity.this);
                TextInputEditText inputDesc = new TextInputEditText(CategoriesActivity.this);

                input.setText(category.getName());
                inputDesc.setText(category.getDescription());

                input.setHint("نام");
                inputDesc.setHint("توضیحات");

                applyTextFont(input, inputDesc);

                LinearLayout linearLayout = new LinearLayout(CategoriesActivity.this);


                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(input);
                linearLayout.addView(inputDesc);
                linearLayout.setPadding(8, 4, 8, 4);

                AlertDialog addCategoryDialog = new AlertDialog.Builder(CategoriesActivity.this)
                        .setView(linearLayout)
                        .setTitle("ویرایش دسته‌بندی")
                        .setMessage("نام دسته‌بندی را وارد نمایید")
                        .setPositiveButton("ویرایش", (dialog, which) -> {
                            category.setName(input.getText().toString());
                            category.setDescription(inputDesc.getText().toString());
                            updateCategory(category, dialog);
                        }).setNegativeButton("انصراف", (dialog, which) -> {
                            dialog.dismiss();
                        }).create();

                addCategoryDialog.show();*/
            }
        };
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_category);

        initToolbar("دسته‌بندی‌ها", true, false);
        initEditListener();

        categories = new ArrayList<>();
        recServiceCategories = findViewById(R.id.recServiceCategories);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        fab = findViewById(R.id.fab);


        recServiceCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        if (savedInstanceState == null || !savedInstanceState.containsKey("categories")) {
            initCategories();
        } else {
            categories = savedInstanceState.getParcelableArrayList("categories");
        }

        fab.setOnClickListener(new View.OnClickListener() {

            TextInputEditText edtName;
            TextInputEditText edtDescription;
            TextInputLayout inputName;
            TextInputLayout inputDescription;

            private void validateForm(LovelyCustomDialog dialog) {


                String name = edtName.getText().toString();
                String desc = edtDescription.getText().toString();

                if (name.isEmpty()) {
                    inputName.setErrorEnabled(true);
                    inputName.setError(getString(R.string.err__empty__name));
                    return;
                } else {
                    inputName.setErrorEnabled(false);
                }

                if (desc.isEmpty()) {
                    inputDescription.setErrorEnabled(true);
                    inputDescription.setError(getString(R.string.err__empty__description));
                    return;
                } else {
                    inputDescription.setErrorEnabled(false);
                }

                addCategory(name, desc, dialog);
            }

            @Override
            public void onClick(View v) {
//                input = new TextInputEditText(CategoriesActivity.this);
//                inputDesc = new TextInputEditText(CategoriesActivity.this);
//
//                applyTextFont(input, inputDesc);
//
//                LinearLayout linearLayout = new LinearLayout(CategoriesActivity.this);
//
//                input.setHint("نام");
//                inputDesc.setHint("توضیحات");
//
//                linearLayout.setOrientation(LinearLayout.VERTICAL);
//                linearLayout.addView(input);
//                linearLayout.addView(inputDesc);
//                linearLayout.setPadding(8, 4, 8, 4);
//
//                AlertDialog addCategoryDialog = new AlertDialog.Builder(CategoriesActivity.this)
//                        .setView(linearLayout)
//                        .setTitle("افزودن دسته‌بندی")
//                        .setMessage("نام دسته‌بندی را وارد نمایید")
//                        .setPositiveButton("افزودن", (dialog, which) -> {
//                            validateForm(dialog);
//                        }).setNegativeButton("انصراف", (dialog, which) -> {
//                            dialog.dismiss();
//                        }).create();
//                addCategoryDialog.show();
//
//                new LovelyTextInputDialog(CategoriesActivity.this)
//                        .setTopColorRes(R.color.colorPrimary)
//                        .setTitle("افزودن دسته‌بندی")
//                        .setMessage("اطلاعات دسته‌بندی را وارد کنید")
//                        .setIcon(R.drawable.fab_add)
//                        .setInputFilter(R.string.err__empty__name, text -> text.matches("\\w+"))
//                        .setConfirmButton(android.R.string.ok, text -> toast(text))
//                        .show();

                View dialogView = getLayoutInflater().inflate(R.layout.dialog_category, null);

                edtName = dialogView.findViewById(R.id.edtName);
                edtDescription = dialogView.findViewById(R.id.edtDescription);
                inputName = dialogView.findViewById(R.id.inputName);
                inputDescription = dialogView.findViewById(R.id.inputDescription);

                final LovelyCustomDialog addCategoryDialog = new LovelyCustomDialog(CategoriesActivity.this)
                        .setView(dialogView)
                        .setTopColorRes(R.color.colorTransaction)
                        .setTitle("افزودن دسته‌بندی")
                        .setMessage("اطلاعات دسته‌بندی را وارد کنید")
                        .configureTitleView(textView->applyTextFont(textView))
                        .configureMessageView(textView->applyTextFont(textView))
                        .setIcon(R.drawable.ic_add_white_18dp)
                        .configureView(view -> applyTextFont(
                                view.findViewById(R.id.inputName),
                                view.findViewById(R.id.edtName),
                                view.findViewById(R.id.inputDescription),
                                view.findViewById(R.id.edtDescription),
                                view.findViewById(R.id.btnCancel),
                                view.findViewById(R.id.btnOk)
                        ));

                addCategoryDialog.setListener(R.id.btnOk, view -> validateForm(addCategoryDialog));
                addCategoryDialog.setListener(R.id.btnCancel, view -> addCategoryDialog.dismiss());
                addCategoryDialog.show();
            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("categories", categories);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    void addCategory(String name, String description, LovelyCustomDialog dialog) {

        CategoryServices service = API.getRetrofit().create(CategoryServices.class);
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        service.create(Auth.getAccessKey(this), category).enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
            @Override
            public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, Response<com.sorinaidea.ghaichi.models.Response> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    initCategories();
                    toast("دسته‌بندی با موفقیت افزوده شد!");
                }
            }

            @Override
            public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                if (t instanceof IOException) {
                    toast(R.string.err_unable_to_connect_to_server);
                }
                logVerbose("خطا در افزودن دسته‌بندی");

            }
        });
    }


    void updateCategory(Category category, LovelyCustomDialog dialog) {

        CategoryServices services = API.getRetrofit().create(CategoryServices.class);
        services.update(Auth.getAccessKey(this), category.getId(), category).enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
            @Override
            public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, Response<com.sorinaidea.ghaichi.models.Response> response) {
                if (response.isSuccessful()) {
                    toast("دسته‌بندی با موفقیت بروزرسانی شد.");
                    initCategories();
                } else {
                    toast("خطایی رخ داده است.");
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                if (t instanceof IOException) {
                    toast(R.string.err_unable_to_connect_to_server);
                }
                logVerbose(t.getMessage(), t);
                dialog.dismiss();
            }
        });
    }

    void deleteCategory(Category category, DialogInterface dialog) {

        CategoryServices services = API.getRetrofit().create(CategoryServices.class);
        services.delete(Auth.getAccessKey(this), category.getId()).enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
            @Override
            public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, Response<com.sorinaidea.ghaichi.models.Response> response) {
                if (response.isSuccessful()) {
                    toast("دسته‌بندی با موفقیت حذف شد.");
                    initCategories();
                } else {
                    toast("خطایی رخ داده است.");
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {
                if (t instanceof IOException) {
                    toast(R.string.err_unable_to_connect_to_server);
                }
                logVerbose(t.getMessage(), t);
                dialog.dismiss();
            }
        });
    }


    public ArrayList<Category> initCategories() {


        String authToken = Auth.getAccessKey(CategoriesActivity.this);


        if (authToken != null) {

            showProgressDialog(null, "در حال دریافت دسته‌بندی‌ها", false);

            CategoryServices categoryServices = API.getRetrofit().create(CategoryServices.class);

            categoryServices.categories(authToken).enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    if (response.isSuccessful()) {
                        categories.clear();
                        categories.addAll(response.body());
                        if (!categories.isEmpty()) {
                            adapter = new ServiceCategoryAdapter(categories, getApplicationContext(), onItemClickListener);
                            recServiceCategories.setAdapter(adapter);
                        } else {
                            recServiceCategories.setAdapter(new EmptyAdabper(CategoriesActivity.this));
                        }
                    } else if (response.code() == HttpCodes.HTTP_NOT_FOUND) {
                        recServiceCategories.setAdapter(new EmptyAdabper(CategoriesActivity.this));
                    }

                 }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {

                    if (t instanceof IOException) {
                        toast(R.string.err_unable_to_connect_to_server);
                    }

                    logVerbose(t.getMessage(), t);
                    hideProgressDialog();
                }
            });

        } else {
            Toast.makeText(CategoriesActivity.this, "دسته‌بندی یافت نشد.", Toast.LENGTH_SHORT).show();
        }

        return categories;
    }


}







/*

public class CategoriesActivity extends ToolbarActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

@Override
public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
    if (viewHolder instanceof ServiceCategoryAdapter.ViewHolder) {
        // get the removed item name to display it in snack bar
        String name = categories.get(viewHolder.getAdapterPosition()).getName();

        // backup of removed item for undo purpose
        final Category deletedItem = categories.get(viewHolder.getAdapterPosition());
        final int deletedIndex = viewHolder.getAdapterPosition();

        // remove the item from recycler view
        adapter.removeItem(viewHolder.getAdapterPosition());

        // showing snack bar with Undo option
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, String.format("%s %s %s", "دسته‌بندی ", name, " حذف شد."), Snackbar.LENGTH_LONG);

        snackbar.setAction("لغو", view -> {
            // undo is selected, restore the deleted item
            adapter.restoreItem(deletedItem, deletedIndex);
        });
        snackbar.setActionTextColor(Color.parseColor("#aeea00"));
        snackbar.show();
    }
}

*/