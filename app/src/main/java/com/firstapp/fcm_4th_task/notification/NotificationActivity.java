package com.firstapp.fcm_4th_task.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firstapp.fcm_4th_task.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationActivity extends AppCompatActivity {

    EditText title,body;
    String titleStr,bodyStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        title=findViewById(R.id.title_edt);
        body=findViewById(R.id.body_edt);


    }

    public void sendAll(View view) {
        titleStr=title.getText().toString();
        bodyStr=body.getText().toString();

        if(TextUtils.isEmpty(titleStr))
        {
            title.setError("enter message");
        }
        if(TextUtils.isEmpty(bodyStr))
        {
            body.setError("enter body");
        }
        else
        {
            FcmSender notificationsSender=new FcmSender("/topics/all",titleStr,bodyStr,getApplicationContext(),NotificationActivity.this);
            notificationsSender.SendNotifications();


        }





    }
    @Override
    protected void onStart() {
        super.onStart();
        getSubscribe();
        getNotification();
    }

    //User subscribe the particular topic
    private void getSubscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d("TAG", msg);
                        Toast.makeText(NotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //user rece
    private void getNotification() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // Get new FCM registration token
                String token = task.getResult();

                // Log and toast
                @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String msg = getString(R.string.msg_token_fmt, token);
                Log.d("TAG", msg);
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();


            }
        });
    }

}