package com.example.expo2019.alzheimerapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ReadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
    }

    public void novels(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wattpad.com/stories/spiritual"));
        startActivity(browserIntent);
    }
    public void stories(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mybrain.alz.org/women-who-inspire.asp"));
        startActivity(browserIntent);
    }
    public void news(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nytimes.com/"));
        startActivity(browserIntent);
    }
    public void sports(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skysports.com/"));
        startActivity(browserIntent);
    }
    public void technology(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://indianexpress.com/section/technology/"));
        startActivity(browserIntent);
    }
    public void healthTips(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.alz.org/help-support/i-have-alz/live-well/tips-for-daily-life"));
        startActivity(browserIntent);
    }
}
