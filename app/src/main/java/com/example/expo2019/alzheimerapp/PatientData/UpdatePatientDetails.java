package com.example.expo2019.alzheimerapp.PatientData;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.DataBase;
import com.example.expo2019.alzheimerapp.R;

import java.util.List;

public class UpdatePatientDetails extends AppCompatActivity {

    EditText name_et, pno_et, address_et, emg_pno1_et, emg_pno2_et;
    FloatingActionButton updatePatientData_btn;

    String u_p_name, u_p_pno, u_p_emergency_pno1, u_p_emergency_pno2, u_p_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient_details);

        name_et = findViewById(R.id.editText);
        pno_et = findViewById(R.id.editText3);
        emg_pno1_et = findViewById(R.id.editText2);
        emg_pno2_et = findViewById(R.id.editText4);
        address_et = findViewById(R.id.editText5);
        updatePatientData_btn = findViewById(R.id.addPatientData_btn);

        GetPatientDataForUpdate getPatientDataForUpdate = new GetPatientDataForUpdate();
        getPatientDataForUpdate.execute();

        updatePatientData_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                u_p_name = name_et.getText().toString().trim();
                u_p_pno = pno_et.getText().toString().trim();;
                u_p_emergency_pno1 = emg_pno1_et.getText().toString().trim();
                u_p_emergency_pno2 = emg_pno2_et.getText().toString().trim();
                u_p_address = address_et.getText().toString().trim();

                Log.d("***Check***",u_p_name);

                if(u_p_name.isEmpty() || u_p_pno.isEmpty() || u_p_emergency_pno1.isEmpty() || u_p_emergency_pno2.isEmpty() || u_p_address.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Patient's Data is Required!",Toast.LENGTH_SHORT).show();
                }
                else{

                    //  save data in background
                    SavePatientData savePatientData = new SavePatientData(getApplicationContext());
                    savePatientData.execute(u_p_name,u_p_pno,u_p_emergency_pno1,u_p_emergency_pno2,u_p_address);
                }
            }
        });

    }

    public class GetPatientDataForUpdate extends AsyncTask<Void, Void, List<Patient>> {

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

            name_et.setText(p_name);
            pno_et.setText(pno);
            emg_pno1_et.setText(emg1_pno);
            emg_pno2_et.setText(emg2_pno);
            address_et.setText(p_address);

        }
    }
}
