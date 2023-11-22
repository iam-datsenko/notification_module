package com.rtnnotification;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class NotificationModule extends ReactContextBaseJavaModule {

    public static String NAME = "RTNNotification";

    NotificationModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void showNotification(String title, String message) {
        NotificationCompat.Builder builder =
            new NotificationCompat.Builder(getReactApplicationContext(), "CHANNEL_ID")
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    NotificationManager notificationManager =
        (NotificationManager) getReactApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

    // Create a notification channel for Android 8.0 or higher
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel =
          new NotificationChannel(
              "CHANNEL_ID",
              "My Notification Channel",
              NotificationManager.IMPORTANCE_DEFAULT);
      channel.setDescription("My Notification Channel Description");
      channel.enableLights(true);
      channel.setLightColor(Color.GREEN);
      notificationManager.createNotificationChannel(channel);
    }

    notificationManager.notify(0, builder.build());
  }
}