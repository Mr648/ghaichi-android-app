package com.sorinaidea.arayeshgah.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.adapter.ServiceCategoryAdapter;
import com.sorinaidea.arayeshgah.model.ServiceCategory;

import java.util.ArrayList;

/**
 * Created by mr-code on 5/15/2018.
 */

public class AddServiceCategoryActivity extends AppCompatActivity {


    private RecyclerView recServiceCategories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_category);
        recServiceCategories = (RecyclerView) findViewById(R.id.recServiceCategories);
        recServiceCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recServiceCategories.setAdapter(new ServiceCategoryAdapter(initCategories(), getApplicationContext()));
    }

    public ArrayList<ServiceCategory> initCategories() {
        ArrayList<ServiceCategory> categories = new ArrayList<>();
        for (int i = 1; i <= 20; i++) categories.add(new ServiceCategory().setName("دسته " + i));
        return categories;
    }
}
