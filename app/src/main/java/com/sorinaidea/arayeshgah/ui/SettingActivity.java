package com.sorinaidea.arayeshgah.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import com.sorinaidea.arayeshgah.R;


/**
 * Created by mr-code on 5/15/2018.
 */

public class SettingActivity extends PreferenceActivity {
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.app_setting);
    }
}
