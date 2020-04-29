package com.example.expo2019.alzheimerapp.HealthReminders;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.HomeActivity;
import com.example.expo2019.alzheimerapp.R;

public class Myservice extends Service {

    private static final String CHANNEL_ID = "2";
    String alarmTitle;
    Notification mNotification ;
    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Reminder!",Toast.LENGTH_LONG).show();
        //get title

        try{
            alarmTitle = intent.getStringExtra("title");
            Log.d("Service*", "MyService: alarm trigered,  title is "+alarmTitle);

        }catch(Exception e){
            e.printStackTrace();
        }
        if (alarmTitle==null){
            buildNotofication(""+"Hey! you have something to do.");

        }else if( alarmTitle.equalsIgnoreCase("food")){
            buildNotofication(""+"Hey! have some food you need energy.");
        }else if( alarmTitle.equalsIgnoreCase("water")){
            buildNotofication(""+"Hey! Its time to drink some water.");
        }else
            buildNotofication("Hey! you have to "+this.alarmTitle+".");

        startForeground(1,mNotification);
        return START_STICKY;
    }

    private void buildNotofication(String alarmTitle) {
        Intent notificationIntent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            CharSequence channelName = "Playback channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            nManager.createNotificationChannel(notificationChannel);
        }

        mNotification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                .setContentTitle("Reminder!")
                .setContentText(alarmTitle)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()).build();

    }




}
