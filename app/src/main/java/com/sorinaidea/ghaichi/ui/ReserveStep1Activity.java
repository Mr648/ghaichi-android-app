package com.sorinaidea.ghaichi.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.sorinaidea.ghaichi.App;
import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.ReservationServiceSelectionAdapter;
import com.sorinaidea.ghaichi.auth.Auth;
import com.sorinaidea.ghaichi.models.ServiceMoreInfo;
import com.sorinaidea.ghaichi.util.Util;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mr-code on 6/17/2018.
 */

public class ReserveStep1Activity extends ToolbarActivity {

    int barbershopId;

    View parentLayout;

    TextView txtPrice;
    CardView cardPrice;
    AppCompatButton btnDate;
    AppCompatButton btnTimeStart;
    AppCompatButton btnTimeEnd;
    RecyclerView recServices;
    PriceUpdater priceUpdater;

    JalaliDate selectedDate = null;
    Time selectedStartTime = null;
    Time selectedEndTime = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_service_step_1);
        initToolbar("رزرو خدمات", true, false);

        txtPrice = findViewById(R.id.txtPrice);

        btnDate = findViewById(R.id.btnDate);
        cardPrice = findViewById(R.id.cardPrice);
        btnTimeStart = findViewById(R.id.btnTimeStart);
        btnTimeEnd = findViewById(R.id.btnTimeEnd);


        btnDate.setOnClickListener((view) -> {

            Calendar now = Calendar.getInstance();
            Calendar next2weeks = Calendar.getInstance();
            next2weeks.add(Calendar.DAY_OF_MONTH, 14);

            new DatePicker.Builder()
                    .id(20)
                    .minDate(now)
                    .maxDate(next2weeks)
                    .date(Calendar.getInstance())
                    .build(
                            (id, calendar, day, month, year) -> {
                                btnDate.setText(
                                        String.format(
                                                App.LOCALE,
                                                "%s %d/%d/%d",
                                                "در تاریخ", year, month, day
                                        )
                                );
                                selectedDate = new JalaliDate(year, month, day);
                            }
                    )
                    .show(getSupportFragmentManager(), "");
        });

        btnTimeStart.setOnClickListener((view) -> {
            showTimePicker(R.id.btnTimeStart);
        });

        btnTimeEnd.setOnClickListener((view) -> {
            showTimePicker(R.id.btnTimeEnd);
        });


        recServices = findViewById(R.id.recBanners);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        parentLayout = findViewById(android.R.id.content);


        //instantiate your adapter with the list of genres

        recServices.setLayoutManager(layoutManager);

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            barbershopId = Integer.parseInt(extras.getString("BARBERSHOP_ID"));
            getServices(barbershopId);
        } else {
            finish();
        }

        applyTextFont(
                txtPrice,
                btnDate,
                btnTimeEnd,
                btnTimeStart
        );

    }

    private void showTimePicker(@IdRes int btn) {
        Calendar now = Calendar.getInstance();
        int mHour = now.get(Calendar.HOUR_OF_DAY);
        int mMinute = now.get(Calendar.MINUTE);

        switch (btn) {
            case R.id.btnTimeStart:
                if (selectedEndTime != null) {
                    selectedEndTime = null;
                    btnTimeEnd.setText("الی ساعت");
                }
                if (selectedStartTime != null) {
                    mHour = selectedStartTime.hour;
                    mMinute = selectedStartTime.minute;
                }

                break;
            case R.id.btnTimeEnd:
                if (selectedStartTime == null) {
                    toast("اول ابتدای بازه زمانی را تعیین نمایید.");
                    return;
                }
                if (priceUpdater.getSelectedServices().isEmpty()) {
                    toast("ابتدا خدمات مورد نظر خود را انتخاب کنید.");
                    return;
                }
                if (selectedEndTime != null) {
                    mHour = selectedEndTime.hour;
                    mMinute = selectedEndTime.minute;
                }
                break;
        }

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_timepicker, null);
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(mHour);
            timePicker.setMinute(mMinute);
        } else {
            timePicker.setCurrentHour(mHour);
            timePicker.setCurrentMinute(mMinute);
        }


        TimePicker.OnTimeChangedListener timeChangedListener = (view, selectedHour, selectedMinute) -> {
            String newTime = String.format(App.LOCALE
                    , "%s:%s"
                    , String.format(App.LOCALE, "%02d", selectedHour)
                    , String.format(App.LOCALE, "%02d", selectedMinute)
            );
            switch (btn) {
                case R.id.btnTimeStart:
                    btnTimeStart.setText(String.format(App.LOCALE, "%s %s", "از ساعت", newTime));
                    selectedStartTime = new Time(selectedHour, selectedMinute);

                    break;
                case R.id.btnTimeEnd:
                    if (selectedHour <= selectedStartTime.hour && selectedMinute <= selectedStartTime.minute) {
                        toast("انتهای بازه زمانی باید پس از ابتدای آن باشد.");
                        return;
                    } else {
                        if (!priceUpdater.getSelectedServices().isEmpty()) {
                            int time = 0;
                            for (ServiceMoreInfo service : priceUpdater.getSelectedServices()) {
                                time += service.getTime();
                            }
                            if ((((selectedHour - selectedStartTime.hour) * 60) + (selectedMinute - selectedStartTime.minute)) < time) {
                                toast(String.format(App.LOCALE, "%s %02d %s.", "بازه منتخب باید حداقل", time, "دقیقه باشد."));
                                return;
                            }
                        }
                    }

                    selectedEndTime = new Time(selectedHour, selectedMinute);
                    btnTimeEnd.setText(String.format(App.LOCALE, "%s %s", "الی ساعت", newTime));
                    break;
            }

        };

        timePicker.setOnTimeChangedListener(timeChangedListener);

        final LovelyCustomDialog addCommentDialog = new LovelyCustomDialog(this)
                .setView(dialogView)
                .setTopColorRes(R.color.colorAmberAccent200)
                .setTitle("انتخاب وقت")
                .setCancelable(false)
                .setMessage(btn == R.id.btnTimeEnd ? "ابتدای بازه زمانی را تعیین کنید." : "انتهای بازه زمانی را تعیین کنید.")
                .setIcon(R.drawable.ic_access_time_black_18dp)
                .configureTitleView(this::applyTextFont)
                .configureMessageView(this::applyTextFont)
                .configureView(view -> applyTextFont(
                        view.findViewById(R.id.btnOk)
                ));

        addCommentDialog.setListener(R.id.btnOk, view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timeChangedListener.onTimeChanged(timePicker, timePicker.getHour(), timePicker.getMinute());
            } else {
                timeChangedListener.onTimeChanged(timePicker, timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            }
            addCommentDialog.dismiss();
        });
        addCommentDialog.show();
    }

    private void initPriceUpdater() {
        priceUpdater = new PriceUpdater(txtPrice) {
            @Override
            public void add(ServiceMoreInfo service) {
                runOnUiThread(() -> {
                    super.add(service);
                    if (!selectedServices.isEmpty()) {
                        cardPrice.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void delete(ServiceMoreInfo service) {
                super.delete(service);
                if (selectedServices.isEmpty()) {
                    cardPrice.setVisibility(View.GONE);
                    if (selectedEndTime != null) {
                        selectedEndTime = null;
                        btnTimeEnd.setText("الی ساعت");
                    }
                }
            }
        };
    }

    private void getServices(int barbershopId) {
        showProgress();
        API.getRetrofit()
                .create(BarbershopServices.class)
                .services(Auth.getAccessKey(getApplicationContext()), barbershopId)
                .enqueue(new Callback<List<com.sorinaidea.ghaichi.models.ServiceCategory>>() {
                    @Override
                    public void onResponse(Call<List<com.sorinaidea.ghaichi.models.ServiceCategory>> call, Response<List<com.sorinaidea.ghaichi.models.ServiceCategory>> response) {
                        hideProgress();
                        if (response.isSuccessful()) {
                            try {
                                initPriceUpdater();
                                recServices.setAdapter(new ReservationServiceSelectionAdapter(Objects.requireNonNull(response.body()), getApplicationContext(), priceUpdater));
                            } catch (NullPointerException ex) {
                                toast(R.string.err_unable_to_connect_to_server);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<com.sorinaidea.ghaichi.models.ServiceCategory>> call, Throwable t) {
                        hideProgress();
                        if (t instanceof IOException) {
                            toast(R.string.err_unable_to_connect_to_server);
                        }
                    }
                });

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_reserve) {
            if (validateForm()) {
                toast("reserve~~");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private MenuItem btnReserve;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_reserve, menu);
        btnReserve = menu.findItem(R.id.action_reserve);
        changeReserveState(false);
        return true;
    }

    private boolean validateForm() {
        changeReserveState(false);

        try {
            if (Objects.requireNonNull(priceUpdater).getSelectedServices().isEmpty()) {
                toast("ابتدا خدمات مورد نظر خود را انتخاب کنید.");
                return false;
            }
        } catch (NullPointerException ex) {
            changeReserveState(false);
            toast("ابتدا خدمات مورد نظر خود را انتخاب کنید.");
            return false;
        }

        try {
            if (Objects.requireNonNull(selectedDate).toString().isEmpty()
                    || !Objects.requireNonNull(selectedDate).toString().matches(Util.CONSTANTS.REGEX_JALALI_DATE)) {
                toast("لطفا روز را انتخاب کنید.");
                return false;
            }
        } catch (NullPointerException ex) {
            changeReserveState(false);
            toast("لطفا روز را انتخاب کنید.");
            return false;
        }

        try {
            if (Objects.requireNonNull(selectedStartTime).toString().isEmpty()
                    || !Objects.requireNonNull(selectedStartTime).toString().matches(Util.CONSTANTS.REGEX_TIME)) {
                toast("لطفا شروع بازه زمانی را انتخاب کنید.");
                return false;
            }
        } catch (NullPointerException ex) {
            changeReserveState(false);
            toast("لطفا شروع بازه زمانی را انتخاب کنید.");
            return false;
        }

        try {
            if (Objects.requireNonNull(selectedEndTime).toString().isEmpty()
                    || !Objects.requireNonNull(selectedEndTime).toString().matches(Util.CONSTANTS.REGEX_TIME)) {
                toast("لطفا پایان بازه زمانی را انتخاب کنید.");
                return false;
            }
        } catch (NullPointerException ex) {
            changeReserveState(false);
            toast("لطفا پایان بازه زمانی را انتخاب کنید.");
            return false;
        }

        changeReserveState(true);
        return true;
    }

    private void changeReserveState(boolean state) {
        if (state) {
            @DrawableRes int icon = R.drawable.ic_done_white_24dp;
            btnReserve.setIcon(icon);
        } else {
            @DrawableRes int icon = R.drawable.ic_done_gray_24dp;
            btnReserve.setIcon(icon);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    class JalaliDate {
        int year, month, day;

        public JalaliDate(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof JalaliDate) {
                JalaliDate other = (JalaliDate) obj;
                return this.year == other.year && this.month == other.month && this.day == other.day;
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format(Locale.ENGLISH, "%04d/%02d/%02d", year, month, day);
        }
    }

    class Time {
        int hour, minute;

        public Time(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Time) {
                Time other = (Time) obj;
                return this.hour == other.hour && this.minute == other.minute;
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format(Locale.ENGLISH, "%02d:%02d", hour, minute);
        }
    }

}



/*
*= new ServiceSelectionAdapter(serviceLists, new PriceUpdater(txtPrice) {
                                    @Override
                                    public void add(Service service) {
                                        runOnUiThread(() -> {
                                            super.add(service);
                                            if (!selectedServices.isEmpty()) {
                                                btnReserve.setEnabled(true);
                                                Log.i("ADD_TAG_SERVICE", "SERVICE " + serviceLists.size());
                                            }
                                        });
                                    }

                                    @Override
                                    public void delete(Service service) {
                                        super.delete(service);
                                        if (selectedServices.isEmpty()) {
                                            btnReserve.setEnabled(false);
                                        }
                                        Log.i("DELETE_TAG_SERVICE", "SERVICE " + serviceLists.size());
                                    }
                                });
* */