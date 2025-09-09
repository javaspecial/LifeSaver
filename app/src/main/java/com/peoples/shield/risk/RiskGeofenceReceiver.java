package com.peoples.shield.risk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class RiskGeofenceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, RiskApp.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("\u26A0 High Risk Area!")
                .setContentText("You are entering a marked hotspot. Please avoid if possible.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat.from(context).notify((int) System.currentTimeMillis(), notification.build());
    }
}
