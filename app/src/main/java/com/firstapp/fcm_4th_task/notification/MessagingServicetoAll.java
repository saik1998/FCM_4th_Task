package com.firstapp.fcm_4th_task.notification;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.firstapp.fcm_4th_task.notification.NotificationHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingServicetoAll extends FirebaseMessagingService {
    //this class use to send notification to who are install the app
    NotificationHelper notificationHelper;
    Class activityName;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        notificationHelper=new NotificationHelper(this);
        notificationHelper.createChannels(message.getNotification().getTitle(),message.getNotification().getBody(),activityName);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("TAG", "onNewToken: "+token);
    }
}
