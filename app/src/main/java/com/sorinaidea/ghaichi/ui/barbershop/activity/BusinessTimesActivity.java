package com.sorinaidea.ghaichi.ui.barbershop.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.barbershop.BusinessTimeAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.BusinessTime;
import com.sorinaidea.ghaichi.models.Response;
import com.sorinaidea.ghaichi.ui.ToolbarActivity;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.barbershop.BarbershopProfileServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class BusinessTimesActivity extends ToolbarActivity {

    RecyclerView recTimes;


    private ArrayList<BusinessTime> businessTimes;
    private BusinessTimeAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_times);
        initToolbar(R.string.toolbar_business_times, true, false);
        recTimes = findViewById(R.id.recTimes);
        recTimes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recTimes.setNestedScrollingEnabled(false);
        businessTimes = new ArrayList<>();
        fetchBusinessTimes();
    }

    private void fetchBusinessTimes() {
        BarbershopProfileServices barbershopProfileServices = API.getRetrofit().create(BarbershopProfileServices.class);
        barbershopProfileServices.businessTimes(Auth.getAccessKey(this)).enqueue(new Callback<List<BusinessTime>>() {
            @Override
            public void onResponse(Call<List<BusinessTime>> call, retrofit2.Response<List<BusinessTime>> response) {
                if (response.isSuccessful()) {
                    try {
                        Objects.requireNonNull(response.body());
                        businessTimes.clear();
                        businessTimes.addAll(response.body());
                        if (adapter == null) {
                            adapter = new BusinessTimeAdapter(businessTimes, getApplicationContext()) {

                                String format(int number) {
                                    return String.format(App.LOCALE_EN, "%02d", number);
                                }

                                @Override
                                public void selectTime(AppCompatButton editText, int position, BusinessTime.FIELD field) {
                                    Calendar now = Calendar.getInstance();
                                    int hour = now.get(Calendar.HOUR_OF_DAY);
                                    int minute = now.get(Calendar.MINUTE);

                                    TimePickerDialog mTimePicker;
                                    mTimePicker = new TimePickerDialog(
                                            BusinessTimesActivity.this,
                                            (timePicker, selectedHour, selectedMinute) -> {
                                                String newTime = String.format(App.LOCALE_EN
                                                        , "%s:%s"
                                                        , format(selectedHour)
                                                        , format(selectedMinute)
                                                );
                                                switch (field) {
                                                    case MOT:
                                                        businessTimes.get(position).setMorningOpenTime(newTime);
                                                        break;
                                                    case MCT:
                                                        businessTimes.get(position).setMorningCloseTime(newTime);
                                                        break;
                                                    case EOT:
                                                        businessTimes.get(position).setEveningOpenTime(newTime);
                                                        break;
                                                    case ECT:
                                                        businessTimes.get(position).setEveningCloseTime(newTime);
                                                        break;
                                                }
                                                editText.setText(newTime);
                                            },
                                            hour,
                                            minute,
                                            true
                                    );
                                    mTimePicker.setCanceledOnTouchOutside(true);
                                    mTimePicker.setTitle("انتخاب ساعت");
                                    mTimePicker.show();
                                }


                            };
                            recTimes.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    } catch (NullPointerException ex) {
                    }
                } else {

                    toast("خطا در پاسخ سرور");
                }
            }

            @Override
            public void onFailure(Call<List<BusinessTime>> call, Throwable t) {
                toast("خطا کلی");
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_save:
                updateTimes();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_save, menu);
        return true;
    }

    private void updateTimes() {
        Map<String, JsonObject> times = new HashMap<>();
        for (BusinessTime time :
                businessTimes) {
            times.put(time.getDay(), time.toJsonObject());
        }

        BarbershopProfileServices barbershopProfileServices = API.getRetrofit().create(BarbershopProfileServices.class);
        barbershopProfileServices.updateBusinessTimes(Auth.getAccessKey(this), times)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            try {
                                toast(Objects.requireNonNull(response.body()).getMessage());
                            } catch (NullPointerException ignore) {
                            }
                        } else {
                            toast("خطایی رخ داده است.");
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        if (t instanceof IOException) {
                            toast("خطا در برقراری ارتباط با سرور");
                        } else {
                            toast("خطایی رخ داده است.");
                        }
                    }
                });
    }
}
