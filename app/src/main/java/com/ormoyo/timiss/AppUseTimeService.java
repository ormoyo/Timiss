package com.ormoyo.timiss;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.ormoyo.timiss.activities.MainActivity;
import com.ormoyo.timiss.activities.OutOfTimeActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class AppUseTimeService extends Service {
    private static final int NOTIFICATION_ID = 543;
    static boolean isServiceRunning = false;

    private Timer timer;

    private static final int firebaseUpdateInterval = 60;
    private int firebaseUpdateTime;

    private final BroadcastReceiver continueUsingAppBr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String app = intent.getStringExtra("App");
            for(AppProfile p : counts.keySet()) {
                if(p.getPackageName().equals(app)) {
                    continueUsingApp = p.getPackageName();
                    break;
                }
            }
        }
    };

    Map<AppProfile, Integer> counts;
    String continueUsingApp;

    public AppUseTimeService() {
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("brAppUseTimeServiceContinueUsingApp");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            registerReceiver(continueUsingAppBr, intentFilter, Context.RECEIVER_EXPORTED);
            return;
        }

        registerReceiver(continueUsingAppBr, intentFilter);
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (isServiceRunning || intent == null) {
            stopSelf();
            return START_STICKY;
        }
//        isServiceRunning = true;
//
//        String channel = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            channel = createNotificationChannel();
//        }
//
//        Account account = (Account) intent.getSerializableExtra("Account");
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        Bitmap icon = getBitmap(R.mipmap.icon_round);
//        Notification notification = new NotificationCompat.Builder(this, channel)
//                .setContentTitle(getString(R.string.app_name))
//                .setTicker(getString(R.string.app_name))
//                .setContentText(account.getName())
//                .setSmallIcon(R.mipmap.icon_round)
//                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
//                .setContentIntent(contentPendingIntent)
//                .setOngoing(true)
//                .setSilent(true)
//                .build();
//
//        notification.flags = notification.flags | Notification.FLAG_NO_CLEAR;     // NO_CLEAR makes the notification stay when the user performs a "delete all" command
//        startForeground(NOTIFICATION_ID, notification);
//
//        counts = new HashMap<>();
//        for(AppProfile profile : account.getProfiles()) {
//            counts.put(profile, profile.getTimeLeft());
//        }
//
//        firebaseUpdateTime = firebaseUpdateInterval;
//
//        setAlarm();
//        startTimer();

        return START_STICKY;
    }

    public void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                ActivityManager m = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                String app = getForeground(m);

                for(AppProfile profile : counts.keySet()) {
                    if(!app.equals(continueUsingApp)) {
                        continueUsingApp = null;
                    }

                    if(profile.getPackageName().equals(app) && !profile.getPackageName().equals(continueUsingApp)) {
                        Integer count = counts.get(profile);
                        counts.put(profile, --count);

                        if(count <= 0) {
                            Intent intent = new Intent(AppUseTimeService.this, OutOfTimeActivity.class);

                            intent.putExtra("App", profile.getPackageName());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
                        }
                    }
                }

            }

            private String currentForeground = "";
            private String getForeground(ActivityManager manager) {
                UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

                long time = System.currentTimeMillis();

                UsageEvents usageEvents = mUsageStatsManager.queryEvents(time - 1000 * 30, System.currentTimeMillis() + (10 * 1000));
                UsageEvents.Event event = new UsageEvents.Event();
                while(usageEvents.hasNextEvent()) {
                    usageEvents.getNextEvent(event);
                }

                if(event != null && !TextUtils.isEmpty(event.getPackageName()) && event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    currentForeground = event.getPackageName();
                }else if(event != null && !TextUtils.isEmpty(event.getPackageName()) && event.getPackageName().equals(currentForeground) && event.getEventType() == UsageEvents.Event.ACTIVITY_STOPPED) {
                    currentForeground = "";
                }
                return currentForeground;
            }

            private String convertToString(int eventType) {
                switch (eventType) {
                    case 0:
                        return "NONE";
                    case 1:
                        return "MOVE_TO_FOREGROUND";
                    case 2:
                        return "MOVE_TO_BACKGROUND";
                    default:
                        return "";
                }
            }
        }, 0, 1000);
    }

    public void stopTimer() {
        if(timer == null) return;

        timer.cancel();
        timer = null;
    }

    private void setAlarm() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59);
//
//        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Intent intent = new Intent(this, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//
//        am.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(continueUsingAppBr);
        if(counts == null) return;

        Map<String, List<Integer>> map = new HashMap<>();

        for(Map.Entry<AppProfile, Integer> entry : counts.entrySet()) {
            map.put(entry.getKey().getPackageName(), Arrays.asList(entry.getKey().getTime(), entry.getValue()));
        }

        SharedPreferences preferences = getSharedPreferences("ACCOUNT_PROFILES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

//        Set<String> originalSet = preferences.getStringSet(account.getName(), new HashSet<>());
//        Set<String> newSet = new HashSet<>();
//
//        for(Map.Entry<AppProfile, Integer> entry : counts.entrySet()) {
//            AppProfile profile = entry.getKey();
//            int count = entry.getValue();
//
//            newSet.add(profile.getPackageName() + ' ' + profile.getTime() + ' ' + (profile.getTime() - count));
//        }
//
//        newSet.addAll(originalSet);
//        editor.putStringSet(account.getName(), newSet);
//
//        editor.commit();
//
//        stopTimer();
//        stopForeground(true);
//        stopSelf();

        Log.d("Stopped", "gag");
        isServiceRunning = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String createNotificationChannel() {
        String appName = getString(R.string.app_name);

        NotificationChannel channel = new NotificationChannel(appName.toLowerCase() + "_service", appName + " Background Task", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        return channel.getId();
    }

    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}