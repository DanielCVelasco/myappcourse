package com.example.admin.navigatordrawer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("App", "onMessageReceive");

        String body= remoteMessage.getData().get("body");
        String title= remoteMessage.getData().get("title");

        /*NotificationCompat.Builder builder= new NotificationCompat.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setSmallIcon(R.mipmap.ic_launcher);*/

        Notification builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {

            builder = new Notification.InboxStyle(new Notification.Builder(this).setTicker("Ticker")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Hey").setContentText("You have new messages")
                    .setNumber(4)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)))
                    .addLine("First Message")
                    .addLine("Second Message")
                    .addLine("Third Message")
                    .addLine("Fourth Message")
                    .setBigContentTitle("Here are your messages")
                    .setSummaryText("+4 more")
                    .build();
        }

        NotificationManager manager= (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder);
    }
}
