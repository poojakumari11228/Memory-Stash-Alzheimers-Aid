package com.example.expo2019.alzheimerapp.HealthReminders;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.R;

public class MyAlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "5";
    Notification mNotification;
    String alarmTitle;


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm triggered!", Toast.LENGTH_SHORT).show();
        //get title
        try{
            alarmTitle = intent.getStringExtra("title").toString();
        }catch(Exception e){
            e.printStackTrace();
        }


        MediaPlayer mediaPlayer =  MediaPlayer.create(context, R.raw.coolalarmnew);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();

                try {
                    Thread.sleep(3*1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mp.pause();
            }
        });
        //mediaPlayer.start();
        Intent i = new Intent(context,Myservice.class);

        Log.d("ALARM*", "onReceive: alarm trigered,  title is "+alarmTitle);
        i.putExtra("title", ""+alarmTitle);



        if( alarmTitle.equalsIgnoreCase("food")){
            mediaPlayer =  MediaPlayer.create(context, R.raw.foodremindervoice);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
           // mediaPlayer.start();
        }else if( alarmTitle.equalsIgnoreCase("water")){
            mediaPlayer =  MediaPlayer.create(context, R.raw.waterreminervoice);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
           // mediaPlayer.start();

        }else {
            mediaPlayer = MediaPlayer.create(context, R.raw.todo);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            //mediaPlayer.start();
        }

        context.startService(i);
    }


}