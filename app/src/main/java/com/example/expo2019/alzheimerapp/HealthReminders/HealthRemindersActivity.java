package com.example.expo2019.alzheimerapp.HealthReminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.R;

import java.util.Locale;

public class HealthRemindersActivity extends AppCompatActivity {

    // for water
    private static final int REQUEST_CODE = 1;
    //for food
    private static final int REQUEST_CODE_FOOD = 33;
    //for text to speech
    private TextToSpeech textToSpeech;

    SwitchCompat tb_water, tb_food;
    LinearLayout custom_reminder;
    AlarmManager alarmManager,alarmManagerFood;
    Intent intent;
    PendingIntent pendingIntent,pendingIntentFood;

    boolean onOffSwitch;
    boolean onOffSwitchFood;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ONN_OFF_SWITCH = "onOffSwitch";
    public static final String ONN_OFF_SWITCH_FOOD = "onOffSwitchFood";

    private static final long INITIAL_ALARM_DELAY = 1*60*1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_me);

        tb_food = findViewById(R.id.food_tb);
        tb_water = findViewById(R.id.water_tb);
        custom_reminder = findViewById(R.id.customR_LL);

        //text to speech object initialization
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //initialize alarm manager service
        inintializeAlarm();
        inintializeAlarmFood();

        //load the default state of switch
        loadSwitchState();

        //update the state of switch
        updateSwitchState();

        // water switch is clicked then change background color and alarm is set
        tb_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSwitchState();
                if(tb_water.isChecked()){
                    tb_water.setBackgroundColor(Color.parseColor("#10465E"));
                    go();
                }
                else{
                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(getApplicationContext(),"Water Alaram went Off", Toast.LENGTH_SHORT).show();
                    //convert text into voice
                    int speechStatus = textToSpeech.speak("Water Alaram went Off", TextToSpeech.QUEUE_FLUSH, null);
                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text (water-off) to Speech!");
                    }
                }
            }
        });

        // Food switch is clicked then change background color and alarm is set
        tb_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSwitchState();
                if(tb_food.isChecked()){
                    tb_food.setBackgroundColor(Color.parseColor("#10465E"));
                    startFoodAlarm();
                }
                else{
                    alarmManagerFood.cancel(pendingIntentFood);
                    Toast.makeText(getApplicationContext(),"Food Reminder went Off", Toast.LENGTH_SHORT).show();
                    //convert text into voice
                    int speechStatus = textToSpeech.speak("Food Reminder went Off", TextToSpeech.QUEUE_FLUSH, null);
                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text (food-off) to Speech!");
                    }
                }
            }
        });

        //Set Listener for custom Reminder
        custom_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"custom",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HealthRemindersActivity.this, CustomReminderActivity.class));

            }
        });
    }

    //save the current state of switch
    public void saveSwitchState(){
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(ONN_OFF_SWITCH, tb_water.isChecked()); // value to store
        editor.putBoolean(ONN_OFF_SWITCH_FOOD, tb_food.isChecked()); // value to store
        editor.commit();
    }

    //get the switch status
    public void loadSwitchState(){
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        onOffSwitch = preferences.getBoolean(ONN_OFF_SWITCH, false);  //default is false
        onOffSwitchFood = preferences.getBoolean(ONN_OFF_SWITCH_FOOD, false);  //default is false
    }

    //update the status of switch when device open again
    public void updateSwitchState(){
        tb_water.setChecked(onOffSwitch);
        tb_food.setChecked(onOffSwitchFood);
    }

    //initialize the alaram service and call reciever for water
    public void inintializeAlarm(){
        alarmManager = (AlarmManager)this.getSystemService(ALARM_SERVICE);
        intent = new Intent(this,MyAlarmReceiver.class);
        intent.putExtra("title","water");
        pendingIntent = PendingIntent.getBroadcast(HealthRemindersActivity.this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //initialize the alaram service and call reciever for food
    public void inintializeAlarmFood(){
        alarmManagerFood = (AlarmManager)this.getSystemService(ALARM_SERVICE);
        intent = new Intent(this,MyAlarmReceiver.class);
        intent.putExtra("title","food");
        pendingIntentFood = PendingIntent.getBroadcast(HealthRemindersActivity.this, REQUEST_CODE_FOOD, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //RUN ALARM for water
    public void go(){
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+INITIAL_ALARM_DELAY,AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntent);
        Toast.makeText(getApplicationContext(),"Set Water Reminder", Toast.LENGTH_SHORT).show();
        //convert text into voice
        int speechStatus = textToSpeech.speak("Water Alaram has been set", TextToSpeech.QUEUE_FLUSH, null);
        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text (water) to Speech!");
        }
    }

    //RUN ALARM for food
    public void startFoodAlarm(){
        alarmManagerFood.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+INITIAL_ALARM_DELAY,AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntentFood);
        Toast.makeText(getApplicationContext(),"Set Food Reminder", Toast.LENGTH_SHORT).show();
        //convert text into voice
        int speechStatus = textToSpeech.speak("Food Reminder has been set", TextToSpeech.QUEUE_FLUSH, null);
        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text (food) to Speech!");
        }
    }
}
