package com.example.clothessearchapp.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.clothessearchapp.R;
import com.example.clothessearchapp.activities.SearchOccasionsActivity;

public class NotificationUtils extends ContextWrapper {
    
    private NotificationManager notificationManager;
    public static final String MESSAGE_CHANNEL_ID = "com.exemple.clothessearchapp.notification.OCCASION";
    public static final String MESSAGE_CHANNEL_NAME = "Occasions channel";
    
    public NotificationUtils(Context base) {
        super(base);
        createChannel();
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(MESSAGE_CHANNEL_ID, MESSAGE_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GRAY);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE );
            getManager().createNotificationChannel(notificationChannel);
        }

    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getAndroidChannelNotification(String title, String body) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), MESSAGE_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.cloth)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0));

        Intent notificationIntent = new Intent(this, SearchOccasionsActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        return builder;
    }
}
