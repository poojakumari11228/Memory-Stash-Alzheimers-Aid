package com.example.expo2019.alzheimerapp.HealthReminders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.expo2019.alzheimerapp.R;

import java.util.ArrayList;

public class MyReminderAdapterLV extends BaseAdapter {

    Context context;
    ArrayList<RemindersModel> remindersModels;
    CustomReminderActivity customReminderActivity;
    int db_id;

    public MyReminderAdapterLV(Context context, ArrayList<RemindersModel> remindersModels,CustomReminderActivity customReminderActivity) {
        this.context = context;
        this.remindersModels = remindersModels;
        this.customReminderActivity = customReminderActivity;
    }

    @Override
    public int getCount() {
        Log.d("test*", "getCount: "+remindersModels.size());
        return remindersModels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row_reminders_lv,parent,false);

        TextView tittle = convertView.findViewById(R.id.reminderTitle_tv);
        TextView time = convertView.findViewById(R.id.reminderTime_tv);
        final Button dlt_btn = convertView.findViewById(R.id.customR_dlt);

       // LinearLayout linearLayout = convertView.findViewById(R.id.customreminder_LL);

        tittle.setText(remindersModels.get(position).getTitle());
        time.setText(remindersModels.get(position).getHour()+" : "+remindersModels.get(position).getMin());
        dlt_btn.setTag(remindersModels.get(position).getId());
        final int index = position;
        db_id = remindersModels.get(index).getId();



        dlt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(context,"touched lv id is "+ remindersModels.get(index).getId(),Toast.LENGTH_SHORT).show();
                DeleteReminderData deleteReminderData = new DeleteReminderData(context);
                customReminderActivity.deleteData(index);

                DeleteReminderData deleteReminderData1 = new DeleteReminderData(context);
                deleteReminderData.execute(String.valueOf(dlt_btn.getTag()));


            }
        });
        return convertView;
    }
}
