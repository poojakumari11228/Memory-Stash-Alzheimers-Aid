package com.example.expo2019.alzheimerapp.PatientData;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.R;

public class PatientDetailsActivity extends AppCompatActivity {

    EditText name_et, pno_et, address_et, emg_pno1_et, emg_pno2_et;
    FloatingActionButton addPatientData_btn;

    String p_name, p_pno, p_emergency_pno1, p_emergency_pno2, p_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        name_et = findViewById(R.id.editText);
        pno_et = findViewById(R.id.editText3);
        emg_pno1_et = findViewById(R.id.editText2);
        emg_pno2_et = findViewById(R.id.editText4);
        address_et = findViewById(R.id.editText5);
        addPatientData_btn = findViewById(R.id.addPatientData_btn);



        addPatientData_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {

                p_name = name_et.getText().toString().trim();
                p_pno = pno_et.getText().toString().trim();;
                p_emergency_pno1 = emg_pno1_et.getText().toString().trim();
                p_emergency_pno2 = emg_pno2_et.getText().toString().trim();
                p_address = address_et.getText().toString().trim();

                Log.d("***Check***",p_name);

//                Toast.makeText(getApplicationContext(),"name: "+p_name,Toast.LENGTH_SHORT).show();

              if(p_name.isEmpty() || p_pno.isEmpty() || p_emergency_pno1.isEmpty() || p_emergency_pno2.isEmpty() || p_address.isEmpty()){

                  Toast.makeText(getApplicationContext(),"Patient's Data is Required!",Toast.LENGTH_SHORT).show();
              }
               else{

                  //  save data in background
                  SavePatientData savePatientData = new SavePatientData(getApplicationContext());
                  savePatientData.execute(p_name,p_pno,p_emergency_pno1,p_emergency_pno2,p_address);
              }




            }
        });
    }

//    public void finishAfterAsyncTask()
//    {
//        this.finish();
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        Toast.makeText(getApplicationContext(),"Patient's Data is Required!",Toast.LENGTH_SHORT).show();

    }
}
