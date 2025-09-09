package com.peoples.shield.risk;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class RiskApp extends Application {
    public static final String CHANNEL_ID = "risk_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Risk Alerts", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Alerts when entering high-risk zones");
            NotificationManager nm = getSystemService(NotificationManager.class);
            if (nm != null) nm.createNotificationChannel(channel);
        }
    }
}
