package com.ormoyo.timiss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.ormoyo.timiss.AppUseTimeService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent i) {
        if(AppUseTimeService.isServiceRunning) {
            AppUseTimeService service = (AppUseTimeService) context;
            service.counts.clear();
        }
        resetTimeLeft(context);
    }

    private void resetTimeLeft(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("ACCOUNT_PROFILES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Map<String, ?> entries = preferences.getAll();
        for(Map.Entry<String, ?> entry : entries.entrySet()) {
            Set<String> profiles = (Set<String>) entry.getValue();
            Set<String> newSet = new HashSet<>();

            for(String profile : profiles) {
                String[] arr = profile.split(" ", 2);
                newSet.add(arr[0] + ' ' + arr[1] + ' ' + arr[1]);
            }

            editor.putStringSet(entry.getKey(), newSet);
        }

        editor.apply();
    }
}
