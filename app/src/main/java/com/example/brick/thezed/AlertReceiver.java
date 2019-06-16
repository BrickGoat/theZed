package com.example.brick.thezed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ScheduleEntry scheduleEntry = new ScheduleEntry();
        if (intent.hasExtra("entry")){
            scheduleEntry = intent.getParcelableExtra("entry");
        }
        NotificationHelper notificationHelper = new NotificationHelper(context, scheduleEntry);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }
}
