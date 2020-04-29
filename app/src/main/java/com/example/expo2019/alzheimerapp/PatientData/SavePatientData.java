package com.example.expo2019.alzheimerapp.PatientData;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.DataBase;
import com.example.expo2019.alzheimerapp.HomeActivity;

public class SavePatientData extends AsyncTask<String, String, String> {

    //Activity PatientDetailsActivity;

    //constructor
    Context context;
    public SavePatientData(Context context){
        this.context = context;
    }

//    public SavePatientData(Activity PatientDetailsActivity)
//    {
//        super();
//        this.PatientDetailsActivity=PatientDetailsActivity;
//    }

    @Override
    protected String doInBackground(String... strings) {

       // Toast.makeText(context,"do in background called",Toast.LENGTH_SHORT).show();

        Patient pt = new Patient();

        Log.d("CHECK: ", "doInBackground: name "+strings[0]+" no: "+strings[1]);
       // Toast.makeText(context,"name"+strings[0]+" no: "+strings[1],Toast.LENGTH_SHORT).show();

        pt.setP_name(strings[0]);
        pt.setP_phone(strings[1]);
        pt.setEmg_pno1(strings[2]);
        pt.setEmg_pno2(strings[3]);
        pt.setP_addresss(strings[4]);

        DataBase.getAppDatabase(context)
                .patientDao()
                .insert(pt);

            return null;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, " PATIENT DATA SAVED!", Toast.LENGTH_SHORT).show();
        Intent homeActivityIntent = new Intent(context, HomeActivity.class);
        context.startActivity(homeActivityIntent);
       // this.PatientDetailsActivity.finish();
    }

}
