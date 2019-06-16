package com.example.brick.thezed;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.widget.Button;

public class AppStart extends Application {
    public static final String CHANNEL_1_ID = "eventReminders";
    public static final String CHANNEL_2_ID = "activitySuggestions";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID,
                    "Schedule Reminders", NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Sends set Reminders from Event Creation");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
