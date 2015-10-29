/*
 * Copyright (C) 2016 Cosmic-OS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cosmic.galaxy.advanced;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;

import com.android.internal.util.cosmic.cosmicUtils;
import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class SystemCategory extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String FLASHLIGHT_NOTIFICATION = "flashlight_notification";

    private SwitchPreference mFlashlightNotification;

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.GALAXY;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceScreen prefSet = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();
        addPreferencesFromResource(R.xml.cosmic_system);

        mFlashlightNotification = (SwitchPreference) findPreference(FLASHLIGHT_NOTIFICATION);
        mFlashlightNotification.setOnPreferenceChangeListener(this);
        if (!cosmicUtils.deviceSupportsFlashLight(getActivity())) {
            prefSet.removePreference(mFlashlightNotification);
        } else {
        mFlashlightNotification.setChecked((Settings.System.getInt(resolver,
                Settings.System.FLASHLIGHT_NOTIFICATION, 0) == 1));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if  (preference == mFlashlightNotification) {
            boolean checked = ((SwitchPreference)preference).isChecked();
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.FLASHLIGHT_NOTIFICATION, checked ? 1:0);
            return true;
        }

        return false;
    }
}
