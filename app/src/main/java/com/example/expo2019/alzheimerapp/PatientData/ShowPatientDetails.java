package com.example.expo2019.alzheimerapp.PatientData;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.expo2019.alzheimerapp.DataBase;
import com.example.expo2019.alzheimerapp.R;

import java.util.List;

public class ShowPatientDetails extends AppCompatActivity {

    TextView name_tv, pno_tv, address_tv, emg1_tv, emg2_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_patient_details);
        name_tv=findViewById(R.id.nameTV);
        pno_tv=findViewById(R.id.pnoTV);
        address_tv=findViewById(R.id.addressTV);
        emg1_tv=findViewById(R.id.emgPno1TV);
        emg2_tv=findViewById(R.id.emgPno2TV);

        GetPatientData getPatientData = new GetPatientData();
        getPatientData.execute();
    }

    public class GetPatientData extends AsyncTask<Void, Void, List<Patient>> {

       String p_name, pno, emg1_pno, emg2_pno, p_address;

        @Override
        protected List<Patient> doInBackground(Void... voids) {
            List<Patient> patientList = DataBase.getAppDatabase(getApplicationContext())
                    .patientDao()
                    .getAll();
            return patientList;
        }

        @Override
        protected void onPostExecute(List<Patient> patient) {
            super.onPostExecute(patient);

            for (Patient p: patient)
            {
                p_name = p.getP_name();
                pno = p.getP_phone();
                emg1_pno = p.getEmg_pno1();
                emg2_pno = p.getEmg_pno2();
                p_address = p.getP_addresss();
            }

            name_tv.setText(p_name);
            pno_tv.setText(pno);
            emg1_tv.setText(emg1_pno);
            emg2_tv.setText(emg2_pno);
            address_tv.setText(p_address);

        }
    }
}
