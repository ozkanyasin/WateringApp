package com.example.wateringapp.utils;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.wateringapp.R;

public class NotificationHelper extends ContextWrapper {
    public static final String channel_1_ID = "channel1ID";
    public static final String channel_2_ID = "channel2ID";
    public static final String channel1Name = "Channel 1 Name";
    public static final String channel2Name = "Channel 2 Name";
    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channel_1_ID, channel1Name, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        getManager().createNotificationChannel(channel);
        NotificationChannel channel2 = new NotificationChannel(channel_2_ID,channel2Name, NotificationManager.IMPORTANCE_LOW);
        getManager().createNotificationChannel(channel2);
    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannelNotification() {

        return new NotificationCompat.Builder(getApplicationContext(), channel_1_ID)
                .setContentTitle("Hatırlatma")
                .setContentText("Bitkilerini sulama vakti")
                .setSmallIcon(R.drawable.gardening)
                .setColor(Color.BLUE)
                .setLights(Color.BLUE,1000,1000)
                .setVibrate(new long[]{500,1000,500,1000})
                .setAutoCancel(true);
    }
}
