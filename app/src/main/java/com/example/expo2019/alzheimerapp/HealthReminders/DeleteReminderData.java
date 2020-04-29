package com.example.expo2019.alzheimerapp.HealthReminders;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.DataBase;

public class DeleteReminderData extends AsyncTask<String,Void,String> {
    Context context;


    DeleteReminderData(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {
        RemindersModel reminders = new RemindersModel();
        reminders.setId(Integer.parseInt(strings[0]));

        DataBase.getAppDatabase(context)
                .reminderDao()
                .deletereminder(reminders);
        return strings[0];
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context,"Reminder deleted!",Toast.LENGTH_SHORT).show();
    }
}
