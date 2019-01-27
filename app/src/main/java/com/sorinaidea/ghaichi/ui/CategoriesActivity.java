package com.sorinaidea.ghaichi.ui;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.ServiceCategoryAdapter;
import com.sorinaidea.ghaichi.model.ServiceCategory;
import com.sorinaidea.ghaichi.ui.helper.RecyclerItemTouchHelper;

import java.util.ArrayList;

/**
 * Created by mr-code on 5/15/2018.
 */

public class CategoriesActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {


    private RecyclerView recServiceCategories;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private ServiceCategoryAdapter adapter;
    private ArrayList<ServiceCategory> categories;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_category);
        recServiceCategories = (RecyclerView) findViewById(R.id.recServiceCategories);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("دسته‌بندی‌ها");


        recServiceCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        categories = initCategories();
        adapter = new ServiceCategoryAdapter(categories, getApplicationContext(), this);
        recServiceCategories.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recServiceCategories);

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
            final ServiceCategory deletedItem = categories.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " حذف شد!", Snackbar.LENGTH_LONG);
            snackbar.setAction("لغو", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
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

    public ArrayList<ServiceCategory> initCategories() {
        ArrayList<ServiceCategory> categories = new ArrayList<>();
        for (int i = 1; i <= 20; i++) categories.add(new ServiceCategory().setName("دسته " + i));
        return categories;
    }
}
