package com.example.expo2019.alzheimerapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Games extends AppCompatActivity {

   // TextView t1,t2, t3, t4;
//    String Text;
//    Spanned result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
    }

    public void game1(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dbfweb.dakim.com/trial/2pRVtUNoTOWDwng992lqvb/"));
        startActivity(browserIntent);
    }
    public void game2(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gaspmobilegames.com/oldmaid/assets/main.htm"));
        startActivity(browserIntent);
    }
    public void game3(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.webgamesonline.com/memory/"));
        startActivity(browserIntent);
    }
    public void game4(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.brainmetrix.com/reflex-test/"));
        startActivity(browserIntent);
    }
}
