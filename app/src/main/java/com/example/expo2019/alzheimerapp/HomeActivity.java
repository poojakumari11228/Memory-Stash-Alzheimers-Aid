package com.example.expo2019.alzheimerapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.AppPeople.AddPeopleActivity;
import com.example.expo2019.alzheimerapp.AppPeople.ViewPeopleActivity;
import com.example.expo2019.alzheimerapp.ChatBot.ChatBot;
import com.example.expo2019.alzheimerapp.HealthReminders.HealthRemindersActivity;
import com.example.expo2019.alzheimerapp.PatientData.Patient;
import com.example.expo2019.alzheimerapp.PatientData.PatientDetailsActivity;
import com.example.expo2019.alzheimerapp.PatientData.ShowPatientDetails;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.lang.Thread.sleep;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "loc";
    private static final int REQUEST_SMS_PERMISSION = 20;

    //for getting current location send
    FusedLocationProviderClient mFusedLocationClient;
    Location mLastLocation;
    double lat, lon, time;
    Address currLoc;
    Geocoder geocoder;
    String alertMessage;

    //for sending current location
    String p_name, emg1_pno,emg2_pno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //perform task when application start first time
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isfirstrun", true);
        if (isFirstRun) {
            Intent p_details = new Intent(HomeActivity.this, PatientDetailsActivity.class);
            startActivity(p_details);
            //edit the preference of application and commit that changes.
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isfirstrun", false).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton callFab = (FloatingActionButton) findViewById(R.id.call_fab);
        FloatingActionButton chatFab = (FloatingActionButton) findViewById(R.id.chat_fab);

        //get emergency used data
        getEmergencyData();


       //sending message button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Runtime Permissions for sms sending.
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_SMS_PERMISSION);
                } else {
                    Log.d(TAG, "Permission Granted !");

                    //get current location of patient
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    startLocationUpdates();
                }
            }
        });

        //ChatBot
        chatFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(HomeActivity.this, ChatBot.class);
                startActivity(in);
            }
        });



        //emergency call intent
        callFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:"+emg1_pno));
                startActivity(in);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //getting emergency patient details
    private void getEmergencyData() {
        class GetEmergencyData extends AsyncTask<Void, Void, List<Patient>> {

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

                for (Patient p : patient) {
                    p_name = p.getP_name();
                    emg1_pno = p.getEmg_pno1();
                    emg2_pno = p.getEmg_pno2();


                }
            }
        }
        //execute GetEmergencyData class
        GetEmergencyData getEmergencyData = new GetEmergencyData();
        getEmergencyData.execute();
    }


    //getting current location
    protected void startLocationUpdates() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
             //   Toast.makeText(getApplicationContext(), "Granted Success", Toast.LENGTH_SHORT);

                if (location != null) {
                    mLastLocation = location;
                    // Get the lat and long.
                    if (location != null) {
                        // Get the lat and long.
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                        time = location.getTime();
                        // Toast.makeText(getApplicationContext(), lat + " " + lon, Toast.LENGTH_SHORT).show();

                        try {
                            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
                            currLoc = addresses.get(0);
                            alertMessage = "This is Memory Stash(Alzheimer's Aid). We here by inform you that " + p_name + " is at " + currLoc.getAddressLine(0) + ". " + p_name + " needs your help!";
                            sendMessage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Error in getting Current Location !", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


    }

    public void sendMessage(){
        //  Toast.makeText(getApplicationContext(),"inside function !", Toast.LENGTH_SHORT).show();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(emg1_pno,null, alertMessage, null, null);
        // Toast.makeText(getApplicationContext(),"Emergency Message 1 sent Successfully !", Toast.LENGTH_SHORT).show();
        try {
            sleep(4000);
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"sleep error !", Toast.LENGTH_SHORT).show();
        }
        smsManager.sendTextMessage(emg2_pno,null, alertMessage, null, null);

        Toast.makeText(getApplicationContext(),"Emergency Messages sent Successfully !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){

            case 0:
                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    startLocationUpdates();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Permissions Not Given !", Toast.LENGTH_SHORT).show();                }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //show setting activity
            Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
            //return true;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_reading){
            //show reading portion
          //  Toast.makeText(getApplicationContext(),"Reading",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, ReadingActivity.class));
        }
       else if(id == R.id.nav_game){
            //show games
          //  Toast.makeText(getApplicationContext(),"Games",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, Games.class));
        }

        else if(id == R.id.nav_patient){
            //show Patient details
            //Toast.makeText(getApplicationContext(),"Patient",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, ShowPatientDetails.class));
        }
        else if (id == R.id.nav_people) {
            //Nav Add People clicked
            //Toast.makeText(getApplicationContext(),"People",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, AddPeopleActivity.class));
        }  else if (id == R.id.nav_peopleView) {
            //Nav View People clicked
            startActivity(new Intent(HomeActivity.this,ViewPeopleActivity.class));

        } else if (id == R.id.nav_healthReminders) {
            //For reminder
            startActivity(new Intent(HomeActivity.this, HealthRemindersActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
