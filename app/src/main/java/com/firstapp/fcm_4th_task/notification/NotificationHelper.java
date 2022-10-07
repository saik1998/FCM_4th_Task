package com.firstapp.fcm_4th_task.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.firstapp.fcm_4th_task.R;

public class NotificationHelper extends ContextWrapper {

    Class name;
    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            createChannels("","",name);
        }
        else
        {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels(String title, String body, Class activityName) {
        String channelID="MyChannelID";
        String channelName="Firebase Notification Channel";

        //notification channel to create your notifications
        NotificationChannel notificationChannel=new NotificationChannel(channelID,channelName, NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager=getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);

        Intent intent=new Intent(this,activityName);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,111,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder=new Notification.Builder(this,channelID)
                .setContentTitle(title)
                .setContentText(body)
                //notification Image Icon
                .setSmallIcon(R.drawable.ic_baseline_emoji_food_beverage_24)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(1,builder.build());

    }
}
