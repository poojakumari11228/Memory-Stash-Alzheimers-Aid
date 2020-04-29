package com.example.expo2019.alzheimerapp.HealthReminders;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.DataBase;

public class SaveReminderData extends AsyncTask <String,Void,String> {

        Context context;

        SaveReminderData(Context context){
            this.context = context;
        }
        @Override
        protected String doInBackground(String... strings) {
            RemindersModel reminders = new RemindersModel();
            reminders.setTitle(strings[0]);
            reminders.setHour(strings[1]);
            reminders.setMin(strings[2]);
            reminders.setStatus(strings[3]);

            DataBase.getAppDatabase(context)
                    .reminderDao()
                    .insert(reminders);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(context, "Saved Data", Toast.LENGTH_SHORT).show();
        }
    }

