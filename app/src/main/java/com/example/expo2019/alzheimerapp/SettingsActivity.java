package com.example.expo2019.alzheimerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.PatientData.UpdatePatientDetails;

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat dayModeSwitch, backupSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dayModeSwitch = findViewById(R.id.dayModeSwitch);
        backupSwitch = findViewById(R.id.backupSwitch);

        dayModeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dayModeSwitch.isChecked()) {
                    dayModeSwitch.setBackgroundColor(Color.parseColor("#10465E"));
                    Toast.makeText(getApplicationContext(),"Work is Under Development!",Toast.LENGTH_SHORT).show();
                }
            }

        });

        backupSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (backupSwitch.isChecked()) {
                    backupSwitch.setBackgroundColor(Color.parseColor("#10465E"));
                    Toast.makeText(getApplicationContext(),"Work is Under Development!",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void updateEmergencyDetails(View view) {
        startActivity(new Intent(SettingsActivity.this, UpdatePatientDetails.class));
    }

    public void readingInterest(View view){
        Toast.makeText(getApplicationContext(),"Work is Under Development!",Toast.LENGTH_SHORT).show();
    }
}

