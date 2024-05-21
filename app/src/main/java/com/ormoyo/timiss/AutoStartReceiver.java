package com.ormoyo.timiss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AutoStartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent i) {
        Timiss prototype = Timiss.getInstance();
        Intent intent = new Intent(context, AppUseTimeService.class);
//        intent.putExtra("Account", prototype.getMainAccount());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        }else {
            context.startService(intent);
        }
    }
}
