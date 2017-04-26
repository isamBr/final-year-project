package com.example.sniketn_pc.diabeticmanagement;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Sniketn-Pc on 07/04/2017.
 */

public class AlertAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeating_intenet = new Intent(context,MainActivity.class);
        //activity replace if needed the same activity
        repeating_intenet.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //show intent to notification
        PendingIntent pendigIntent =PendingIntent.getActivity(context,100,repeating_intenet,PendingIntent.FLAG_UPDATE_CURRENT);
        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContentIntent(pendigIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Diabetic inputs")
                .setContentText("Glucose level need to update")
                .setAutoCancel(true);
        notificationManager.notify(100,builder.build());

    }
}
