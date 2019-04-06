//package com.sorinaidea.ghaichi.ui;
//
//import android.os.Bundle;
//import android.support.annotation.DrawableRes;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.sorinaidea.ghaichi.R;
//import com.sorinaidea.ghaichi.fast.Category;
//import com.sorinaidea.ghaichi.model.Service;
//import com.sorinaidea.ghaichi.model.ServiceList;
//import com.sorinaidea.ghaichi.ui.dialog.TransactionDialog;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by mr-code on 6/17/2018.
// */
//
//public class ReserveActivity extends AppCompatActivity {
//
//    private Toolbar toolbar;
//    private TextView txtPrice;
//    private RelativeLayout relativeLayout;
//    View parentLayout;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reserve_service);
//
//
//
//
//        toolbar = findViewById(R.id.toolbar);
//        txtPrice = findViewById(R.id.txtPrice);
//        relativeLayout = findViewById(R.id.relativeLayout);
//
//        relativeLayout.animate().alpha(0.0f).setDuration(1).start();
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setTitle("رزرو خدمات");
//
//
//        recServices = findViewById(R.id.recBanners);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        parentLayout = findViewById(android.R.id.content);
//
//
//        //instantiate your adapter with the list of genres
//
//        recServices.setLayoutManager(layoutManager);
//
//        Bundle extras = getIntent().getExtras();
//        if (extras != null && !extras.isEmpty()) {
//            barbershopId = Integer.parseInt(extras.getString("BARBERSHOP_ID"));
////            getServices(barbershopId);
//        } else {
//            finish();
//        }
//
//
//    }
//
//    private int barbershopId;
//
//    private RecyclerView recServices;
//
////    private void getServices(int barbershopId) {
////        Retrofit retrofit = API.getRetrofit();
////
////        BarbershopServices service = retrofit.create(BarbershopServices.class);
////
////        Call<List<Category>> commentsCall = service.services(barbershopId, Auth.getAccessKey(getApplicationContext()));
////
////        commentsCall.enqueue(new Callback<List<Category>>() {
////            @Override
////            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
////                if (response.body() != null) {
////                    if (!response.body().isEmpty()) {
////
////                        List<ServiceList> serviceLists = serviceLists(response.body());
////
////
////                        ServiceSelectionAdapter adapter = new ServiceSelectionAdapter(serviceLists, new PriceUpdater(txtPrice) {
////                            @Override
////                            public void add(Service service) {
////                                runOnUiThread(() -> {
////                                    super.add(service);
////                                    if (!selectedServices.isEmpty()) {
////                                        relativeLayout.animate().alpha(1.0f).setDuration(500).start();
////                                        btnReserve.setEnabled(true);
////                                        Log.i("ADD_TAG_SERVICE", "SERVICE " + serviceLists.size());
////                                    }
////                                });
////                            }
////
////                            @Override
////                            public void delete(Service service) {
////                                super.delete(service);
////                                if (selectedServices.isEmpty()) {
////                                    relativeLayout.animate().alpha(0.0f).setDuration(500).start();
////                                    btnReserve.setEnabled(false);
////                                }
////                                Log.i("DELETE_TAG_SERVICE", "SERVICE " + serviceLists.size());
////                            }
////                        });
////                        recServices.setAdapter(adapter);
////                    } else {
////                        recServices.setAdapter(new EmptyAdabper(getApplicationContext()));
////                        recServices.setNestedScrollingEnabled(false);
////                    }
////                }
////            }
////
////            @Override
////            public void onFailure(Call<List<Category>> call, Throwable t) {
////
////            }
////        });
////
////    }
//
//    private List<ServiceList> serviceLists(List<Category> categories) {
//
//        ArrayList<ServiceList> list = new ArrayList<>();
//
//
//        for (Category category :
//                categories) {
//            ArrayList<Service> listServices = new ArrayList<>();
//            for (com.sorinaidea.ghaichi.fast.Service service :
//                    category.getServices()) {
//                Service sss = new Service(service.getName(),Float.parseFloat(service.getPrice()));
//                listServices.add(sss);
//            }
//            list.add(new ServiceList(category.getName(), listServices));
//        }
//
//        return list;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            onBackPressed();
//            return true;
//        } else if (id == R.id.action_reserve) {
//            // TODO Show Reserve Dialog
//            TransactionDialog dialog = new TransactionDialog(ReserveActivity.this);
//            dialog.show();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private MenuItem btnReserve;
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_reserve, menu);
//        btnReserve = menu.findItem(R.id.action_reserve);
//
//        return true;
//    }
//
//    private void changeReserveState(boolean state) {
//        if (state) {
//            @DrawableRes int icon = R.drawable.ic_done_white_24dp;
//            btnReserve.setIcon(icon);
//        } else {
//            @DrawableRes int icon = R.drawable.ic_done_gray_24dp;
//            btnReserve.setIcon(icon);
//        }
//        btnReserve.setEnabled(state);
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//
//}
