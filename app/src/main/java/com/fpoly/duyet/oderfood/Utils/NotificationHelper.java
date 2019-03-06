package com.fpoly.duyet.oderfood.Utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import com.fpoly.duyet.oderfood.R;

/**
 * Created by duyet on 11/7/2018.
 */

public class NotificationHelper extends ContextWrapper{
    private static final String DTD_CHANNEL_ID = "com.fpoly.duyet.oderfood.DTD";
    private static final String DTD_CHANNEL_NAME ="Order Food";
    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
            creatChannel();
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void creatChannel() {
        NotificationChannel dtdchannel = new NotificationChannel(DTD_CHANNEL_ID,DTD_CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
        dtdchannel.enableLights(false);
        dtdchannel.enableVibration(true);
        dtdchannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(dtdchannel);

    }

    public NotificationManager getManager() {
       if (notificationManager == null)
           notificationManager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
       return notificationManager;
    }
    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getDrinkShopNotification(String title, String message, Uri soundUri)
    {
        return new Notification.Builder(getApplicationContext(),DTD_CHANNEL_NAME)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(soundUri)
                .setAutoCancel(true);
    }
}
