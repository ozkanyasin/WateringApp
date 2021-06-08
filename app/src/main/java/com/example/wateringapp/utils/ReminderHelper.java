package com.example.wateringapp.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.wateringapp.R;

public class ReminderHelper {

    private Activity activity;
    public ReminderHelper(Activity context) {
        this.activity = context;
    }

    public void scheduleNotification(Notification notification, long date,int routine) {

        Intent notificationIntent = new Intent(activity, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager =(AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,date,routine*24*60*60*1000,pendingIntent);
    }

    public Notification getNotification(String routine) {
        Notification.Builder builder = new Notification.Builder(activity);
        builder.setContentTitle("Hatırlatma");
        builder.setContentText(routine);
        builder.setSmallIcon(R.drawable.calendar_icon);
        return builder.build();
    }
}

class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);

    }
}
