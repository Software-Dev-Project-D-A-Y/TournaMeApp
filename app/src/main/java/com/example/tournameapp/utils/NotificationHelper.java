package com.example.tournameapp.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.tournameapp.R;
import com.example.tournameapp.app.activity.LoginActivity;
import com.example.tournameapp.model.TournamentRequest;

public class NotificationHelper {

    private static String REQUESTS_APPROVE_CHANNEL = "Requests Approve";

    private Context context;
    private NotificationManager mNotificationManager;
    private NotificationChannel mRequestsApproveChannel;

    private static NotificationHelper instance;

    private NotificationHelper(Context context) {
        this.context = context;
    }

    public static NotificationHelper getInstance(Context context) {
        if(instance == null) {
            instance = new NotificationHelper(context);
        } else {
            instance.setContext(context);
        }
        instance.createRequestsApproveChannel();

        return instance;
    }

    private void setContext(Context context) {
    }

    private void createRequestsApproveChannel()
    {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mNotificationManager = context.getSystemService(NotificationManager.class);
        }
        String id = REQUESTS_APPROVE_CHANNEL;
        CharSequence name = REQUESTS_APPROVE_CHANNEL;
        int importance = NotificationManager.IMPORTANCE_HIGH;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mRequestsApproveChannel = new NotificationChannel(id, name, importance);
            mNotificationManager.createNotificationChannel(mRequestsApproveChannel);
        }
    }

    public void addRequestApprovedNotification(TournamentRequest request)
    {
        Notification.Builder notificationBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationBuilder = new Notification.Builder(context, REQUESTS_APPROVE_CHANNEL);
        } else {
//noinspection deprecation
            notificationBuilder = new Notification.Builder(context);
        }

        Intent landingIntent = new Intent(context, LoginActivity.class);
        PendingIntent pendingLandingIntent = PendingIntent.getActivity(context, 0,
                landingIntent,0);

        Notification notification = notificationBuilder
                .setContentTitle("Request from '"+request.getTournament().getTournamentName()+"' approved")
                .setSmallIcon(R.drawable.logo2)
                .setContentText("You joined to '"+request.getTournament().getTournamentName()+"' tournament")
                .setContentIntent(pendingLandingIntent).build();
        NotificationManager notificationManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            notificationManager = context.getSystemService(NotificationManager.class);
        } else {
            throw new RuntimeException("Require API");
        }
        notificationManager.notify((int) System.currentTimeMillis(), notification);
//notificationManager.notify(1, notification);
    }
}
