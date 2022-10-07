package com.firstapp.fcm_4th_task.notification;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FcmSender {

    String s;
    String titleStr;
    String bodyStr;
    Context applicationContext;
    Activity notificationActivity;

    private RequestQueue requestQueue;
    private final String postUrl = "https://fcm.googleapis.com/fcm/send";
    private final String fcmServerKey="AAAAgpewQpg:APA91bGgY24SqaVq773-WkI_FEO--qOs1x73p8mwraBg5Bm37Bb-AHWkQvyNCm_8pFq08hJxn76zkzvW-E2DE-oIp7BfAhl9KYakniu0eWUnp6K-reFcRXjj76d_TfuIPzWAhPsobImJ";

    public FcmSender(String s, String titleStr, String bodyStr, Context applicationContext, Activity notificationActivity) {
        this.s = s;
        this.titleStr = titleStr;
        this.bodyStr = bodyStr;
        this.applicationContext = applicationContext;
        this.notificationActivity = notificationActivity;
    }
//volley api to call the object fileds
    public void SendNotifications() {
        requestQueue = Volley.newRequestQueue(notificationActivity);
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to", s);
            JSONObject notiObject = new JSONObject();
            notiObject.put("title", titleStr);
            notiObject.put("body", bodyStr);
            notiObject.put("icon", "icon"); // enter icon that exists in drawable only


            mainObj.put("notification", notiObject);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    // code run is got response


                    Toast.makeText(notificationActivity, "" + response, Toast.LENGTH_SHORT).show();
                    Log.d("onResponse", response.toString());


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // code run is got error

                    Toast.makeText(notificationActivity, "all Error"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                        Toast.makeText(notificationActivity, "Time out or no connection  error", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        //TODO
                        Toast.makeText(notificationActivity, "AuthFailureError  error", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof ServerError) {
                        //TODO
                        Toast.makeText(notificationActivity, "ServerError  error", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof NetworkError) {
                        //TODO
                        Toast.makeText(notificationActivity, "NetworkError  error", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof ParseError) {
                        //TODO
                        Toast.makeText(notificationActivity, "ParseError  error", Toast.LENGTH_SHORT).show();

                    }

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
                    return header;


                }
            };
            requestQueue.add(request);


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(notificationActivity, "Exception"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    }

