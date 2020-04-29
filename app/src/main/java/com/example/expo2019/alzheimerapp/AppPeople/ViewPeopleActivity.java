package com.example.expo2019.alzheimerapp.AppPeople;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.expo2019.alzheimerapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class ViewPeopleActivity extends AppCompatActivity {


    private static final int REQ_CODE_SPEECH_INPUT = 1;
    FloatingActionButton fb_voice, fb_camera;
    String textFromVoice="";
    Context mycontext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_people);

        fb_voice = findViewById(R.id.voice_fb);
        fb_camera = findViewById(R.id.camera_fb);

        mycontext = this;
        //get data from db
        GetPeopleData getPeopleData = new GetPeopleData(this);
        getPeopleData.execute("all");

        fb_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                promptSpeechInput();

            }
        });

        fb_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Under Development", Toast.LENGTH_SHORT).show();

            }
        });

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
                "Find People by name");
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
                    //get data from db
                    GetPeopleData getSomePeopleData = new GetPeopleData((Activity) mycontext);
                    Log.d("voice test*************", "onClick: "+textFromVoice);
                    getSomePeopleData.execute(""+textFromVoice);
                }
                break;
            }
        }
    }
}
