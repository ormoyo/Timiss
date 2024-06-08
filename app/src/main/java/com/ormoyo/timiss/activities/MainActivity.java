package com.ormoyo.timiss.activities;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ormoyo.timiss.R;
import com.ormoyo.timiss.Timiss;
import com.ormoyo.timiss.ui.fragments.AskUsagePermissionFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timiss application = Timiss.getInstance();

        setContentView(R.layout.activity_main);
        findViewById(R.id.calendarBtn).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.todoBtn).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TodoListActivity.class);
            startActivity(intent);
        });

        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        int day = preferences.getInt("day", -1);

        if (day != currentDay)
        {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("day", currentDay);
            editor.apply();
        }

        if(!hasUsagePermission(this))
            new AskUsagePermissionFragment().show(getSupportFragmentManager(), "ask_usage_permission");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        stopService(new Intent(this, AppUseTimeService.class));
    }

    private boolean hasUsagePermission(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
