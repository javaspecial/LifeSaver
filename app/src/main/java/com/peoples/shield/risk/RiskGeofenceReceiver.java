package com.peoples.shield.risk;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class RiskGeofenceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        if (event == null || event.hasError()) return;

        int transition = event.getGeofenceTransition();
        if (transition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            List<Geofence> triggering = event.getTriggeringGeofences();
            String areaName = (triggering != null && !triggering.isEmpty()) ? triggering.get(0).getRequestId() : "Risk Zone";
            this.sendNotification(context, "⚠ High Risk Area!", "You are entering " + areaName + ". Please avoid if possible.");
        }
    }

    void sendNotification(Context ctx, String title, String msg) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, RiskApp.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        NotificationManagerCompat.from(ctx).notify((int) System.currentTimeMillis(), builder.build());
    }
}
