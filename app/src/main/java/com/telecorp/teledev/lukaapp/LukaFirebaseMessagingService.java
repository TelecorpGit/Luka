package com.telecorp.teledev.lukaapp;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.telecorp.teledev.lukaapp.LukaFirebaseInstanceIDService.TAG;


public class LukaFirebaseMessagingService  extends FirebaseMessagingService {

    private static final String TAG = "Message";
    public String dataEntry;
    public String txtTitle;
    public String txtBody;
    public String txtRefrigUID ,customLink;
    public String txtCode;
    public String content;
    public String txtType;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        if(remoteMessage.getData().size() > 0){
//            RemoteMessage.Notification notification = remoteMessage.getNotification();
//            txtTitle = notification.getTitle();
//            txtBody = notification.getBody();
            Map<String, String> data = remoteMessage.getData();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                dataEntry =(String.valueOf(entry.getKey() + "=" + entry.getValue()));
            }
            txtTitle = data.get("title");
            txtBody = data.get("body");
            customLink = data.get("link");
            txtRefrigUID = data.get("RefrigUID");
            txtCode = data.get("code");
            txtType = data.get("type");



            workingOn();


        }




    }


    private void workingOn() {
        if (txtType.equals("Emergency")){
            dialogMessage( txtTitle,  txtBody,  customLink,  txtRefrigUID,  txtCode , txtType);
            sendNotification(txtTitle, txtBody ,customLink,txtRefrigUID, txtCode);
        }else {
            sendNotification(txtTitle, txtBody ,customLink,txtRefrigUID, txtCode);
        }



    }


    private void dialogMessage(String txtTitle, String txtBody, String customLink, String txtRefrigUID, String txtCode, String txtType) {

        Intent intent=new Intent(this,DialogActivity.class);
        intent.putExtra("title", txtTitle);
        intent.putExtra("body", txtBody);
        intent.putExtra("link", customLink);
        intent.putExtra("RefrigUID", txtRefrigUID);
        intent.putExtra("code", txtCode);
        intent.putExtra("type", txtType);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    private void sendNotification(final String txtTitle, final String txtBody, final String customLink, final String txtRefrigUID , final String txtCode) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", txtTitle);
        intent.putExtra("body", txtBody);
        intent.putExtra("link", customLink);
        intent.putExtra("RefrigUID", txtRefrigUID);
        intent.putExtra("code", txtCode);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        long[] pattern = {500,500,500,500,500};
        Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications_24dp)
                .setContentTitle(txtTitle)
                .setContentText(txtBody)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_luka))
                .setColor(Color.RED)
                .setVibrate(pattern)
                .setSound(defaultUri)
                .setLights(Color.BLUE,1,1)
                .setPriority(NotificationManager.IMPORTANCE_HIGH);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());



//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void sendNotification(final String txtTitle, final String txtBody, final String customLink, final String txtRefrigUID , final String txtCode) {
//
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("title", txtTitle);
//        intent.putExtra("body", txtBody);
//        intent.putExtra("link", customLink);
//        intent.putExtra("RefrigUID", txtRefrigUID);
//        intent.putExtra("code", txtCode);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        long[] pattern = {500,500,500,500,500};
//        Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_notifications_24dp)
//                .setContentTitle(txtTitle)
//                .setContentText(txtBody)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_luka))
//                .setColor(Color.RED)
//                .setVibrate(pattern)
//                .setSound(defaultUri)
//                .setLights(Color.BLUE,1,1)
//                .setPriority(NotificationManager.IMPORTANCE_HIGH);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());






//            Intent intent = new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("title", txtTitle);
//            intent.putExtra("body", txtBody);
//            intent.putExtra("link", customLink);
//            intent.putExtra("RefrigUID", txtRefrigUID);
//            intent.putExtra("code", txtCode);
//
//            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//            stackBuilder.addParentStack(MainActivity.class);
//            stackBuilder.addNextIntent(intent);
//            PendingIntent pendingIntent =
//                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            long[] pattern = {500,500,500,500,500};
//
////            Uri soundUri = RingtoneManager.getDefaultUri(R.raw.wakeup_sounds);
//            Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                    .setSmallIcon(R.drawable.ic_notifications_24dp)
//                    .setContentTitle(txtTitle)
//                    .setContentText(txtBody)
//                    .setAutoCancel(true)
//                    .setContentIntent(pendingIntent)
//                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_luka))
//                    .setColor(Color.RED)
//                    .setVibrate(pattern)
//                    .setSound(defaultUri)
//                    .setLights(Color.BLUE,1,1)
//                    .setPriority(NotificationManager.IMPORTANCE_HIGH);
////                .setDefaults(
////                        Notification.DEFAULT_SOUND
////                                | Notification.DEFAULT_VIBRATE
////                                | Notification.DEFAULT_LIGHTS
////                );
//
//            NotificationManager notificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
////            getApplicationContext().startActivity(intent);
    }




}

