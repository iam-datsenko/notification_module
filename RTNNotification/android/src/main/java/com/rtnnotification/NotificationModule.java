package com.rtnnotification;

import androidx.annotation.NonNull;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;
import com.rtnnotification.NativeNotificationSpec;
import androidx.core.app.NotificationCompat;
import android.os.Build;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import androidx.core.app.NotificationManagerCompat;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import com.facebook.react.bridge.Callback;
import android.net.Uri;
import java.io.File;
import android.content.res.Resources;
import java.util.Base64;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.core.graphics.drawable.IconCompat;

public class NotificationModule extends NativeNotificationSpec {

    public static String NAME = "RTNNotification";

    NotificationModule(ReactApplicationContext context) {
      super(context);
    }

    @Override
    @NonNull
    public String getName() {
      return NAME;
    }

    @Override
    public void show(String header, String message, Callback onPress, String icon, Promise promise) {
      onPress.invoke();

      Context myContext = getReactApplicationContext();
      NotificationManager notificationManager = myContext.getSystemService(NotificationManager.class);

      if (notificationManager.areNotificationsEnabled()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          int importance = NotificationManager.IMPORTANCE_DEFAULT;
          NotificationChannel channel = new NotificationChannel("CHANNEL_ID", "channel_name", importance);
          channel.setDescription("channel_description");
          notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("myapp://news/today"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(myContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // int resourceId = myContext.getResources().getIdentifier(icon, "drawable", "com.notification");

        byte[] iconBytes = Base64.getDecoder().decode(icon);
        Bitmap iconBitmap = BitmapFactory.decodeByteArray(iconBytes, 0, iconBytes.length);
        IconCompat iconCompat = IconCompat.createWithBitmap(iconBitmap);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(myContext, "CHANNEL_ID")
          .setSmallIcon(iconCompat)
          // .setSmallIcon(R.drawable.notif_icon)
          .setContentTitle(header)
          .setContentText(message)
          .setPriority(NotificationCompat.PRIORITY_DEFAULT)
          .setContentIntent(pendingIntent)
          .setAutoCancel(true);

        notificationManager.notify(777, builder.build());

        promise.resolve(header + " " + message);
      } else {
        promise.resolve("No permission");
      }
    }
}