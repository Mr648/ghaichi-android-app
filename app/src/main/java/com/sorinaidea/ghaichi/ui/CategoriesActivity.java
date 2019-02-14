package com.sorinaidea.ghaichi.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.EmptyAdabper;
import com.sorinaidea.ghaichi.adapter.ServiceCategoryAdapter;
import com.sorinaidea.ghaichi.model.ServiceCategory;
import com.sorinaidea.ghaichi.models.Category;
import com.sorinaidea.ghaichi.ui.helper.RecyclerItemTouchHelper;
import com.sorinaidea.ghaichi.util.GhaichiPrefrenceManager;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.HttpCodes;
import com.sorinaidea.ghaichi.webservice.barbershop.CategoryServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mr-code on 5/15/2018.
 */

public class CategoriesActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {


    private RecyclerView recServiceCategories;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private ServiceCategoryAdapter adapter;
    private ArrayList<Category> categories;

    private ProgressDialog prgLoad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_category);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("دسته‌بندی‌ها");


        categories = new ArrayList<>();
        recServiceCategories = (RecyclerView) findViewById(R.id.recServiceCategories);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);


        recServiceCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        prgLoad = new ProgressDialog(CategoriesActivity.this);
        prgLoad.setTitle("بارگزاری");
        prgLoad.setMessage("در حال بارگزاری دسته‌بندی‌ها");
        prgLoad.setCancelable(false);


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recServiceCategories);


        if (savedInstanceState == null || !savedInstanceState.containsKey("categories")) {
            initCategories();
        } else {
            categories = savedInstanceState.getParcelableArrayList("categories");
        }

    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
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
                    .make(coordinatorLayout, String.format("%s %s %s", "دسته‌بندی ", name , " حذف شد."), Snackbar.LENGTH_LONG);

            snackbar.setAction("لغو", view -> {
                // undo is selected, restore the deleted item
                adapter.restoreItem(deletedItem, deletedIndex);
            });
            snackbar.setActionTextColor(Color.parseColor("#aeea00"));
            snackbar.show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Category> initCategories() {


        String authToken = Util.getAccessKey(CategoriesActivity.this);


        if (authToken != null) {

            prgLoad.show();

            CategoryServices categoryServices = API.getRetrofit().create(CategoryServices.class);

            categoryServices.categories(authToken).enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    if (response.isSuccessful()) {
                        categories.addAll(response.body());
                        if (!categories.isEmpty()) {
                            adapter = new ServiceCategoryAdapter(categories, getApplicationContext(), CategoriesActivity.this);
                            for (Category c :
                                    categories) {
                                Log.d("CATEGORY", c.getName() + c.getDescription());
                            }
                            recServiceCategories.setAdapter(adapter);
                        } else {
                            recServiceCategories.setAdapter(new EmptyAdabper(CategoriesActivity.this));
                        }
                        Log.d("GHAICHIAPP", "categories : " + categories.size());
                    } else if (response.code() == HttpCodes.HTTP_NOT_FOUND) {
                        Toast.makeText(CategoriesActivity.this, "دسته‌بندی یافت نشد.", Toast.LENGTH_SHORT).show();
                    }
                    if (prgLoad != null && prgLoad.isShowing()) {
                        prgLoad.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    Log.d("GHAICHIAPP", "Failed" + t.getMessage() + " : " + call.isExecuted());
                    if (prgLoad != null && prgLoad.isShowing()) {
                        prgLoad.dismiss();
                    }
                }
            });

        } else {
            Toast.makeText(CategoriesActivity.this, "دسته‌بندی یافت نشد.", Toast.LENGTH_SHORT).show();
        }


        return categories;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("categories", categories);
        super.onSaveInstanceState(outState);
    }
}
