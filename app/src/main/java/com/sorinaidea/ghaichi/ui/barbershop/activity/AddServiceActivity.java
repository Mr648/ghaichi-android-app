package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.Barber;
import com.sorinaidea.ghaichi.models.Category;
import com.sorinaidea.ghaichi.models.Service;
import com.sorinaidea.ghaichi.ui.ToolbarActivity;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.BarberServices;
import com.sorinaidea.ghaichi.webservice.barbershop.CategoryServices;
import com.sorinaidea.ghaichi.webservice.barbershop.ServiceServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceActivity extends ToolbarActivity {

    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutPrice;
    private TextInputLayout inputLayoutDuration;
    private TextInputLayout inputLayoutDiscount;
    private TextInputLayout inputLayoutDescription;

    private TextInputEditText txtName;
    private TextInputEditText txtPrice;
    private TextInputEditText txtDuration;
    private TextInputEditText txtDiscount;
    private TextInputEditText txtDescription;

    private AppCompatButton btnSelectBarber;
    private AppCompatButton btnSelectCategory;

    List<Category> categories;
    List<Barber> barbers;
    List<Barber> selectedBarbers;

    private int selectedCategory = -1;


    public static final int ADD_SERVICE_REQUEST = 0x3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        initToolbar(R.string.toolbar_add_service, true, false);
        setupInputs();
        getCategoriesAndBarbers();
        setFonts();

    }

    private void getCategoriesAndBarbers() {
        CategoryServices categoriesService = API.getRetrofit().create(CategoryServices.class);
        categoriesService.categories(Auth.getAccessKey(AddServiceActivity.this)).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categories = response.body();
                    addClickListenerToCategory();
                } else {
                    categories = new ArrayList<>();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                if (t instanceof IOException) {
                    toast(R.string.err_unable_to_connect_to_server);
                }
                categories = new ArrayList<>();
            }
        });

        BarberServices barbersService = API.getRetrofit().create(BarberServices.class);
        barbersService.barbers(Auth.getAccessKey(AddServiceActivity.this)).enqueue(new Callback<List<Barber>>() {
            @Override
            public void onResponse(Call<List<Barber>> call, Response<List<Barber>> response) {
                if (response.isSuccessful()) {
                    barbers = response.body();
                    addClickListenerToBarbers();
                } else {
                    barbers = new ArrayList<>();
                }
            }

            @Override
            public void onFailure(Call<List<Barber>> call, Throwable t) {
                if (t instanceof IOException) {
                    toast(R.string.err_unable_to_connect_to_server);
                }
                barbers = new ArrayList<>();
            }
        });

    }


    private void addClickListenerToCategory() {
        btnSelectCategory.setOnClickListener(new View.OnClickListener() {
            private int choice = -1;

            private CharSequence[] getCategories() {
                CharSequence[] names = new CharSequence[categories.size()];
                int index = 0;
                for (Category c :
                        categories) {
                    names[index++] = c.getName();
                }
                return names;
            }

            @Override
            public void onClick(View v) {

                AlertDialog selectCategoryDialog = new AlertDialog.Builder(AddServiceActivity.this)
                        .setTitle(R.string.action_single_category)
                        .setSingleChoiceItems(getCategories(), choice, (dialog, which) -> selectedCategory = which)
                        .setPositiveButton("انتخاب", (dialog, which) -> {
                            if (selectedCategory != -1)
                                btnSelectCategory.setText(String.format("%s %s", "دسته‌بندی: ", categories.get(selectedCategory).getName()));
                            dialog.dismiss();
                        }).create();

                selectCategoryDialog.show();
            }
        });
    }

    private void addClickListenerToBarbers() {
        selectedBarbers = new ArrayList<>();
        btnSelectBarber.setOnClickListener(new View.OnClickListener() {

            private CharSequence[] names = new CharSequence[barbers.size()];
            private boolean[] selects = new boolean[barbers.size()];

            private CharSequence[] getBarbers() {
                int index = 0;
                for (Barber c :
                        barbers) {
                    names[index++] = c.getName();
                }
                return names;
            }

            private ArrayList<CharSequence> getSelectedBarbers() {
                selectedBarbers.clear();
                ArrayList<CharSequence> list = new ArrayList<>();
                for (int i = 0; i < selects.length; i++) {
                    if (selects[i]) {
                        list.add(names[i]);
                        selectedBarbers.add(barbers.get(i));
                    }
                }
                return list;
            }


            @Override
            public void onClick(View v) {
                AlertDialog selectBarbersDialog = new
                        AlertDialog.Builder(AddServiceActivity.this)
                        .setIcon(R.drawable.ic_group_add_black_24dp)
                        .setTitle(R.string.action_choose_barbers)
                        .setMultiChoiceItems(
                                getBarbers()
                                , selects
                                , (dialog, which, isChecked) -> {
                                    selects[which] = isChecked;
                                }
                        )
                        .setPositiveButton("انتخاب", (dialog, which) -> {
                            btnSelectBarber.setText(String.format("%s %s", "آرایشگران: ", Arrays.toString(getSelectedBarbers().toArray())));
                            dialog.dismiss();
                        }).create();
                selectBarbersDialog.show();
            }
        });
    }


    private void setupInputs() {

        btnSelectBarber = findViewById(R.id.btnSelectBarber);
        btnSelectCategory = findViewById(R.id.btnSelectCategory);

        inputLayoutName = findViewById(R.id.inputLayoutName);
        inputLayoutPrice = findViewById(R.id.inputLayoutPrice);
        inputLayoutDuration = findViewById(R.id.inputLayoutDuration);
        inputLayoutDiscount = findViewById(R.id.inputLayoutDiscount);
        inputLayoutDescription = findViewById(R.id.inputLayoutDescription);

        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDuration = findViewById(R.id.txtDuration);
        txtDiscount = findViewById(R.id.txtDiscount);
        txtDescription = findViewById(R.id.txtDescription);
    }


    private void setFonts() {
        applyTextFont(
                inputLayoutName,
                inputLayoutPrice,
                inputLayoutDuration,
                inputLayoutDiscount,
                inputLayoutDescription,
                txtName,
                txtPrice,
                txtDuration,
                txtDiscount,
                txtDescription,
                btnSelectCategory,
                btnSelectBarber
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_save, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_save:
                submitForm();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void addService(Map<String, String> map) {
        ServiceServices serviceServices = API.getRetrofit().create(ServiceServices.class);
        serviceServices.create(Auth.getAccessKey(AddServiceActivity.this), map).enqueue(new Callback<com.sorinaidea.ghaichi.models.Response>() {
            @Override
            public void onResponse(Call<com.sorinaidea.ghaichi.models.Response> call, Response<com.sorinaidea.ghaichi.models.Response> response) {
                if (response.isSuccessful()) {
                    try {
                        Objects.requireNonNull(response.body());
                        toast(response.body().getMessage());
                    } catch (NullPointerException ex) {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<com.sorinaidea.ghaichi.models.Response> call, Throwable t) {

            }
        });
    }

    private void submitForm() {

        if (!validateName()) {
            return;
        }
        if (!validatePrice()) {
            return;
        }
        if (!validateDuration()) {
            return;
        }
        if (!validateDescription()) {
            return;
        }
        if (!validateCategory()) {
            return;
        }
        if (!validateBarbers()) {
            return;
        }

        Service service = new Service();

        service.setName(txtName.getText().toString());
        service.setPrice(txtPrice.getText().toString());
        service.setDescription(txtDescription.getText().toString());
        service.setTime(txtDuration.getText().toString());
        service.setDescription(txtDescription.getText().toString());

        if (txtDiscount.getText().toString().trim().isEmpty()) {
            service.setDiscount("0");
        } else {
            service.setDiscount(txtDiscount.getText().toString());
        }

        service.setCategory(categories.get(selectedCategory));
        service.setBarbers(selectedBarbers);
        Map<String, String> map = new HashMap<>();
        map.put("category_id", String.valueOf(categories.get(selectedCategory).getId()));
        map.put("name", txtName.getText().toString());
        map.put("price", txtPrice.getText().toString());
        map.put("description", txtDescription.getText().toString());
        map.put("time", txtDuration.getText().toString());
        map.put("barbers", getSelectedBarbershops());
        addService(map);
    }

    private String getSelectedBarbershops() {
        String str = "";
        for (Barber b :
                selectedBarbers) {
            str = str.concat(String.valueOf(b.getId()) + ",");
        }
        return str;
    }

    private boolean validateName() {
        if (txtName.getText().  toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err__empty__name));
            requestFocus(txtName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePrice() {
        if (txtPrice.getText().toString().trim().isEmpty()) {
            inputLayoutPrice.setError(getString(R.string.err__empty__price));
            requestFocus(txtPrice);
            return false;
        } else {
            inputLayoutPrice.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateDuration() {
        if (txtDuration.getText().toString().trim().isEmpty()) {
            inputLayoutDuration.setError(getString(R.string.err__empty__name));
            requestFocus(txtDuration);
            return false;
        } else {
            inputLayoutDuration.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCategory() {
        if (selectedCategory < 0) {
            btnSelectCategory.setTextColor(getResources().getColor(R.color.colorRed));
            btnSelectCategory.setText("ابتدا دسته‌بندی را مشخص کنید.");
            return false;
        } else {
            btnSelectCategory.setTextColor(getResources().getColor(R.color.colorBlack));
        }
        return true;
    }


    private boolean validateBarbers() {
        if (selectedBarbers == null || selectedBarbers.isEmpty()) {
            btnSelectBarber.setTextColor(getResources().getColor(R.color.colorRed));
            btnSelectBarber.setText("ابتدا آرایشگر(ان) را انتخاب کنید.");
            return false;
        } else {
            btnSelectBarber.setTextColor(getResources().getColor(R.color.colorBlack));
        }
        return true;
    }


    private boolean validateDescription() {
        if (txtDescription.getText().toString().trim().isEmpty()) {
            inputLayoutDescription.setError(getString(R.string.err__empty__name));
            requestFocus(txtDescription);
            return false;
        } else {
            inputLayoutDescription.setErrorEnabled(false);
        }
        return true;
    }


}
