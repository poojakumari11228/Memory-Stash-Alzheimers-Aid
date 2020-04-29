package com.example.expo2019.alzheimerapp.HealthReminders;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.AppPeople.GetPeopleData;
import com.example.expo2019.alzheimerapp.DataBase;
import com.example.expo2019.alzheimerapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CustomReminderActivity extends AppCompatActivity {

    //for text to speech
    private TextToSpeech textToSpeech;

    //for voice to text
    private static final int REQ_CODE_SPEECH_INPUT = 1;
    ImageView speakTitle;
    String textFromVoice="";

    private static int REQUEST_CODE =99 ;
    EditText remaindertitle;
    TimePicker timePicker;
    FloatingActionButton addReminderC;
    Calendar calendar = Calendar.getInstance();
    List<RemindersModel> remindersModelList;
    ArrayAdapter arrayAdapter;
    ListView listView;
    MyReminderAdapterLV adapterLV;
    ArrayList<RemindersModel> arrayList;

    AlarmManager alarmManager;
    Intent intent;
    PendingIntent pendingIntent;

    String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_reminder);


        remaindertitle = findViewById(R.id.reminderTitle);
        timePicker = findViewById(R.id.simpleTimePicker);
        addReminderC = findViewById(R.id.add_customR);
        listView = findViewById(R.id.reminder_lv);
        speakTitle = findViewById(R.id.speakNowIV);

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

        speakTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        // Alarm manager
        alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);


        timePicker.setIs24HourView(false); // used to display AM/PM mode

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                // Set the time to calender object

                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
//
//                //if alarm time is before current time then set for next day
//                if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= hourOfDay) {
//                    calendar.add(Calendar.DAY_OF_YEAR, 1); // add, not set!
//                }
            }
        });

        //on add reminder click
        addReminderC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = remaindertitle.getText().toString();

               // Toast.makeText(getBaseContext(),""+calendar.get(Calendar.HOUR_OF_DAY),Toast.LENGTH_SHORT).show();
                SaveReminderData saveReminderData = new SaveReminderData(getApplicationContext());
                saveReminderData.execute(title,""+calendar.get(Calendar.HOUR_OF_DAY),""+calendar.get(Calendar.MINUTE),"set");

                getDataAndSetLV();

                //For Alarm
                intent = new Intent(CustomReminderActivity.this, MyAlarmReceiver.class);
                //Bundle myBundle = new Bundle();
                intent.putExtra("title", ""+title);
//                intent.putExtras(myBundle);
                REQUEST_CODE = (int) System.currentTimeMillis();
                pendingIntent = PendingIntent.getBroadcast(CustomReminderActivity.this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.setRepeating(AlarmManager.RTC,calendar.getTimeInMillis(),24*60*1000,pendingIntent);
                // Make a toast to display toggle button status
                Toast.makeText(getApplicationContext(),
                        "Alarm is set for "+title, Toast.LENGTH_SHORT).show();
                //convert text into voice
                int speechStatus = textToSpeech.speak("Alarm is set for "+title, TextToSpeech.QUEUE_FLUSH, null);
                if (speechStatus == TextToSpeech.ERROR) {
                    Log.e("TTS", "Error in converting Text (custom) to Speech!");
                }

            }
        });

        //get data from db and set to listview
        getDataAndSetLV();

    }



    public void getDataAndSetLV(){


        class GetRemindersData extends AsyncTask <Void,Void,List<RemindersModel>>{


            @Override
            protected List<RemindersModel> doInBackground(Void... voids) {
                List<RemindersModel> dataList = DataBase.getAppDatabase(getBaseContext()).reminderDao().getAll();

                return dataList;
            }

            @Override
            protected void onPostExecute(List<RemindersModel> remindersModels) {
                super.onPostExecute(remindersModels);

              arrayList = new ArrayList<>();

                for (RemindersModel r : remindersModels){
                    arrayList.add(r);
                }

                 adapterLV= new MyReminderAdapterLV(getApplication(),arrayList,CustomReminderActivity.this);
                 listView.setAdapter(adapterLV);



                // Toast.makeText(getApplicationContext(),"adapter added",Toast.LENGTH_SHORT).show();

            }
        }


        //create innerclass obj
        GetRemindersData getRemindersData = new GetRemindersData();
        getRemindersData.execute();



    }

    public void deleteData(int index){
        arrayList.remove(index);
        adapterLV = new MyReminderAdapterLV(getApplicationContext(),arrayList,CustomReminderActivity.this);
        listView.setAdapter(adapterLV);
    }


    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Set Title For Remainder");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Speech not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textFromVoice = result.get(0);
                    //set Alarm to Reminder Title
                    remaindertitle.setText(textFromVoice);
                }
                break;
            }
        }
    }
}

