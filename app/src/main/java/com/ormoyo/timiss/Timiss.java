package com.ormoyo.timiss;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Timiss extends Application implements DefaultLifecycleObserver {
    private static Timiss instance;
    private List<AppInfo> apps;

    public static Timiss getInstance() {
        return instance;
    }
    
    public List<AppInfo> getInstalledApps() {
        return Collections.unmodifiableList(apps);
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        SharedPreferences preferences = getSharedPreferences("MAIN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Intent intent = new Intent(this, AppUseTimeService.class);
        startService(intent);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        SharedPreferences preferences = getSharedPreferences("ACCOUNT_PROFILES", Context.MODE_PRIVATE);
        SharedPreferences main = getSharedPreferences("MAIN", Context.MODE_PRIVATE);

        String currentAccountName = main.getString("CurrentAccount", null);
        apps = getApps();
    }

    private List<AppInfo> getApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<AppInfo> apps = new ArrayList<>();
        List<ResolveInfo> packages = getPackageManager().queryIntentActivities(mainIntent, 0);

        for(ResolveInfo resolveInfo : packages) {
            ApplicationInfo info = resolveInfo.activityInfo.applicationInfo;
            if(info.packageName.equals(getPackageName()))
                continue;

            String name = info.loadLabel(getPackageManager()).toString();
            Drawable icon = null;
            try {
                icon = getPackageManager().getApplicationIcon(info.packageName);
                Bitmap bitmap = drawableToBitmap(icon);
                icon = new BitmapDrawable(bitmap);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            apps.add(new AppInfo(info.packageName, name, icon));
        }
        return apps;
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if(drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        }else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public boolean isServiceRunning(){
        final ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(AppUseTimeService.class.getName())){
                return true;
            }
        }
        return false;
    }
}
