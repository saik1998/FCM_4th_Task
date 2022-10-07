package com.firstapp.fcm_4th_task.main;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.firstapp.fcm_4th_task.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService1 extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        String title=message.getNotification().getTitle();
        String body=message.getNotification().getBody();
        NotificationChannel notificationChannel=new NotificationChannel("ChannelID","NotificationTest", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager=getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);

        Notification.Builder builder=new Notification.Builder(this,"ChannelID")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_baseline_emoji_food_beverage_24)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(1,builder.build());
        super.onMessageReceived(message);


        //Toast.makeText(this, ""+message.getNotification(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

    }
}
